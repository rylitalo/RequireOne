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

import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.model.Tile;


/**
 * @author Ryan Ylitalo
 *
 */
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
