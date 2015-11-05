/**
 * 
 */
package com.byteperceptions.require.turn;

import java.util.List;

import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.model.StockShare;
import com.byteperceptions.require.model.Tile;

/**
 * @author JDev
 *
 */
public interface TurnChangedListener
{
	public void turnChanged(Player nextPlayerTurn, Tile previousPlayedTile, Player previousPlayerTurn, List<StockShare> purchasedShares);
}
