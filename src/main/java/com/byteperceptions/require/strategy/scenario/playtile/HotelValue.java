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
import java.util.PriorityQueue;

import com.byteperceptions.require.model.BankStockRegistry;
import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.model.TileRegistry;
import com.byteperceptions.require.registry.PlayerRegistry;


/**
 * @author Ryan Ylitalo
 *
 */
public class HotelValue
{
	private Integer value;

	private HotelChain hotelChain;

	public HotelValue(Integer value, HotelChain hotelChain)
	{
		this.value = value;
		this.hotelChain = hotelChain;
	}

	public Integer getValue()
	{
		return value;
	}

	public HotelChain getHotelChain()
	{
		return hotelChain;
	}

	public static HotelValue getMaxHotelValueForPlayerByNumberOfStocks(Player player)
	{
		List<HotelChain> hotelChains = TileRegistry.getInstance()
				.getAvailableHotelChains();

		PriorityQueue<HotelValue> hotelValueQueue = new PriorityQueue<HotelValue>(
				7, new HotelValueComparator());

		for (HotelChain hotelChain : hotelChains)
		{
			int maxSharesNotOwnedByPlayer = 0;
			int secondMostSharesNotOwnedByPlayer = 0;
			for (Player gamePlayer : PlayerRegistry.getInstance()
					.getAllPlayers())
			{
				if (gamePlayer != player)
				{
					if (gamePlayer.getStockRegistry().getNumberOfShares(
							hotelChain) > maxSharesNotOwnedByPlayer)
					{
						secondMostSharesNotOwnedByPlayer = maxSharesNotOwnedByPlayer;
						maxSharesNotOwnedByPlayer = gamePlayer
								.getStockRegistry().getNumberOfShares(
										hotelChain);
					}
					else if (gamePlayer.getStockRegistry().getNumberOfShares(
							hotelChain) > secondMostSharesNotOwnedByPlayer)
					{
						secondMostSharesNotOwnedByPlayer = gamePlayer
								.getStockRegistry().getNumberOfShares(
										hotelChain);
					}
				}
			}

			if (player.getStockRegistry().getNumberOfShares(hotelChain) >= maxSharesNotOwnedByPlayer)
			{
				Integer value = 5 + ((player.getStockRegistry()
						.getNumberOfShares(hotelChain) - maxSharesNotOwnedByPlayer) * 2);
				HotelValue hotelValue = new HotelValue(value, hotelChain);
				hotelValueQueue.add(hotelValue);
			}
			else if (player.getStockRegistry().getNumberOfShares(hotelChain) >= secondMostSharesNotOwnedByPlayer)
			{
				Integer value = 3
						+ player.getStockRegistry().getNumberOfShares(
								hotelChain) - secondMostSharesNotOwnedByPlayer;
				HotelValue hotelValue = new HotelValue(value, hotelChain);
				hotelValueQueue.add(hotelValue);
			}
			else if ((secondMostSharesNotOwnedByPlayer - player
					.getStockRegistry().getNumberOfShares(hotelChain)) < 3
					&& BankStockRegistry.getInstance().getNumberOfShares(
							hotelChain) > 2)
			{
				Integer value = 2;
				HotelValue hotelValue = new HotelValue(value, hotelChain);
				hotelValueQueue.add(hotelValue);
			}
			else
			{

				Integer value = -2;
				HotelValue hotelValue = new HotelValue(value, hotelChain);
				hotelValueQueue.add(hotelValue);
			}
		}

		return hotelValueQueue.remove();
	}
}
