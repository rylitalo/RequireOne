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

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.byteperceptions.require.model.StockShare;
import com.byteperceptions.require.turn.Turn;


/**
 * @author Ryan Ylitalo
 *
 */
public class GameLogTableModel extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;

	private List<Turn> turns = new ArrayList<Turn>();

	private String[] columnNames =
	{ "Player", "Tile", "Purchase", "Purchase", "Purchase" };

	public int getColumnCount()
	{
		return columnNames.length;
	}

	public int getRowCount()
	{
		return turns.size();
	}

	public String getColumnName(int col)
	{
		return columnNames[col];
	}

	public Object getValueAt(int row, int col)
	{
		Turn turn = turns.get(row);
		if (col == 0)
		{
			return turn.getPlayer();
		}
		else if (col == 1)
		{
			return turn.getTile();
		}
		else
		{
			List<StockShare> purchasedShares = turn.getPurchasedShares();
			if (purchasedShares != null && purchasedShares.size() > col - 2)
			{
				StockShare share = purchasedShares.get(col - 2);
				if (share.getHotelChain() != null)
				{
					return share.getHotelChain();
				}
			}
		}

		return "";
	}

	public void addRow(Turn turn)
	{
		turns.add(0, turn);
		fireTableDataChanged();
	}

	public void restoreGameState(int selectedRow)
	{
		for(int i = 0; i < selectedRow; i++)
		{
			turns.remove(0);
		}
		fireTableDataChanged();
	}
}