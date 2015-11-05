/**
 * 
 */
package com.byteperceptions.require.gui.scoresheet;

import com.byteperceptions.require.model.Player;

/**
 * @author JDev
 *
 */
public class PlayerCashButton extends HotelStatisticsButton
{
	private static final long serialVersionUID = 1L;
	
	private Player player;
	
	public PlayerCashButton(Player player){
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
		return player.getCash() + "";
	}

}
