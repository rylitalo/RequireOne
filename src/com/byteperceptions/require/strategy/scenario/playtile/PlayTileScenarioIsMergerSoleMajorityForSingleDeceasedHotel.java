package com.byteperceptions.require.strategy.scenario.playtile;

import java.util.List;

import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.model.Tile;
import com.byteperceptions.require.model.TileRegistry;

public class PlayTileScenarioIsMergerSoleMajorityForSingleDeceasedHotel
		implements PlayTileScenario
{

	@Override
	public int calculateValue(Player player, Tile tile)
	{
		if (isPlayerInScenario(player, tile))
		{
			return 10;
		}

		return 0;
	}

	@Override
	public boolean isPlayerInScenario(Player player, Tile tile)
	{
		if (TileRegistry.getInstance().isMergerChip(tile))
		{
			List<HotelChain> potentiallyDeceasedChains = TileRegistry
					.getInstance().getPotentiallyDeceasedChain(tile);

			for (HotelChain potentiallyDeceasedChain : potentiallyDeceasedChains)
			{
				if (potentiallyDeceasedChain.getMajorityStockHolders().size() == 1
						&& potentiallyDeceasedChain.getMinorityStockHolders()
								.size() == 0
						&& potentiallyDeceasedChain.getMajorityStockHolders()
								.contains(player))
				{
					return true;
				}
			}
		}
		return false;
	}

}
