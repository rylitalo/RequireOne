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
package com.byteperceptions.require.gui.tilerack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import com.byteperceptions.require.RequireChangeListener;
import com.byteperceptions.require.model.Tile;


/**
 * @author Ryan Ylitalo
 *
 */
public class TileButton extends JButton implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private Tile tile;
	private List<TileRackChangeListener> listeners = new ArrayList<TileRackChangeListener>();

	/**
	 * @return the tile
	 */
	public Tile getTile()
	{
		return tile;
	}

	public TileButton(Tile tile)
	{
		super(tile.getButtonLabel());
		this.tile = tile;
		this.addActionListener(this);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		tile.playTile();
		RequireChangeListener.getInstance().fireBoardChangedEvent();
		for (TileRackChangeListener listener : listeners)
		{
			listener.tileButtonClicked(this);
		}
	}

	public void addTileRackChangeListener(TileRackChangeListener listener)
	{
		listeners.add(listener);
	}

	/**
	 * @see java.awt.Component#toString()
	 */
	@Override
	public String toString()
	{
		return "TileButton : (" + tile.toString() + ")";
	}
	
	
}
