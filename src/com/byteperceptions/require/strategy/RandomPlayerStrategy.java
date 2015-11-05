/**
 * 
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
 * @author JDev
 * 
 */
public class RandomPlayerStrategy implements PlayerStrategy
{

	private static final Random random = new Random();

	/**
	 * @see com.byteperceptions.require.strategy.PlayerStrategy#playTile(com.byteperceptions.require.model.Player)
	 */
	@Override
	public Tile playTile(Player player)
	{
		Tile tile = null;
		for (int i = 0; i < 6; i++)
		{
			if (player.getTiles().get(i).isPlayable())
			{
				tile = player.getTiles().remove(i);
				break;
			}
		}
		if (tile != null)
		{
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
		for (int i = 0; i < 3; i++)
		{
			List<HotelChain> activeHotelChains = TileRegistry.getInstance()
				.getActiveHotelChains();
			int numberOfActiveHotels = activeHotelChains.size();
			if (numberOfActiveHotels > 0)
			{
				HotelChain hotelChain = activeHotelChains.get(random
					.nextInt(numberOfActiveHotels));
				StockShare stockShare = purchaseShare(hotelChain, player);
				purchasedShares.add(stockShare);
			}
		}
		
		return purchasedShares;
	}

	private StockShare purchaseShare(HotelChain hotelChain, Player player)
	{
		if (hotelChain.getSize() > 0
			&& BankStockRegistry.getInstance().getNumberOfShares(hotelChain) > 0
			&& player.getCash() > hotelChain.getPrice())
		{
			return BankStockRegistry.getInstance().brokerSharePurchase(hotelChain,
				player);
		}
		
		return new StockShare(null);
	}

	/**
	 * @see com.byteperceptions.require.strategy.PlayerStrategy#selectHotelChainToStart(java.util.List)
	 */
	@Override
	public HotelChain selectHotelChainToStart(Player player,
		List<HotelChain> availableHotels)
	{
		for (int i = 20; i > 0; i--)
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
		return survivingChains.get(random.nextInt(survivingChains.size()));
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

	@Override
	public HotelChain selectHotelToMergeFirst(HotelChain firstChain,
			HotelChain secondChain)
	{
		// TODO Auto-generated method stub
		return firstChain;
	}
}
