package com.byteperceptions.require.gamestate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.model.Tile;

public class PlayerGameState
{

	private HashMap<HotelChain, Integer> numberOfSharesByHotelChain;
	private int cash;
	private List<Tile> tiles;
	private Player player;

	public PlayerGameState(Player player)
	{
		this.player = player;
		numberOfSharesByHotelChain = new HashMap<HotelChain, Integer>();
		tiles = new ArrayList<Tile>();
		cash = player.getCash();

		for (HotelChain chain : HotelChain.values())
		{
			numberOfSharesByHotelChain.put(chain, player.getStockRegistry()
					.getNumberOfShares(chain));
		}

		for (Tile tile : player.getTiles())
		{
			tiles.add(tile);
		}
	}

	public int getNumberOfShares(HotelChain chain)
	{
		return numberOfSharesByHotelChain.get(chain);
	}

	public Player getPlayer()
	{
		return player;
	}

	public List<Tile> getTiles()
	{
		return tiles;
	}

	public int getCash()
	{
		return cash;
	}

}
