package com.byteperceptions.require.strategy.scenario.purchasestocks;

import com.byteperceptions.require.model.BankStockRegistry;
import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;

public class PurchaseScenarioLockedInMajority implements PurchaseScenario
{

	@Override
	public int calculateValue(Player player, HotelChain hotelChain,
			int sharesLeftToPurchaseThisTurn)
	{
		if (isPlayerInScenario(player, hotelChain, sharesLeftToPurchaseThisTurn))
		{
			//Already locked in Majority, don't buy any more
			return -5;
		}

		return 0;
	}

	@Override
	public boolean isPlayerInScenario(Player player, HotelChain hotelChain,
			int sharesLeftToPurchaseThisTurn)
	{
		if (hotelChain.getMajorityStockHolders().contains(player)
				&& hotelChain.getMajorityStockHolders().size() == 1
				&& player.getStockRegistry().getNumberOfShares(hotelChain) > (hotelChain
						.getNumberOfStocksForMinority() + BankStockRegistry
						.getInstance().getNumberOfShares(hotelChain)))
		{
			return true;
		}

		return false;
	}
}
