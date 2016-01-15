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
package com.byteperceptions.require.strategy.scenario.selectsurvivor;

import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;


/**
 * @author Ryan Ylitalo
 *
 */
public class SelectSurvivorScenarioNeedMoney implements SelectSurvivorScenario
{
	public int calculateValue(Player player, HotelChain hotelChain)
	{
		if (isPlayerInScenario(player, hotelChain))
		{
			if (hotelChain.getMajorityStockHolders().contains(player)
					&& hotelChain.getMajorityStockHolders().size() == 1)
			{
				//If I need money, I don't want this company to survive the merger.
				return -5 - hotelChain.determinePayoffForPlayer(player)/100;
			}
			else 
			{
				return -(hotelChain.determinePayoffForPlayer(player)/100);
			}
		}
		return 0;
	}

	public boolean isPlayerInScenario(Player player, HotelChain hotelChain)
	{
		if (player.getCash() < 10000)
		{
			return true;
		}
		return false;
	}
}
