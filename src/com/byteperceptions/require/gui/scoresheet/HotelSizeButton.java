/**
 * 
 */
package com.byteperceptions.require.gui.scoresheet;

import com.byteperceptions.require.model.HotelChain;

/**
 * @author JDev
 *
 */
public class HotelSizeButton extends HotelStatisticsButton
{
	private static final long serialVersionUID = 1L;
	
	private HotelChain hotelChain;
	
	public HotelSizeButton(HotelChain hotelChain){
		super();
		this.hotelChain = hotelChain;
	}
	/**
	 * @see com.byteperceptions.require.gui.scoresheet.HotelStatisticsButton#getButtonValueLabel()
	 */
	@Override
	public String getButtonValueLabel()
	{
		return hotelChain.getSize() + "";
	}

}
