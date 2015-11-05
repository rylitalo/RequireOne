package com.byteperceptions.require.strategy.scenario.purchasestocks;

import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;

/**
 * We are using a genetic mutation algorithm to determine what the best stocks to purchase are.
 *
 */
public interface PurchaseScenario
{
	//This 
	public int calculateValue(Player player, HotelChain hotelChain, int sharesLeftToPurchaseThisTurn);
	public boolean isPlayerInScenario(Player player, HotelChain hotelChain, int sharesLeftToPurchaseThisTurn);
}
