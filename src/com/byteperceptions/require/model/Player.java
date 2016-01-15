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

import java.util.ArrayList;
import java.util.List;

import com.byteperceptions.require.gamestate.GameState;
import com.byteperceptions.require.gamestate.PlayerGameState;


/**
 * @author Ryan Ylitalo
 *
 */
public abstract class Player
{
	private String name;
	private StockRegistry stockRegistry;
	private int cash;
	private List<Tile> tiles;

	/**
	 * @param string
	 */
	public Player(String name)
	{
		this.name = name;
		cash = 6000;
		stockRegistry = new PersonalStockRegistry();
		tiles = new ArrayList<Tile>(6);
		initializeTileRack();
	}

	/**
	 * @return the tiles
	 */
	public List<Tile> getTiles()
	{
		return tiles;
	}

	public void addTile(Tile tile)
	{
		tiles.add(tile);
	}

	/**
	 * @param mainPlayer
	 */
	private void initializeTileRack()
	{
		for (int i = 0; i < 6; i++)
		{
			addTile(TileRegistry.getInstance().selectChipFromBag());
		}

	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the stockRegistry
	 */
	public StockRegistry getStockRegistry()
	{
		return stockRegistry;
	}

	/**
	 * @param stockRegistry
	 *            the stockRegistry to set
	 */
	public void setStockRegistry(StockRegistry stockRegistry)
	{
		this.stockRegistry = stockRegistry;
	}

	/**
	 * @return the cash
	 */
	public int getCash()
	{
		return cash;
	}

	/**
	 * @param cash
	 *            the cash to set
	 */
	public void setCash(int cash)
	{
		this.cash = cash;
	}

	public abstract void takeTurn();

	/**
	 * @param majorityBonus
	 */
	public void addCash(int majorityBonus)
	{
		this.cash += majorityBonus;
	}

	@Override
	public String toString()
	{
		return name;
	}

	/**
	 * @return
	 */
	public abstract HotelChain selectHotelChainToStart(
			List<HotelChain> availableHotels);

	/**
	 * Purchase Stocks during turn
	 */
	public abstract List<StockShare> purchaseStocks();

	/**
	 * @return
	 */
	public abstract HotelChain selectSurvivingMergerChain(Tile tile);

	/**
	 * @param parentCompany
	 * @param hotelChain
	 */
	public abstract void transactMergerStocks(HotelChain mergerSurvivor,
			HotelChain mergerDeceased);

	/**
	 * @return
	 */
	public abstract boolean shouldEndGame();

	public abstract HotelChain selectHotelToMergeFirst(HotelChain firstChain,
			HotelChain secondChain);

	public void captureGameState(GameState gameState)
	{
		PlayerGameState playerGameState = new PlayerGameState(this);
		gameState.addPlayerGameState(playerGameState);
	}

	public void restoreGameState(GameState gameState)
	{
		PlayerGameState playerGameState = gameState.getPlayerGameState(this);
		this.getStockRegistry().clear();
		for(HotelChain chain : HotelChain.values())
		{
			int numberOfShares = playerGameState.getNumberOfShares(chain);
			for(int i = 0; i < numberOfShares; i++)
			{
				BankStockRegistry.getInstance().issueFreeStock(chain, this);
			}
		}
		
		this.tiles.clear();
		
		for(Tile tile : playerGameState.getTiles())
		{
			this.tiles.add(tile);
		}
		
		this.cash = playerGameState.getCash();
	}
	
}
