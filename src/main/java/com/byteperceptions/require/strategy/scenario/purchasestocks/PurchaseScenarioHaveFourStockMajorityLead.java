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

import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.registry.PlayerRegistry;


/**
 * @author Ryan Ylitalo
 *
 */
public class PurchaseScenarioHaveFourStockMajorityLead implements PurchaseScenario
{

	@Override
	public int calculateValue(Player player, HotelChain hotelChain, int sharesLeftToPurchaseThisTurn)
	{
		if (isPlayerInScenario(player, hotelChain, sharesLeftToPurchaseThisTurn))
		{
			return -5;
		}

		return 0;
	}

	@Override
	public boolean isPlayerInScenario(Player player, HotelChain hotelChain, int sharesLeftToPurchaseThisTurn)
	{
		//Returns true if there is one player or less who is closer than 4 stocks on the count.  IE:  Determines the lead
		//for both minority and majority.
		
		int countOfPlayersCloserThan4Stocks = 0;
		
		if(!hotelChain.getMajorityStockHolders().contains(player) || hotelChain.getMajorityStockHolders().size() > 1){
			return false;
		}
		
		
		for(Player gamePlayer: PlayerRegistry.getInstance().getAllPlayers()){
			if(gamePlayer.equals(player)){
				continue;
			}
			
			if(gamePlayer.getStockRegistry().getNumberOfShares(hotelChain) > (player.getStockRegistry().getNumberOfShares(hotelChain) - 4)){
				countOfPlayersCloserThan4Stocks++;
			}
		}
		
		return countOfPlayersCloserThan4Stocks <= 1;
		
	}
	
//	private boolean has4StockLeadForMajority(Player player, HotelChain hotelChain){
//		return hotelChain.getMajorityStockHolders().contains(player) && hotelChain.getMajorityStockHolders().size() == 1 &&
//				hotelChain.getNumberOfStocksForMinority() < (player.getStockRegistry().getNumberOfShares(hotelChain) - 3);
//	}
//	
//	private boolean has4StockLeadForMinority(Player player, HotelChain hotelChain){
//		return hotelChain.getMinorityStockHolders().contains(player) && hotelChain.getMajorityStockHolders().size() == 1 &&
//				hotelChain.getMinorityStockHolders().size() == 1 && 
//				hotelChain.getNumberOfStocksForMinority() < (player.getStockRegistry().getNumberOfShares(hotelChain) - 3);
//	}

}
