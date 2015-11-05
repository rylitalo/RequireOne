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
public class PlayerNPVButton extends HotelStatisticsButton
{
	private static final long serialVersionUID = 1L;
	
	private Player player;
	
	public PlayerNPVButton(Player player){
		super();
		this.player = player;
		this.setText(getButtonValueLabel());
	}
	/**
	 * @see com.byteperceptions.require.gui.scoresheet.HotelStatisticsButton#getButtonValueLabel()
	 */
	@Override
	public String getButtonValueLabel()
	{
		int totalValueForPlayer = player.getCash();
		for(HotelChain hotelChain : HotelChain.values())
		{
			totalValueForPlayer += hotelChain.determinePayoffForPlayer(player);
			totalValueForPlayer += player.getStockRegistry().getNumberOfShares(hotelChain) * hotelChain.getPrice();
		}
	
		return totalValueForPlayer + "";
	}

}
