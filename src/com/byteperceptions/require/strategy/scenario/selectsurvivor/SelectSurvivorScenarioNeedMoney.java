package com.byteperceptions.require.strategy.scenario.selectsurvivor;

import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;

public class SelectSurvivorScenarioNeedMoney implements SelectSurvivorScenario
{
	public int calculateValue(Player player, HotelChain hotelChain)
	{
		if (isPlayerInScenario(player, hotelChain))
		{
			if (hotelChain.getMajorityStockHolders().contains(player)
					&& hotelChain.getMajorityStockHolders().size() == 1)
			{
				//If I need money, I don't want this company to survive the merger.
				return -5 - hotelChain.determinePayoffForPlayer(player)/100;
			}
			else 
			{
				return -(hotelChain.determinePayoffForPlayer(player)/100);
			}
		}
		return 0;
	}

	public boolean isPlayerInScenario(Player player, HotelChain hotelChain)
	{
		if (player.getCash() < 10000)
		{
			return true;
		}
		return false;
	}
}
