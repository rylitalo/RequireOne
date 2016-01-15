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

import java.awt.Color;

import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Tile;


/**
 * @author Ryan Ylitalo
 *
 */
public class TileState
{

	private boolean alreadyPlayed;
	private HotelChain currentHotelChain;
	private Tile tile;
	private Color backgroundColor;
	private Color foregroundColor;

	public TileState(Tile tile)
	{
		this.alreadyPlayed = tile.isAlreadyPlayed();
		this.currentHotelChain = tile.getHotelChain();
		this.foregroundColor = tile.getChipBoardButton().getForeground();
		this.backgroundColor = tile.getChipBoardButton().getBackground();
		this.tile = tile;
	}

	public boolean isAlreadyPlayed()
	{
		return alreadyPlayed;
	}

	public HotelChain getCurrentHotelChain()
	{
		return currentHotelChain;
	}

	public Tile getTile()
	{
		return tile;
	}

	public Color getBackgroundColor()
	{
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor)
	{
		this.backgroundColor = backgroundColor;
	}

	public Color getForegroundColor()
	{
		return foregroundColor;
	}

	public void setForegroundColor(Color foregroundColor)
	{
		this.foregroundColor = foregroundColor;
	}
}
