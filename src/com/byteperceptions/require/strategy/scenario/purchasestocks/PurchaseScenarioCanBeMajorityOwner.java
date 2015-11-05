package com.byteperceptions.require.strategy.scenario.purchasestocks;

import com.byteperceptions.require.model.BankStockRegistry;
import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;

public class PurchaseScenarioCanBeMajorityOwner implements PurchaseScenario
{

	@Override
	public int calculateValue(Player player, HotelChain hotelChain,
			int sharesLeftToPurchaseThisTurn)
	{
		if (isPlayerInScenario(player, hotelChain, sharesLeftToPurchaseThisTurn))
		{
			//lower the value on majority once we get to nine.
			if (player.getStockRegistry().getNumberOfShares(hotelChain) > 8)
			{
				return 2;
			}
			else
			{
				return 5;
			}
		}

		return 0;
	}

	@Override
	public boolean isPlayerInScenario(Player player, HotelChain hotelChain,
			int sharesLeftToPurchaseThisTurn)
	{
		return hotelChain.getNumberOfStocksForMajority() < (player
				.getStockRegistry().getNumberOfShares(hotelChain) + Math.min(
				sharesLeftToPurchaseThisTurn, BankStockRegistry.getInstance()
						.getNumberOfShares(hotelChain)));
	}

}
