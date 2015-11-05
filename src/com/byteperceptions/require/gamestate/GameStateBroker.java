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
