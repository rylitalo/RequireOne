package com.byteperceptions.require.gamestate;

import java.util.ArrayList;
import java.util.List;

import com.byteperceptions.require.model.Player;

public class GameState
{
	private TileRegistryState tileRegistryState;
	private List<PlayerGameState> playerGameStates;
	private int turnCounter;

	public GameState()
	{
		playerGameStates = new ArrayList<PlayerGameState>();
	}

	public TileRegistryState getTileRegistryState()
	{
		return tileRegistryState;
	}

	public void setTileRegistryState(TileRegistryState tileRegistryState)
	{
		this.tileRegistryState = tileRegistryState;
	}

	public void addPlayerGameState(PlayerGameState playerGameState)
	{
		playerGameStates.add(playerGameState);
	}

	public PlayerGameState getPlayerGameState(Player player)
	{
		for (PlayerGameState playerGameState : playerGameStates)
		{
			if (playerGameState.getPlayer().equals(player))
			{
				return playerGameState;
			}
		}
		// Shouldn't ever get here.
		return null;
	}

	public void setTurnCounter(int turnCounter)
	{
		this.turnCounter = turnCounter;
	}

	public int getTurnCounter()
	{
		return turnCounter;
	}

	@Override
	public String toString()
	{
		return "Turn : " + turnCounter;
	}
}
