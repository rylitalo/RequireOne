package com.byteperceptions.require.strategy.scenario.playtile;

import java.util.List;

import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.model.Tile;
import com.byteperceptions.require.model.TileRegistry;

public class PlayTileScenarioIsMerger implements PlayTileScenario
{

	@Override
	public int calculateValue(Player player, Tile tile)
	{
		if (isPlayerInScenario(player, tile))
		{
			List<HotelChain> potentiallyDeceasedChains = TileRegistry
					.getInstance().getPotentiallyDeceasedChain(tile);

			int maxValue = 0;
			for (HotelChain potentiallyDeceasedChain : potentiallyDeceasedChains)
			{
				int valueForDeadCompany = 0;

				if (potentiallyDeceasedChain.getMajorityStockHolders()
						.contains(player)
						&& potentiallyDeceasedChain.getMajorityStockHolders()
								.size() == 1)
				{
					valueForDeadCompany = 5 + potentiallyDeceasedChain
					.determinePayoffForPlayer(player) / 100;
				}
				else
				{
					valueForDeadCompany = potentiallyDeceasedChain
							.determinePayoffForPlayer(player) / 100;
				}

				if (valueForDeadCompany > maxValue)
				{
					maxValue = valueForDeadCompany;
				}
			}

			if (maxValue > 0)
			{
				return maxValue;
			}
			else
			{
				return -5;
			}
		}

		return 0;
	}

	@Override
	public boolean isPlayerInScenario(Player player, Tile tile)
	{
		//TODO - Should actually call the Engine to determine the sum total of what would be optimal to buy.
		return TileRegistry.getInstance().isMergerChip(tile);
	}

}
