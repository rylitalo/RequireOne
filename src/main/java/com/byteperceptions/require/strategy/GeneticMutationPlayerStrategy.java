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

import java.util.List;

import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.model.StockShare;
import com.byteperceptions.require.model.Tile;
import com.byteperceptions.require.model.TileRegistry;


/**
 * @author Ryan Ylitalo
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
