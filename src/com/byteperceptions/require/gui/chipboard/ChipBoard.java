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
package com.byteperceptions.require.gui.chipboard;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import com.byteperceptions.require.BoardChangedListener;
import com.byteperceptions.require.RequireChangeListener;
import com.byteperceptions.require.model.Tile;
import com.byteperceptions.require.model.TileRegistry;
import com.byteperceptions.require.model.TileRegistryIterator;


/**
 * @author Ryan Ylitalo
 *
 */
public class ChipBoard extends JPanel implements BoardChangedListener
{
	private static final long serialVersionUID = 1L;

	private static final Dimension PREFERRED_SIZE = new Dimension(500,400);
	//private static final Dimension PREFERRED_SIZE = new Dimension(800,650);
	char[] letters =
	{ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I' };

	public ChipBoard()
	{
		this.setLayout(new GridLayout(9, 12));
		this.setPreferredSize(PREFERRED_SIZE);
		this.setMinimumSize(PREFERRED_SIZE);
		this.setSize(PREFERRED_SIZE);
		this.setMaximumSize(PREFERRED_SIZE);
		
		this.setBorder(new TitledBorder(new SoftBevelBorder(
			BevelBorder.RAISED), "Game Board"));

		for (Tile tile : new TileRegistryIterator())
		{
			this.add(tile.getChipBoardButton());
		}
		RequireChangeListener.getInstance().addBoardChangedListener(this);
		
	}

	/**
	 * @see com.byteperceptions.require.BoardChangedListener#fireBoardChangedEvent()
	 */
	@Override
	public void fireBoardChangedEvent()
	{
		TileRegistry.getInstance().removeDeadChips();
	}
}
