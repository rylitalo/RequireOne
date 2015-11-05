/**
 * 
 */
package com.byteperceptions.require.gui.scoresheet;

import javax.swing.JButton;

/**
 * @author JDev
 *
 */
public abstract class HotelStatisticsButton extends JButton
{
	private static final long serialVersionUID = 1L;

	public HotelStatisticsButton(){
		super("0");
	}
	
	public abstract String getButtonValueLabel();
	
}
