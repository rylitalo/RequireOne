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
package com.byteperceptions.require.gui.gamelog;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.byteperceptions.require.model.HotelChain;


/**
 * @author Ryan Ylitalo
 *
 */
public class GameLogTableCellRenderer implements
		TableCellRenderer
{

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, final int row, int column)
	{
		JLabel label = new JLabel();
		label.setBorder(null);

		if(column >= 2)
		{
			if(value instanceof HotelChain)
			{
				label.setBackground(((HotelChain)value).getColor());
			}
		}
		
		if(table.getSelectedRow() == row)
		{
			label.setBackground(Color.WHITE);
		}

		label.setOpaque(true);
		label.setText(value + "");

		return label;
	}

}