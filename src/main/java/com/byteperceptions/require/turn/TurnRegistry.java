/** Copyright Ryan Ylitalo and BytePerceptions LLC. 
  * 
  * Licensed under the Apache License, Version 2.0 (the "License"); 
  * you may not use this file except in compliance with the License. 
  * You may obtain a copy of the License at 
  * 
  *      http://www.apache.org/licenses/LICENSE-2.0 
  * 
  * Unless required by applicable law or agreed to in writing, software 
  * distributed under the License is distributed on an "AS IS" BASIS, 
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
  * See the License for the specific language governing permissions and 
  * limitations under the License. 
  */ 
package com.byteperceptions.require.turn;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.byteperceptions.require.RequireChangeListener;
import com.byteperceptions.require.gamestate.GameState;
import com.byteperceptions.require.gamestate.GameStateBroker;
import com.byteperceptions.require.model.BankStockRegistry;
import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.model.StockShare;
import com.byteperceptions.require.model.Tile;
import com.byteperceptions.require.model.TileRegistry;
import com.byteperceptions.require.registry.PlayerRegistry;


/**
 * @author Ryan Ylitalo
 *
 */
public class TurnRegistry
{
	private int nextTurnCounter;
	private static final TurnRegistry INSTANCE = new TurnRegistry();

	private List<TurnChangedListener> turnChangedListeners;

	private TurnRegistry()
	{
		nextTurnCounter = 1;
		turnChangedListeners = new ArrayList<TurnChangedListener>();
	}

	public static TurnRegistry getInstance()
	{
		return INSTANCE;
	}

	public void addTurnChangedListener(TurnChangedListener listener)
	{
		turnChangedListeners.add(listener);
	}

	public void finishTurn(Tile previousPlayedTile, List<StockShare> purchasedShares)
	{
		RequireChangeListener.getInstance().fireBoardChangedEvent();
		Player currentPlayer = currentPlayerTurn();
		if (currentPlayer.shouldEndGame())
		{
			endGame();
		}
		else
		{
			Player nextPlayerTurn = nextPlayerTurn();
			for (TurnChangedListener listener : turnChangedListeners)
			{
				listener.turnChanged(nextPlayerTurn, previousPlayedTile, currentPlayer, purchasedShares);
			}

			nextTurnCounter++;
			GameStateBroker.getInstance().captureGameState();

			new Thread(new PlayerTurnThread(nextPlayerTurn)).start();
		}
	}

	/**
	 * 
	 */
	private void endGame()
	{
		Player winner = null;
		for (HotelChain activeChain : TileRegistry.getInstance()
			.getActiveHotelChains())
		{
			activeChain.payoffMajorityMinorityStockHolders();
			
			int maxCash = 0;
			for (Player player : PlayerRegistry.getInstance().getAllPlayers())
			{
				BankStockRegistry.getInstance().sellAllShares(player,
					activeChain);
				if(player.getCash() > maxCash)
				{
					maxCash = player.getCash();
					winner = player;
				}
			}
		}

		RequireChangeListener.getInstance().fireBoardChangedEvent();
		
		JOptionPane.showMessageDialog(null, "Game Over. " + winner + " won.");

	}

	/**
	 * @return
	 */
	public Player nextPlayerTurn()
	{
		List<Player> allPlayers = PlayerRegistry.getInstance().getAllPlayers();
		Player currentPlayerTurn = allPlayers.get(nextTurnCounter
			% allPlayers.size());
		return currentPlayerTurn;
	}

	public Player currentPlayerTurn()
	{
		return getPlayerForTurnCounter(nextTurnCounter);
	}

	private Player getPlayerForTurnCounter(int turnCounter)
	{
		List<Player> allPlayers = PlayerRegistry.getInstance().getAllPlayers();
		Player currentPlayerTurn = allPlayers.get((turnCounter - 1)
			% allPlayers.size());
		return currentPlayerTurn;
	}

	/**
	 * @return
	 */
	public List<Player> getMergerTransactionOrder()
	{
		int numberOfPlayers = PlayerRegistry.getInstance().getAllPlayers()
			.size();
		List<Player> playersInOrderStartingWithCurrentTurn = new ArrayList<Player>(
			numberOfPlayers);
		int turnCounter = nextTurnCounter;
		for (int i = 0; i < numberOfPlayers; i++)
		{
			playersInOrderStartingWithCurrentTurn
				.add(getPlayerForTurnCounter(turnCounter));
			turnCounter++;
		}
		return playersInOrderStartingWithCurrentTurn;
	}
	
	public void clear(){
		nextTurnCounter = 1;
		turnChangedListeners = new ArrayList<TurnChangedListener>();
	}

	public void captureGameState(GameState gameState)
	{
		gameState.setTurnCounter(nextTurnCounter);
	}

	public void restoreGameState(GameState gameState)
	{
		nextTurnCounter = gameState.getTurnCounter();
	}
}
