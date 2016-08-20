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

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.byteperceptions.require.gamestate.GameState;
import com.byteperceptions.require.gamestate.TileRegistryState;
import com.byteperceptions.require.gamestate.TileState;


/**
 * @author Ryan Ylitalo
 *
 */
public class TileRegistry
{
	private static final TileRegistry INSTANCE = new TileRegistry();
	private static final Random randomNumberGenerator = new Random(System
			.currentTimeMillis());

	private List<Tile> chipBag;
	private HashMap<Integer, Tile> tileBoard;
	private ArrayList<Tile> iterableTileList;
	private List<HotelChain> availableHotelChains;
	private List<HotelChain> activeHotelChains;

	private TileRegistry()
	{
		initialize();
	}

	private void initialize()
	{
		chipBag = new ArrayList<Tile>();
		tileBoard = new HashMap<Integer, Tile>();
		iterableTileList = new ArrayList<Tile>();
		availableHotelChains = new ArrayList<HotelChain>();
		activeHotelChains = new ArrayList<HotelChain>();

		for (int letter = 0; letter < 9; letter++)
		{
			for (int i = 1; i <= 12; i++)
			{
				Tile tile = new Tile(i, letter);
				chipBag.add(tile);
				iterableTileList.add(tile);
				tileBoard.put(tile.hashCode(), tile);
			}
		}

		availableHotelChains.add(HotelChain.LUXOR);
		availableHotelChains.add(HotelChain.TOWER);
		availableHotelChains.add(HotelChain.WORLDWIDE);
		availableHotelChains.add(HotelChain.FESTIVAL);
		availableHotelChains.add(HotelChain.AMERICAN);
		availableHotelChains.add(HotelChain.CONTINENTAL);
		availableHotelChains.add(HotelChain.IMPERIAL);
	}

	public static TileRegistry getInstance()
	{
		return INSTANCE;
	}

	Iterator<Tile> getTileBoardIterator()
	{
		return iterableTileList.iterator();
	}

	/**
	 * Returns a random tile from the tile bag. If tile bag is empty, returns
	 * null.
	 * 
	 * @return
	 */
	public Tile selectChipFromBag()
	{
		if (chipBag.size() > 0)
		{
			return chipBag
					.remove(randomNumberGenerator.nextInt(chipBag.size()));
		}

		return null;
	}

