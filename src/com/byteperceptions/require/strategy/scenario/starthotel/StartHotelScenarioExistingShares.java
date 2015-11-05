/**
 * 
 */
package com.byteperceptions.require.strategy.scenario.starthotel;

import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.strategy.scenario.playtile.HotelValue;

/**
 * @author JDev
 *
 */
public class StartHotelScenarioExistingShares implements StartHotelScenario
{

	/**
	 * @see com.byteperceptions.require.strategy.scenario.starthotel.StartHotelScenario#calculateValue(com.byteperceptions.require.model.Player, com.byteperceptions.require.model.HotelChain)
	 */
	@Override
	public int calculateValue(Player player, HotelChain hotelChain)
	{
		return HotelValue.getMaxHotelValueForPlayerByNumberOfStocks(player)
		.getValue();
	}

	/**
	 * @see com.byteperceptions.require.strategy.scenario.starthotel.StartHotelScenario#isPlayerInScenario(com.byteperceptions.require.model.Player, com.byteperceptions.require.model.HotelChain)
	 */
	@Override
	public boolean isPlayerInScenario(Player player, HotelChain hotelChain)
	{
		//Do nothing here... Logic all in calculate
		return false;
	}

}
