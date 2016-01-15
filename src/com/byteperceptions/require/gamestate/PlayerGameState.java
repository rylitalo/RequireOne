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
package com.byteperceptions.require.gamestate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.model.Tile;


/**
 * @author Ryan Ylitalo
 *
 */
public class PlayerGameState
{

	private HashMap<HotelChain, Integer> numberOfSharesByHotelChain;
	private int cash;
	private List<Tile> tiles;
	private Player player;

	public PlayerGameState(Player player)
	{
		this.player = player;
		numberOfSharesByHotelChain = new HashMap<HotelChain, Integer>();
		tiles = new ArrayList<Tile>();
		cash = player.getCash();

		for (HotelChain chain : HotelChain.values())
		{
			numberOfSharesByHotelChain.put(chain, player.getStockRegistry()
					.getNumberOfShares(chain));
		}

		for (Tile tile : player.getTiles())
		{
			tiles.add(tile);
		}
	}

	public int getNumberOfShares(HotelChain chain)
	{
		return numberOfSharesByHotelChain.get(chain);
	}

	public Player getPlayer()
	{
		return player;
	}

	public List<Tile> getTiles()
	{
		return tiles;
	}

	public int getCash()
	{
		return cash;
	}

}
