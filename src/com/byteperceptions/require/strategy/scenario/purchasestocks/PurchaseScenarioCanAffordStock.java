package com.byteperceptions.require.strategy.scenario.purchasestocks;

import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;

public class PurchaseScenarioCanAffordStock implements PurchaseScenario
{

	@Override
	public int calculateValue(Player player, HotelChain hotelChain, int sharesLeftToPurchaseThisTurn)
	{
		if (isPlayerInScenario(player, hotelChain, sharesLeftToPurchaseThisTurn))
		{
			return 0;
		}

		return -100;
	}

	@Override
	public boolean isPlayerInScenario(Player player, HotelChain hotelChain, int sharesLeftToPurchaseThisTurn)
	{
		return hotelChain.getPrice() <= player.getCash();
	}

}
