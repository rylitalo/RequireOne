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
package com.byteperceptions.require.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.byteperceptions.require.model.BankStockRegistry;
import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.model.StockShare;
import com.byteperceptions.require.model.Tile;
import com.byteperceptions.require.model.TileRegistry;


/**
 * @author Ryan Ylitalo
 *
 */
public class RyanPlayerStrategy implements PlayerStrategy
{

	private static final Random random = new Random();

	/**
	 * @see com.byteperceptions.require.strategy.PlayerStrategy#playTile(com.byteperceptions.require.model.Player)
	 */
	@Override
	public Tile playTile(Player player)
	{
		// Play starter Chip if you have one.
		Tile tile = findStarterTile(player);
		if (tile != null)
		{
			// There is a company to start.
			if (TileRegistry.getInstance().getAvailableHotelChains().size() > 0)
			{
				player.getTiles().remove(tile);
				tile.playTile();
				return tile;
			}
		}

		// Find All Playable Tiles
		List<Tile> allTiles = player.getTiles();
		List<Tile> playableTiles = new ArrayList<Tile>();
		for (int i = 0; i < allTiles.size(); i++)
		{
			Tile indexTile = allTiles.get(i);
			if (indexTile.isPlayable())
			{
				playableTiles.add(indexTile);
			}
		}

		// See if you have a merger that you are the majority
		List<Tile> mergerChips = new ArrayList<Tile>();
		for (int i = 0; i < playableTiles.size(); i++)
		{
			Tile mergerChip = playableTiles.get(i);
			if (TileRegistry.getInstance().isMergerChip(mergerChip))
			{
				mergerChips.add(mergerChip);
			}
		}

		// Need to figure out best Merger.
		for (int i = 0; i < mergerChips.size(); i++)
		{
			Tile mergerChip = mergerChips.get(i);
			List<HotelChain> potentiallyDeceasedChain = TileRegistry
					.getInstance().getPotentiallyDeceasedChain(mergerChip);
			for (HotelChain chain : potentiallyDeceasedChain)
			{
				if (chain.getMajorityStockHolders().contains(player))
				{
					tile = mergerChip;
					break;
				}
			}
		}

		if (tile == null)
		{
			for (int i = 0; i < mergerChips.size(); i++)
			{
				Tile mergerChip = mergerChips.get(i);
				List<HotelChain> potentiallyDeceasedChain = TileRegistry
						.getInstance().getPotentiallyDeceasedChain(mergerChip);
				for (HotelChain chain : potentiallyDeceasedChain)
				{
					if (chain.getMinorityStockHolders().contains(player))
					{
						tile = mergerChip;
						break;
					}
				}
			}
		}

		if (tile == null)
		{
			if (playableTiles.size() > 0)
			{
				tile = playableTiles.get(0);
			}
		}

		if (tile != null)
		{
			player.getTiles().remove(tile);
			tile.playTile();
		}

		return tile;
	}

	/**
	 * @see com.byteperceptions.require.strategy.PlayerStrategy#purchaseStocks(com.byteperceptions.require.model.Player)
	 */
	@Override
	public List<StockShare> purchaseStocks(Player player)
	{
		List<StockShare> purchasedShares = new ArrayList<StockShare>(3);
		List<HotelChain> activeHotelChains = TileRegistry.getInstance()
				.getActiveHotelChains();

		HotelChain maxStocks = null;
		HotelChain secondMostStocks = null;

		for (HotelChain chain : activeHotelChains)
		{
			if (maxStocks == null
					&& BankStockRegistry.getInstance().getNumberOfShares(chain) > 0
					&& player.getStockRegistry().getNumberOfShares(chain) < 8)
			{
				maxStocks = chain;
				secondMostStocks = chain;
			}
			else
			{
				if (player.getStockRegistry().getNumberOfShares(chain) > player
						.getStockRegistry().getNumberOfShares(chain)
						&& BankStockRegistry.getInstance().getNumberOfShares(
								chain) > 0
						&& player.getStockRegistry().getNumberOfShares(chain) < 8)
				{
					secondMostStocks = maxStocks;
					maxStocks = chain;
				}
			}
		}

		for (int i = 0; i < 3; i++)
		{

			int numberOfActiveHotels = activeHotelChains.size();
			if (numberOfActiveHotels > 0)
			{
				if (maxStocks != null)
				{
					if (player.getStockRegistry().getNumberOfShares(maxStocks) < 8
							&& BankStockRegistry.getInstance()
									.getNumberOfShares(maxStocks) > 0
							&& player.getCash() >= maxStocks.getPrice())
					{
						StockShare share = purchaseShare(maxStocks, player);
						purchasedShares.add(share);
					}
					else if (player.getStockRegistry().getNumberOfShares(
							secondMostStocks) < 8
							&& BankStockRegistry.getInstance()
									.getNumberOfShares(secondMostStocks) > 0
							&& player.getCash() >= secondMostStocks.getPrice())
					{
						StockShare share = purchaseShare(secondMostStocks,
								player);
						purchasedShares.add(share);
					}
					else
					{
						for (HotelChain chain : activeHotelChains)
						{
							if (player.getStockRegistry().getNumberOfShares(
									chain) < 8
									&& BankStockRegistry.getInstance()
											.getNumberOfShares(chain) > 0
									&& player.getCash() >= chain.getPrice())
							{
								StockShare share = purchaseShare(chain, player);
								purchasedShares.add(share);
								break;
							}
						}
					}
				}
				// Find Shares of Something for which you can compete.
				else
				{
					// TODO - This needs to be changed to a non-random scenarios
					if (numberOfActiveHotels > 0)
					{
						HotelChain hotelChain = activeHotelChains.get(random
								.nextInt(numberOfActiveHotels));
						if (player.getCash() >= hotelChain.getPrice() && BankStockRegistry.getInstance()
								.getNumberOfShares(hotelChain) > 0)
						{
							StockShare stockShare = purchaseShare(hotelChain,
									player);
							purchasedShares.add(stockShare);
						}
					}
				}

			}
		}

		return purchasedShares;
	}

