/**
 * 
 */
package com.byteperceptions.require.strategy.scenario.starthotel;

import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;

/**
 * @author JDev
 *
 */
public interface StartHotelScenario
{
	//This 
	public int calculateValue(Player player, HotelChain hotelChain);
	public boolean isPlayerInScenario(Player player, HotelChain hotelChain);
}
