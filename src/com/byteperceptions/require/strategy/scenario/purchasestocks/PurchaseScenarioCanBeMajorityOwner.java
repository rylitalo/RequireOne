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
package com.byteperceptions.require.strategy.scenario.purchasestocks;

import com.byteperceptions.require.model.BankStockRegistry;
import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;


/**
 * @author Ryan Ylitalo
 *
 */
public class PurchaseScenarioCanBeMajorityOwner implements PurchaseScenario
{

	@Override
	public int calculateValue(Player player, HotelChain hotelChain,
			int sharesLeftToPurchaseThisTurn)
	{
		if (isPlayerInScenario(player, hotelChain, sharesLeftToPurchaseThisTurn))
		{
			//lower the value on majority once we get to nine.
			if (player.getStockRegistry().getNumberOfShares(hotelChain) > 8)
			{
				return 2;
			}
			else
			{
				return 5;
			}
		}

		return 0;
	}

	@Override
	public boolean isPlayerInScenario(Player player, HotelChain hotelChain,
			int sharesLeftToPurchaseThisTurn)
	{
		return hotelChain.getNumberOfStocksForMajority() < (player
				.getStockRegistry().getNumberOfShares(hotelChain) + Math.min(
				sharesLeftToPurchaseThisTurn, BankStockRegistry.getInstance()
						.getNumberOfShares(hotelChain)));
	}

}
