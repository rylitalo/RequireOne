package com.byteperceptions.require.turn;

import java.util.List;

import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.model.StockShare;
import com.byteperceptions.require.model.Tile;

public class Turn
{

	private Player player;
	private List<StockShare> purchasedShares;
	private Tile tile;

	public Turn(Player player, List<StockShare> purchasedShares, Tile tile)
	{
		this.player = player;
		this.purchasedShares = purchasedShares;
		this.tile = tile;
	}

	public Player getPlayer()
	{
		return player;
	}

	public List<StockShare> getPurchasedShares()
	{
		return purchasedShares;
	}

	public Tile getTile()
	{
		return tile;
	}

	@Override
	public String toString()
	{
		return tile + " : " + player;
	}

}
