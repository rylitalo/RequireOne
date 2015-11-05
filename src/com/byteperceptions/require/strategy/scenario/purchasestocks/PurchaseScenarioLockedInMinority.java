package com.byteperceptions.require.strategy.scenario.purchasestocks;

import com.byteperceptions.require.model.BankStockRegistry;
import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.registry.PlayerRegistry;

public class PurchaseScenarioLockedInMinority implements PurchaseScenario
{
	@Override
	public int calculateValue(Player player, HotelChain hotelChain,
			int sharesLeftToPurchaseThisTurn)
	{
		if (isPlayerInScenario(player, hotelChain, sharesLeftToPurchaseThisTurn))
		{
			// Already locked in Majority, don't buy any more
			return -5;
		}

		return 0;
	}

	@Override
	public boolean isPlayerInScenario(Player player, HotelChain hotelChain,
			int sharesLeftToPurchaseThisTurn)
	{
		if (!hotelChain.getMajorityStockHolders().contains(player)
				&& hotelChain.getMajorityStockHolders().size() == 1
				&& hotelChain.getMinorityStockHolders().contains(player)
				&& hotelChain.getMajorityStockHolders().size() == 1)
		{
			Player majorityHolder = hotelChain.getMajorityStockHolders().get(0);
			for (Player gamePlayer : PlayerRegistry.getInstance()
					.getAllPlayers())
			{
				if (gamePlayer != majorityHolder
						&& gamePlayer != player
						&& (gamePlayer.getStockRegistry().getNumberOfShares(
								hotelChain) + BankStockRegistry.getInstance()
								.getNumberOfShares(hotelChain)) >= player
								.getStockRegistry().getNumberOfShares(
										hotelChain))
				{
					return false;
				}
			}

			// No player can can me for majority - Now return true if I can't
			// catch the Majority holder.
			if ((player.getStockRegistry().getNumberOfShares(hotelChain) + BankStockRegistry
					.getInstance().getNumberOfShares(hotelChain)) < majorityHolder
					.getStockRegistry().getNumberOfShares(hotelChain))
			{

				return true;
			}
		}

		return false;
	}
}
