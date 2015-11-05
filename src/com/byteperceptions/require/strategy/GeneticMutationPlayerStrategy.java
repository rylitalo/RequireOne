/**
 * 
 */
package com.byteperceptions.require.strategy;

import java.util.List;

import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.model.StockShare;
import com.byteperceptions.require.model.Tile;
import com.byteperceptions.require.model.TileRegistry;
import com.byteperceptions.require.strategy.scenario.GeneticMutationEngine;

/**
 * @author JDev
 * 
 */
public class GeneticMutationPlayerStrategy implements PlayerStrategy
{

	/**
	 * @see com.byteperceptions.require.strategy.PlayerStrategy#playTile(com.byteperceptions.require.model.Player)
	 */
	@Override
	public Tile playTile(Player player)
	{
		return GeneticMutationEngine.getInstance().playTile(player);
	}

	/**
	 * @see com.byteperceptions.require.strategy.PlayerStrategy#purchaseStocks(com.byteperceptions.require.model.Player)
	 */
	@Override
	public List<StockShare> purchaseStocks(Player player)
	{
		return GeneticMutationEngine.getInstance().purchaseStockShares(player);
	}


	/**
	 * @see com.byteperceptions.require.strategy.PlayerStrategy#selectHotelChainToStart(java.util.List)
	 */
	@Override
	public HotelChain selectHotelChainToStart(Player player,
			List<HotelChain> availableHotels)
	{
		return GeneticMutationEngine.getInstance().selectHotelChainToStart(player);
		
	}

	/**
	 * @see com.byteperceptions.require.strategy.PlayerStrategy#selectSurvivingMergerChain(com.byteperceptions.require.model.Tile)
	 */
	@Override
	public HotelChain selectSurvivingMergerChain(Player player, Tile tile)
	{
		return GeneticMutationEngine.getInstance().selectSurvivingMergerChain(player, tile);
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
		GeneticMutationEngine.getInstance().transactStocksAfterMerger(player, mergerSurvivor, mergerDeceased);
	}

	@Override
	public HotelChain selectHotelToMergeFirst(HotelChain firstChain,
			HotelChain secondChain)
	{
		// TODO Auto-generated method stub
		return firstChain;
	}
}
