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
package com.byteperceptions.require.gui.stockpurchase;

import java.awt.Color;

import javax.swing.JButton;

import com.byteperceptions.require.model.HotelChain;


/**
 * @author Ryan Ylitalo
 *
 */
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
			setOpaque(true);
			setText("Stock Purchase");
			setForeground(Color.BLACK);
		}
		else
		{
			setBackground(hotelPurchaseButton.getHotelChain().getColor());
			setOpaque(true);
			setText(hotelPurchaseButton.getHotelChain().getLabel() + " $" + hotelPurchaseButton.getHotelChain().getPrice());
			hotelPurchaseButton.decrementSharesRemaining();
		}
	}

	public HotelPurchaseButton getHotelPurchaseButton()
	{
		return hotelPurchaseButton;
	}

}
