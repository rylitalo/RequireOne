package com.byteperceptions.require.strategy.scenario.selectsurvivor;

import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;

public interface SelectSurvivorScenario
{
	public int calculateValue(Player player, HotelChain hotelChain);
	public boolean isPlayerInScenario(Player player, HotelChain hotelChain);
}
