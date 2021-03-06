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
package com.byteperceptions.require.model;

import java.util.List;

import com.byteperceptions.require.RequireChangeListener;


/**
 * @author Ryan Ylitalo
 *
 */
public class BankStockRegistry extends StockRegistry
{
	private static BankStockRegistry INSTANCE = new BankStockRegistry();

	private BankStockRegistry()
	{
		super();
	}

	public void initialize()
	{
		initializeStockShares(HotelChain.LUXOR, availableLuxor);
		initializeStockShares(HotelChain.TOWER, availableTower);
		initializeStockShares(HotelChain.WORLDWIDE, availableWorldwide);
		initializeStockShares(HotelChain.FESTIVAL, availableFestival);
		initializeStockShares(HotelChain.AMERICAN, availableAmerican);
		initializeStockShares(HotelChain.CONTINENTAL, availableContinental);
		initializeStockShares(HotelChain.IMPERIAL, availableImperial);
	}

	public static BankStockRegistry getInstance()
	{
		return INSTANCE;
	}

	/**
	 * @param hotelChain
	 * @param availableShares
	 */
	private void initializeStockShares(HotelChain hotelChain,
		List<StockShare> availableShares)
	{
		for (int i = 0; i < 25; i++)
		{
			availableShares.add(new StockShare(hotelChain));
		}
	}

	/**
	 * @see com.byteperceptions.require.model.StockRegistry#getNumberOfShares(com.byteperceptions.require.model.HotelChain)
	 */
	@Override
	public int getNumberOfShares(HotelChain chain)
	{
		return sharesByHotelChain.get(chain).size();
	}

	public StockShare brokerSharePurchase(HotelChain hotelChain, Player player)
	{
		StockShare stockShare = removeShare(hotelChain);
		player.getStockRegistry().addShare(stockShare);
		player.setCash(player.getCash() - hotelChain.getPrice());
		return stockShare;
	}

	/**
	 * @param currentHotelChain
	 * @param player
	 */
	public void issueFreeStock(HotelChain hotelChain, Player player)
	{
		if (getNumberOfShares(hotelChain) > 0)
		{
			StockShare stockShare = removeShare(hotelChain);
			player.getStockRegistry().addShare(stockShare);
		}

	}

	/**
	 * @param player
	 * @param mergerSurvivor
	 * @param mergerDeceased
	 * @param determineTradeAmount
	 */
	public void tradeStocks(Player player, HotelChain mergerSurvivor,
		HotelChain mergerDeceased, int tradeAmount)
	{
		for (int i = 0; i < tradeAmount; i++)
		{
			StockShare stockShare = removeShare(mergerSurvivor);
			player.getStockRegistry().addShare(stockShare);

			stockShare = player.getStockRegistry().removeShare(mergerDeceased);
			addShare(stockShare);
			stockShare = player.getStockRegistry().removeShare(mergerDeceased);
			addShare(stockShare);
		}
		RequireChangeListener.getInstance().fireBoardChangedEvent();
	}

	/**
	 * @param player
	 * @param activeChain
	 */
	public void sellAllShares(Player player, HotelChain activeChain)
	{
		int numberOfShares = player.getStockRegistry().getNumberOfShares(
			activeChain);
		for (int i = 0; i < numberOfShares; i++)
		{
			StockShare stockShare = player.getStockRegistry().removeShare(
				activeChain);
			addShare(stockShare);
			player.setCash(player.getCash() + activeChain.getPrice());
		}
		RequireChangeListener.getInstance().fireBoardChangedEvent();
	}
	
	public void sellShares(Player player, HotelChain activeChain, int numberOfShares)
	{
		for (int i = 0; i < numberOfShares; i++)
		{
			StockShare stockShare = player.getStockRegistry().removeShare(
				activeChain);
			addShare(stockShare);
			player.setCash(player.getCash() + activeChain.getPrice());
		}
		RequireChangeListener.getInstance().fireBoardChangedEvent();
	}
	
}
