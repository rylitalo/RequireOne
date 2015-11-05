package com.byteperceptions.require.gui.gamelog;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.byteperceptions.require.model.StockShare;
import com.byteperceptions.require.turn.Turn;

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