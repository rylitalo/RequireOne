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
package com.byteperceptions.require.strategy.scenario.playtile;

import java.util.List;

import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.model.Tile;
import com.byteperceptions.require.model.TileRegistry;


/**
 * @author Ryan Ylitalo
 *
 */
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
