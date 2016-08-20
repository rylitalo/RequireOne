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
package com.byteperceptions.require.turn;

import java.util.List;

import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.model.StockShare;
import com.byteperceptions.require.model.Tile;


/**
 * @author Ryan Ylitalo
 *
 */
public class Turn
{

	private Player player;
	private List<StockShare> purchasedShares;
	private Tile tile;

	public Turn(Player player, List<StockShare> purchasedShares, Tile tile)
	{
		this.player = player;
		this.purchasedShares = purchasedShares;
		this.tile = tile;
	}

	public Player getPlayer()
	{
		return player;
	}

	public List<StockShare> getPurchasedShares()
	{
		return purchasedShares;
	}

	public Tile getTile()
	{
		return tile;
	}

	@Override
	public String toString()
	{
		return tile + " : " + player;
	}

}
