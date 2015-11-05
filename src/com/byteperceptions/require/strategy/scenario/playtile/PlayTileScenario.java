package com.byteperceptions.require.strategy.scenario.playtile;

import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.model.Tile;

public interface PlayTileScenario
{
	//This 
	public int calculateValue(Player player, Tile tile);
	public boolean isPlayerInScenario(Player player, Tile tile);
}
