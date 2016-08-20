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
import java.util.List;

import com.byteperceptions.require.registry.PlayerRegistry;
import com.byteperceptions.require.turn.TurnRegistry;


/**
 * @author Ryan Ylitalo
 *
 */
public enum HotelChain
{
	LUXOR(Color.RED, 200, "Luxor"), TOWER(Color.YELLOW, 200, "Tower"), AMERICAN(
			Color.BLUE, 300, "American"), FESTIVAL(Color.GREEN, 300, "Festival"), WORLDWIDE(
			Color.ORANGE, 300, "Worldwide"), IMPERIAL(Color.PINK, 400,
			"Imperial"), CONTINENTAL(Color.CYAN, 400, "Continental");

	private Color color;
	private int basePrice;
	private String label;

	private HotelChain(Color color, int basePrice, String label)
	{
		this.color = color;
		this.basePrice = basePrice;
		this.label = label;
	}

	@Override
	public String toString()
	{
		return getLabel();
	}

	public Color getColor()
	{
		return color;
	}

	public String getLabel()
	{
		return label;
	}

	public int getSize()
	{
		int counter = 0;
		for (Tile tile : new TileRegistryIterator())
		{
			if (this.equals(tile.getHotelChain()))
			{
				counter++;
			}
		}
		return counter;
	}

	public void mergeHotelChain(HotelChain parentCompany)
	{
		if (!this.equals(parentCompany))
		{
			payoffMajorityMinorityStockHolders();
			List<Player> transactMergerStocksOrder = TurnRegistry.getInstance()
					.getMergerTransactionOrder();
			for (Player player : transactMergerStocksOrder)
			{
				player.transactMergerStocks(parentCompany, this);
			}

			for (Tile tile : new TileRegistryIterator())
			{
				if (this.equals(tile.getHotelChain()))
				{
					tile.setHotelChain(parentCompany);
					tile.getChipBoardButton().setBackground(
							parentCompany.getColor());
					if(parentCompany == HotelChain.AMERICAN)
					{
						tile.getChipBoardButton().setForeground(Color.WHITE);
					}
					else{
						tile.getChipBoardButton().setForeground(Color.BLACK);
					}
				}
			}

			TileRegistry.getInstance().addAvailableHotelChain(this);
		}
	}

	/**
	 * 
	 */
	public void payoffMajorityMinorityStockHolders()
	{
		List<Player> majorityOwners = getMajorityStockHolders();

		if (majorityOwners.size() > 1)
		{
			payBonus(majorityOwners, (getMajorityBonus() + getMinorityBonus()));
		}
		else
		{
			payBonus(majorityOwners, getMajorityBonus());
			if (getMinorityStockHolders().size() == 0)
			{
				// No Minority Holder - pay minority bonus to Majority Holder.
				payBonus(majorityOwners, getMinorityBonus());
			}
			else
			{
				payBonus(getMinorityStockHolders(), getMinorityBonus());
			}
		}
	}

	private void payBonus(List<Player> majorityOwners, int bonus)
	{
		int majorityBonus = bonus / majorityOwners.size();

		if ((majorityBonus % 100) != 0)
		{
			majorityBonus = majorityBonus + (100 - (majorityBonus % 100));
		}

		for (Player player : majorityOwners)
		{
			player.addCash(majorityBonus);
		}
	}

	/**
	 * 
	 */
	public int determinePayoffForPlayer(Player player)
	{
		List<Player> majorityOwners = getMajorityStockHolders();

		CashBag cashContainer = new CashBag();

		if (majorityOwners.size() > 1)
		{
			determinePlayerBonus(player, cashContainer, majorityOwners,
					(getMajorityBonus() + getMinorityBonus()));
		}
		else
		{
			determinePlayerBonus(player, cashContainer, majorityOwners,
					getMajorityBonus());
			if (getMinorityStockHolders().size() == 0)
			{
				// No Minority Holder - pay minority bonus to Majority Holder.
				determinePlayerBonus(player, cashContainer, majorityOwners,
						getMinorityBonus());
			}
			else
			{
				determinePlayerBonus(player, cashContainer,
						getMinorityStockHolders(), getMinorityBonus());
			}
		}
		
		return cashContainer.getCash();
	}