	/**
	 * @return
	 */
	public boolean hasAdjoiningTileBeenPlayed(Tile tile)
	{
		List<Tile> adjoiningTiles = getAdjoiningTiles(tile);
		for (Tile adjoiningTile : adjoiningTiles)
		{
			if (adjoiningTile.isAlreadyPlayed())
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * @return
	 */
	public List<HotelChain> getAdjoiningHotelChains(Tile tile)
	{
		List<HotelChain> adjoiningHotelChains = new ArrayList<HotelChain>();
		List<Tile> adjoiningTiles = getAdjoiningTiles(tile);
		for (Tile adjoiningTile : adjoiningTiles)
		{
			if (adjoiningTile.isAlreadyPlayed())
			{
				if (adjoiningTile.getHotelChain() != null)
				{
					if (!adjoiningHotelChains.contains(adjoiningTile
							.getHotelChain()))
					{
						adjoiningHotelChains.add(adjoiningTile.getHotelChain());
					}
				}
			}
		}

		return adjoiningHotelChains;
	}

	/**
	 * @return
	 */
	public List<HotelChain> getAvailableHotelChains()
	{
		return availableHotelChains;
	}

	/**
	 * @param tile
	 * @param hotelChain
	 */
	public void paintAdjoiningTiles(Tile tile, HotelChain hotelChain)
	{
		Color color = hotelChain.getColor();
		tile.getChipBoardButton().setBackground(color);
		if (hotelChain == HotelChain.AMERICAN)
		{
			tile.getChipBoardButton().setForeground(Color.WHITE);
		}

		List<Tile> adjoiningTiles = getAdjoiningTiles(tile);
		for (Tile adjoiningTile : adjoiningTiles)
		{
			if (adjoiningTile.isAlreadyPlayed())
			{
				adjoiningTile.setHotelChain(hotelChain);
				adjoiningTile.getChipBoardButton().setBackground(color);
				if (hotelChain == HotelChain.AMERICAN)
				{
					adjoiningTile.getChipBoardButton().setForeground(
							Color.WHITE);
				}
			}
		}
	}

	/**
	 * @param tile
	 * @return
	 */
	public List<Tile> getAdjoiningTiles(Tile tile)
	{
		List<Tile> adjoiningTiles = new ArrayList<Tile>();
		Tile leftTile = tileBoard.get(TileDirection.LEFT
				.getAdjoiningHashCode(tile));
		if (leftTile != null)
		{
			adjoiningTiles.add(leftTile);
		}

		Tile rightTile = tileBoard.get(TileDirection.RIGHT
				.getAdjoiningHashCode(tile));
		if (rightTile != null)
		{
			adjoiningTiles.add(rightTile);
		}

		Tile upperTile = tileBoard.get(TileDirection.UP
				.getAdjoiningHashCode(tile));
		if (upperTile != null)
		{
			adjoiningTiles.add(upperTile);
		}

		Tile lowerTile = tileBoard.get(TileDirection.DOWN
				.getAdjoiningHashCode(tile));
		if (lowerTile != null)
		{
			adjoiningTiles.add(lowerTile);
		}

		return adjoiningTiles;
	}

	/**
	 * @param tile
	 * @return
	 */
	public boolean isMergerChip(Tile tile)
	{
		if (getAdjoiningHotelChains(tile).size() > 1)
		{
			return true;
		}
		return false;
	}

	/**
	 * @param tile
	 * @return
	 */
	public List<HotelChain> getSurvivingChain(Tile tile)
	{
		List<HotelChain> adjoiningHotelChains = getAdjoiningHotelChains(tile);
		assert (adjoiningHotelChains.size() > 1);

		List<HotelChain> largestChain = new ArrayList<HotelChain>();
		for (HotelChain hotelChain : adjoiningHotelChains)
		{
			if (largestChain.size() == 0)
			{
				largestChain.add(hotelChain);
			}
			else
			{
				if (largestChain.get(0).getSize() < hotelChain.getSize())
				{
					largestChain.clear();
					largestChain.add(hotelChain);
				}
				else if (largestChain.get(0).getSize() == hotelChain.getSize())
				{
					largestChain.add(hotelChain);
				}
			}
		}
		return largestChain;
	}

	public List<HotelChain> getPotentiallyDeceasedChain(Tile tile)
	{
		List<HotelChain> adjoiningHotelChains = getAdjoiningHotelChains(tile);

		List<HotelChain> largestChain = new ArrayList<HotelChain>();
		for (HotelChain hotelChain : adjoiningHotelChains)
		{
			if (largestChain.size() == 0)
			{
				largestChain.add(hotelChain);
			}
			else
			{
				if (largestChain.get(0).getSize() < hotelChain.getSize())
				{
					largestChain.clear();
					largestChain.add(hotelChain);
				}
				else if (largestChain.get(0).getSize() == hotelChain.getSize())
				{
					largestChain.add(hotelChain);
				}
			}
		}

		// More than 1 largest chain - all chains could be potentially deceased
		if (largestChain.size() == 1)
		{
			adjoiningHotelChains.remove(largestChain.get(0));
		}
		return adjoiningHotelChains;
	}

	public void addAvailableHotelChain(HotelChain hotelChain)
	{
		availableHotelChains.add(hotelChain);
		activeHotelChains.remove(hotelChain);
	}

	public void removeDeadChips()
	{
		List<Tile> deadChips = new ArrayList<Tile>();
		for (Tile tile : chipBag)
		{
			if (isDeadChip(tile))
			{
				// This is a dead chip
				tile.getChipBoardButton().setBackground(Color.DARK_GRAY);
				tile.getChipBoardButton().setForeground(Color.WHITE);
				deadChips.add(tile);
			}
		}

		// Remove all dead chips from bag.
		for (Tile tile : deadChips)
		{
			chipBag.remove(tile);
		}
	}

	public boolean isDeadChip(Tile tile)
	{
		List<HotelChain> adjoiningHotelChainsOverTenBig = new ArrayList<HotelChain>(
				7);
		List<HotelChain> adjoiningHotelChains = getAdjoiningHotelChains(tile);
		for (HotelChain adjoiningHotelChain : adjoiningHotelChains)
		{
			if (adjoiningHotelChain.getSize() > 10)
			{
				adjoiningHotelChainsOverTenBig.add(adjoiningHotelChain);
			}
		}
		if (adjoiningHotelChainsOverTenBig.size() > 1)
		{
			return true;
		}

		return false;
	}

	/**
	 * A method to determine if the game can be ended.
	 * 
	 * @return
	 */
	public boolean canEndGame()
	{
		boolean areAllCompaniesSafe = true;
		boolean isThereAtLeastOneCompanyOnTheBoard = TileRegistry.getInstance()
				.getActiveHotelChains().size() > 0;
		if (!isThereAtLeastOneCompanyOnTheBoard)
		{
			return false;
		}

		for (HotelChain activeChain : TileRegistry.getInstance()
				.getActiveHotelChains())
		{
			if (activeChain.getSize() > 40)
			{
				return true;
			}
			else if (activeChain.getSize() < 11)
			{
				areAllCompaniesSafe = false;
			}
		}
		return areAllCompaniesSafe;
	}

	/**
	 * @return
	 */
	public List<HotelChain> getActiveHotelChains()
	{
		return activeHotelChains;
	}

	public List<HotelChain> getActiveHotelChainsWithSharesForPurchase()
	{
		List<HotelChain> chainsWithSharesForPurchase = new ArrayList<HotelChain>();
		for (HotelChain chain : activeHotelChains)
		{
			if (BankStockRegistry.getInstance().getNumberOfShares(chain) > 0)
			{
				chainsWithSharesForPurchase.add(chain);
			}
		}
		return chainsWithSharesForPurchase;
	}

	public void clear()
	{
		initialize();
	}

	public void restoreGameState(GameState gameState)
	{
		TileRegistryState tileRegistryState = gameState.getTileRegistryState();
		
		chipBag.clear();
		for(Tile tile : tileRegistryState.getChipBagTiles())
		{
			chipBag.add(tile);
		}
		
		for(TileState tileState : tileRegistryState.getTileStates())
		{
			tileState.getTile().restoreGameState(tileState);
		}
		
		activeHotelChains.clear();
		activeHotelChains.addAll(tileRegistryState.getActiveChains());
		
		availableHotelChains.clear();
		availableHotelChains.addAll(tileRegistryState.getAvailableChains());
		
	}

	public void captureGameState(GameState gameState)
	{
		TileRegistryState tileRegistryState = new TileRegistryState();
		List<TileState> tileStates = new ArrayList<TileState>();
		for (Tile tile : iterableTileList)
		{
			tileStates.add(new TileState(tile));
		}

		tileRegistryState.setTileStates(tileStates);
		List<Tile> chipBagTileStates = new ArrayList<Tile>();
		for (Tile chipBagTile : chipBag)
		{
			chipBagTileStates.add(chipBagTile);
		}
		tileRegistryState.setChipBagTiles(chipBagTileStates);

		List<HotelChain> availableChainStates = new ArrayList<HotelChain>(7);
		availableChainStates.addAll(availableHotelChains);
		tileRegistryState.setAvailableChains(availableChainStates);
		
		List<HotelChain> activeChainStates = new ArrayList<HotelChain>(7);
		activeChainStates.addAll(activeHotelChains);
		tileRegistryState.setActiveChains(activeChainStates);
		
		gameState.setTileRegistryState(tileRegistryState);
	}
}