	/**
	 * @see com.byteperceptions.require.strategy.PlayerStrategy#selectHotelChainToStart(java.util.List)
	 */
	@Override
	public HotelChain selectHotelChainToStart(Player player,
			List<HotelChain> availableHotels)
	{
		for (int i = 25; i > 0; i--)
		{
			for (HotelChain chain : availableHotels)
			{
				if (player.getStockRegistry().getNumberOfShares(chain) == i)
				{
					return chain;
				}
			}
		}
		return availableHotels.get(0);
	}

	/**
	 * @see com.byteperceptions.require.strategy.PlayerStrategy#selectSurvivingMergerChain(com.byteperceptions.require.model.Tile)
	 */
	@Override
	public HotelChain selectSurvivingMergerChain(Player player, Tile tile)
	{

		List<HotelChain> survivingChains = TileRegistry.getInstance()
				.getSurvivingChain(tile);

		boolean foundBestMerger = false;
		if (survivingChains.size() == 1)
		{
			return survivingChains.get(0);
		}
		else
		{
			for (HotelChain chain : survivingChains)
			{
				if (chain.getMajorityStockHolders().contains(player))
				{
					survivingChains.remove(chain);
					foundBestMerger = true;
					break;
				}
			}

			if (!foundBestMerger)
			{
				for (HotelChain chain : survivingChains)
				{
					if (chain.getMinorityStockHolders().contains(player))
					{
						survivingChains.remove(chain);
						foundBestMerger = true;
						break;
					}
				}
			}

		}

		return survivingChains.get(0);
	}

	/**
	 * @see com.byteperceptions.require.strategy.PlayerStrategy#shouldEndGame(com.byteperceptions.require.model.Player)
	 */
	@Override
	public boolean shouldEndGame(Player player)
	{
		return TileRegistry.getInstance().canEndGame();
	}

	/**
	 * @see com.byteperceptions.require.strategy.PlayerStrategy#transactStocksAfterMerger(com.byteperceptions.require.model.Player,
	 *      com.byteperceptions.require.model.HotelChain,
	 *      com.byteperceptions.require.model.HotelChain)
	 */
	@Override
	public void transactStocksAfterMerger(Player player,
			HotelChain mergerSurvivor, HotelChain mergerDeceased)
	{
		if (random.nextInt() % 3 == 0)
		{
			int determineTradeAmount = determineMaxTradeAmount(player,
					mergerSurvivor, mergerDeceased);
			BankStockRegistry.getInstance().tradeStocks(player, mergerSurvivor,
					mergerDeceased, determineTradeAmount);
		}
	}

	private int determineMaxTradeAmount(Player player,
			HotelChain mergerSurvivor, HotelChain mergerDeceased)
	{
		return Math.min(player.getStockRegistry().getNumberOfShares(
				mergerDeceased) / 2, BankStockRegistry.getInstance()
				.getNumberOfShares(mergerSurvivor));
	}

	private Tile findStarterTile(Player player)
	{
		for (int i = 0; i < 6; i++)
		{
			Tile tile = player.getTiles().get(i);
			if (tile.isStarterChip() && tile.isPlayable())
			{
				return tile;
			}
		}

		return null;
	}

	private StockShare purchaseShare(HotelChain hotelChain, Player player)
	{
		if (hotelChain.getSize() > 0
				&& BankStockRegistry.getInstance()
						.getNumberOfShares(hotelChain) > 0
				&& player.getCash() >= hotelChain.getPrice())
		{
			return BankStockRegistry.getInstance().brokerSharePurchase(
					hotelChain, player);
		}

		throw new StockSharesUnavailableException(hotelChain);
	}

	@Override
	public HotelChain selectHotelToMergeFirst(HotelChain firstChain,
			HotelChain secondChain)
	{
		// TODO Auto-generated method stub
		return firstChain;
	}
}