	private void determinePlayerBonus(Player player, CashBag cashContainer,
			List<Player> majorityOwners, int bonus)
	{
		int majorityBonus = bonus / majorityOwners.size();

		if ((majorityBonus % 100) != 0)
		{
			majorityBonus = majorityBonus + (100 - (majorityBonus % 100));
		}

		for (Player playerReceivingBonus : majorityOwners)
		{
			if (playerReceivingBonus.equals(player))
			{
				cashContainer.addCash(majorityBonus);
			}
		}
	}

	/**
	 * @return
	 */
	private int getMajorityBonus()
	{
		return getPrice() * 10;
	}

	/**
	 * @return
	 */
	private int getMinorityBonus()
	{
		return getPrice() * 5;
	}

	/**
	 * @return
	 */
	public int getPrice()
	{
		int chainSize = getSize();
		if (chainSize == 0)
		{
			return 0;
		}

		if (chainSize <= 6)
		{
			return (basePrice + (100 * (chainSize - 2)));
		}
		else if (chainSize > 6 && chainSize <= 10)
		{
			return basePrice + 100 * 4;
		}
		else if (chainSize > 10 && chainSize <= 20)
		{
			return basePrice + 100 * 5;
		}
		else if (chainSize > 20 && chainSize <= 30)
		{
			return basePrice + 100 * 6;
		}
		else if (chainSize > 30 && chainSize <= 40)
		{
			return basePrice + 100 * 7;
		}
		else
		// if(chainSize > 40 )
		{
			return basePrice + 100 * 8;
		}

	}

	public List<Player> getMajorityStockHolders()
	{

		int numberOfStocksForMajority = getNumberOfStocksForMajority();

		List<Player> majorityOwners = new ArrayList<Player>();
		for (Player player : PlayerRegistry.getInstance().getAllPlayers())
		{
			if (player.getStockRegistry().getNumberOfShares(this) == numberOfStocksForMajority)
			{
				majorityOwners.add(player);
			}
		}

		return majorityOwners;
	}

	public int getNumberOfStocksForMajority()
	{
		int numberOfStocksForMajority = 0;
		for (Player player : PlayerRegistry.getInstance().getAllPlayers())
		{
			numberOfStocksForMajority = Math.max(numberOfStocksForMajority,
					player.getStockRegistry().getNumberOfShares(this));
		}
		return numberOfStocksForMajority;
	}

	
	public List<Player> getMinorityStockHolders()
	{
		List<Player> minorityStockHolders = new ArrayList<Player>();

		if (getMajorityStockHolders().size() > 1)
		{
			return minorityStockHolders;
		}

		int numberOfStocksForMinority = getNumberOfStocksForMinority();
		if (numberOfStocksForMinority == 0)
		{
			return minorityStockHolders;
		}

		for (Player player : PlayerRegistry.getInstance().getAllPlayers())
		{
			if (player.getStockRegistry().getNumberOfShares(this) == numberOfStocksForMinority)
			{
				minorityStockHolders.add(player);
			}
		}

		return minorityStockHolders;

	}

	public int getNumberOfStocksForMinority()
	{

		List<Player> majorityStockHolders = getMajorityStockHolders();
		int numberOfStocksForMinority = 0;
		for (Player player : PlayerRegistry.getInstance().getAllPlayers())
		{
			if (!majorityStockHolders.contains(player))
			{
				numberOfStocksForMinority = Math.max(numberOfStocksForMinority,
						player.getStockRegistry().getNumberOfShares(this));
			}
		}
		return numberOfStocksForMinority;
	}

	private class CashBag
	{
		private int cash = 0;
		
		public void addCash(int amount)
		{
			cash += amount;
		}

		public int getCash()
		{
			return cash;
		}
	}

	public static HotelChain getLargestChain()
	{
		int maxSize = 0;
		HotelChain largestChain = null;
		for(HotelChain chain : values()){
			if(chain.getSize() > maxSize)
			{
				maxSize = chain.getSize();
				largestChain = chain;
			}
		}
		return largestChain;
	}

}