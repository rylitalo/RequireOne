/**
 * 
 */
package com.byteperceptions.require.gui.stockpurchase;

import java.awt.Color;

import javax.swing.JButton;

import com.byteperceptions.require.model.HotelChain;

public class StocksPurchaseButton extends JButton
{
	private static final long serialVersionUID = 1L;
	
	private Color originalColor;
	private HotelPurchaseButton hotelPurchaseButton;

	public StocksPurchaseButton()
	{
		super("Stock Purchase");
		originalColor = this.getBackground();
	}

	public void setHotelPurchaseButton(HotelPurchaseButton hotelPurchaseButton)
	{
		this.hotelPurchaseButton = hotelPurchaseButton;
		if (hotelPurchaseButton == null)
		{
			setBackground(originalColor);
			setText("Stock Purchase");
			setForeground(Color.BLACK);
		}
		else
		{
			setBackground(hotelPurchaseButton.getHotelChain().getColor());
			if(hotelPurchaseButton.getHotelChain() == HotelChain.AMERICAN)
			{
				setForeground(Color.WHITE);
			}
			setText(hotelPurchaseButton.getHotelChain().getLabel() + " $" + hotelPurchaseButton.getHotelChain().getPrice());
			hotelPurchaseButton.decrementSharesRemaining();
		}
	}

	public HotelPurchaseButton getHotelPurchaseButton()
	{
		return hotelPurchaseButton;
	}

}
