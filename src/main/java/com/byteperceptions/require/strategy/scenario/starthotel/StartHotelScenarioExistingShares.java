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
package com.byteperceptions.require.strategy.scenario.starthotel;

import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.strategy.scenario.playtile.HotelValue;


/**
 * @author Ryan Ylitalo
 *
 */
public class StartHotelScenarioExistingShares implements StartHotelScenario
{

	/**
	 * @see com.byteperceptions.require.strategy.scenario.starthotel.StartHotelScenario#calculateValue(com.byteperceptions.require.model.Player, com.byteperceptions.require.model.HotelChain)
	 */
	@Override
	public int calculateValue(Player player, HotelChain hotelChain)
	{
		return HotelValue.getMaxHotelValueForPlayerByNumberOfStocks(player)
		.getValue();
	}

	/**
	 * @see com.byteperceptions.require.strategy.scenario.starthotel.StartHotelScenario#isPlayerInScenario(com.byteperceptions.require.model.Player, com.byteperceptions.require.model.HotelChain)
	 */
	@Override
	public boolean isPlayerInScenario(Player player, HotelChain hotelChain)
	{
		//Do nothing here... Logic all in calculate
		return false;
	}

}
