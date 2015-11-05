/**
 * 
 */
package com.byteperceptions.require.gui.stockpurchase;

import java.awt.Color;

import javax.swing.JButton;

import com.byteperceptions.require.model.BankStockRegistry;
import com.byteperceptions.require.model.HotelChain;

/**
 * @author JDev
 * 
 */
public class HotelPurchaseButton extends JButton
{
	private static final long serialVersionUID = 1L;
	
	private HotelChain hotelChain;
	private int initialNumberOfSharesRemaining;

	public HotelPurchaseButton(HotelChain hotelChain)
	{
		super();
		this.hotelChain = hotelChain;
		this.setBackground(hotelChain.getColor());
		if(hotelChain == HotelChain.AMERICAN)
		{
			this.setForeground(Color.WHITE);
		}
		this.setText(hotelChain.getLabel());
		initialNumberOfSharesRemaining = BankStockRegistry.getInstance()
				.getNumberOfShares(hotelChain);
	}

	public void incrementSharesRemaining()
	{
		initialNumberOfSharesRemaining++;
	}

	public void decrementSharesRemaining()
	{
		initialNumberOfSharesRemaining--;
	}
	
	public int getSharesRemaining(){
		return initialNumberOfSharesRemaining;
	}

	public HotelChain getHotelChain()
	{
		return hotelChain;
	}
}
