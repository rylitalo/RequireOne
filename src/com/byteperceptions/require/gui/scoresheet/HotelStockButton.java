/**
 * 
 */
package com.byteperceptions.require.gui.scoresheet;

import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;

/**
 * @author JDev
 *
 */
public class HotelStockButton extends HotelStatisticsButton
{
	private static final long serialVersionUID = 1L;
	
	private HotelChain hotelChain;
	private Player player;
	
	public HotelStockButton(HotelChain hotelChain, Player player){
		super();
		this.player = player;
		this.hotelChain = hotelChain;
	}
	/**
	 * @see com.byteperceptions.require.gui.scoresheet.HotelStatisticsButton#getButtonValueLabel()
	 */
	@Override
	public String getButtonValueLabel()
	{
		return player.getStockRegistry().getNumberOfShares(hotelChain) + "";
	}
}
