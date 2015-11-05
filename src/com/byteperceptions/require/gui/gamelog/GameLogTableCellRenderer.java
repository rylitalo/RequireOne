package com.byteperceptions.require.gui.gamelog;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.byteperceptions.require.model.HotelChain;

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
				if((HotelChain)value == HotelChain.AMERICAN){
					label.setForeground(Color.WHITE);
				}
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