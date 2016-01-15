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
package com.byteperceptions.require.gamestate;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.byteperceptions.require.RequireChangeListener;
import com.byteperceptions.require.model.BankStockRegistry;
import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.model.TileRegistry;
import com.byteperceptions.require.registry.PlayerRegistry;
import com.byteperceptions.require.turn.TurnRegistry;


/**
 * @author Ryan Ylitalo
 *
 */
public class GameStateBroker
{
	private static final GameStateBroker INSTANCE = new GameStateBroker();
	private List<GameStateChangeListener> gameStateChangeListeners;

	private Stack<GameState> gameStates = new Stack<GameState>();

	private GameStateBroker()
	{
		gameStateChangeListeners = new ArrayList<GameStateChangeListener>();
	}

	public static GameStateBroker getInstance()
	{
		return INSTANCE;
	}

	public void captureGameState()
	{
		GameState gameState = new GameState();
		TileRegistry.getInstance().captureGameState(gameState);

		for (Player player : PlayerRegistry.getInstance().getAllPlayers())
		{
			player.captureGameState(gameState);
		}
		TurnRegistry.getInstance().captureGameState(gameState);

		gameStates.push(gameState);
	}

	public void restoreGameState(int selectedRow, boolean playBackOnly)
	{
		if (playBackOnly)
		{
			if ((gameStates.size() - (selectedRow + 1)) < 0)
			{
				System.out.println("Error : "
						+ (gameStates.size() - (selectedRow + 1)));
			}
			GameState gameState = gameStates.get(gameStates.size()
					- (selectedRow + 1));
			restoreGameState(gameState);
		}
		// Resuming Game play from this state
		else
		{
			for (int i = 0; i < selectedRow; i++)
			{
				gameStates.pop();
				restoreGameState(gameStates.peek());
			}
			TurnRegistry.getInstance().currentPlayerTurn().takeTurn();
		}
	}

	public void restoreGameState(GameState gameState)
	{
		TileRegistry.getInstance().restoreGameState(gameState);
		BankStockRegistry.getInstance().clear();
		for (Player player : PlayerRegistry.getInstance().getAllPlayers())
		{
			player.restoreGameState(gameState);
		}
		TurnRegistry.getInstance().restoreGameState(gameState);

		for (GameStateChangeListener gameStateChangeListener : gameStateChangeListeners)
		{
			gameStateChangeListener.gameStateChanged();
		}

		RequireChangeListener.getInstance().fireBoardChangedEvent();
	}

	public void addGameStateChangeListener(
			GameStateChangeListener gameStateChangeListener)
	{
		this.gameStateChangeListeners.add(gameStateChangeListener);
	}

	public void resumeGame()
	{
		restoreGameState(gameStates.peek());
	}
}
