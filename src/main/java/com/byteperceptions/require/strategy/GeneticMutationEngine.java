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
public class GeneticMutationEngine
{
	private static final GeneticMutationEngine INSTANCE = new GeneticMutationEngine();

	private GeneticMutationEngine()
	{
	}

	public static GeneticMutationEngine getInstance()
	{
		return INSTANCE;
	}

	public List<StockShare> purchaseStockShares(Player player)
	{
		throw new RuntimeException("Method not implemented.");
	}

	public Tile playTile(Player player)
	{
		throw new RuntimeException("Method not implemented.");
	}

	public HotelChain selectHotelChainToStart(Player player)
	{
		throw new RuntimeException("Method not implemented.");
	}

	public void transactStocksAfterMerger(Player player,
			HotelChain mergerSurvivor, HotelChain mergerDeceased)
	{
		throw new RuntimeException("Method not implemented.");
	}

	public HotelChain selectSurvivingMergerChain(Player player, Tile tile)
	{
		throw new RuntimeException("Method not implemented.");
	}

}
