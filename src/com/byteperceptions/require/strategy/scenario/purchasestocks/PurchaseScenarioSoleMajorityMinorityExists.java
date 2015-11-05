package com.byteperceptions.require.strategy.scenario.purchasestocks;

import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;

public class PurchaseScenarioSoleMajorityMinorityExists implements
		PurchaseScenario
{

	@Override
	public int calculateValue(Player player, HotelChain hotelChain,
			int sharesLeftToPurchaseThisTurn)
	{
		if (isPlayerInScenario(player, hotelChain, sharesLeftToPurchaseThisTurn))
		{
			return 1;
		}

		return 0;
	}

	@Override
	public boolean isPlayerInScenario(Player player, HotelChain hotelChain,
			int sharesLeftToPurchaseThisTurn)
	{
		return !hotelChain.getMajorityStockHolders().contains(player)
				&& hotelChain.getMajorityStockHolders().size() == 1
				&& hotelChain.getMinorityStockHolders().size() == 0;
	}

}
