package com.byteperceptions.require.strategy.scenario.playtile;

import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.model.Tile;

public class PlayTileScenarioIsStarterTile implements PlayTileScenario
{

	@Override
	public int calculateValue(Player player, Tile tile)
	{

		if (isPlayerInScenario(player, tile))
		{
			return HotelValue.getMaxHotelValueForPlayerByNumberOfStocks(player)
					.getValue();
		}

		return 0;
	}

	@Override
	public boolean isPlayerInScenario(Player player, Tile tile)
	{

		// TODO - need to do analysis to see whether it makes sense.
		// May not make sense to start a company in a spot that can merge if
		// you're
		// not going to buy
		if (tile.isStarterChip() && tile.isPlayable())
		{
			return true;
		}

		return false;
	}
}
