/**
 * 
 */
package com.byteperceptions.require.strategy;

import java.util.List;

import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.model.StockShare;
import com.byteperceptions.require.model.Tile;

/**
 * @author JDev
 * 
 */
public interface PlayerStrategy
{
	public Tile playTile(Player player);

	public List<StockShare> purchaseStocks(Player player);

	public HotelChain selectHotelChainToStart(Player player,
			List<HotelChain> availableHotels);

	public HotelChain selectSurvivingMergerChain(Player player, Tile tile);

	public void transactStocksAfterMerger(Player player,
			HotelChain mergerSurvivor, HotelChain mergerDeceased);

	public boolean shouldEndGame(Player player);

	public HotelChain selectHotelToMergeFirst(HotelChain firstChain,
			HotelChain secondChain);
}
