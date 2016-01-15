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


/**
 * @author Ryan Ylitalo
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
