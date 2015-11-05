/**
 * 
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
 * @author JDev
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
			//nextPlayerTurn.takeTurn();
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
		
		JOptionPane.showMessageDialog(null, "Game Over. " + winner + " has won.");

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
