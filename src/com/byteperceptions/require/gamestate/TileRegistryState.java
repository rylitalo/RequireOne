package com.byteperceptions.require.gamestate;

import java.util.List;

import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Tile;

public class TileRegistryState
{
	private List<Tile> chipBagState;
	private List<TileState> tileStates;
	private List<HotelChain> activeChains;
	private List<HotelChain> availableChains;

	public void setChipBagTiles(List<Tile> chipBagTileStates)
	{
		chipBagState = chipBagTileStates;
	}

	public List<Tile> getChipBagTiles()
	{
		return chipBagState;
	}

	public void setTileStates(List<TileState> tileStates)
	{
		this.tileStates = tileStates;
	}

	public List<TileState> getTileStates()
	{
		return tileStates;
	}

	public List<HotelChain> getActiveChains()
	{
		return activeChains;
	}

	public void setActiveChains(List<HotelChain> activeChains)
	{
		this.activeChains = activeChains;
	}

	public List<HotelChain> getAvailableChains()
	{
		return availableChains;
	}

	public void setAvailableChains(List<HotelChain> availableChains)
	{
		this.availableChains = availableChains;
	}

}
