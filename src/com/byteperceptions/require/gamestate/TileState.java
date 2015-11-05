package com.byteperceptions.require.gamestate;

import java.awt.Color;

import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Tile;

public class TileState
{

	private boolean alreadyPlayed;
	private HotelChain currentHotelChain;
	private Tile tile;
	private Color backgroundColor;
	private Color foregroundColor;

	public TileState(Tile tile)
	{
		this.alreadyPlayed = tile.isAlreadyPlayed();
		this.currentHotelChain = tile.getHotelChain();
		this.foregroundColor = tile.getChipBoardButton().getForeground();
		this.backgroundColor = tile.getChipBoardButton().getBackground();
		this.tile = tile;
	}

	public boolean isAlreadyPlayed()
	{
		return alreadyPlayed;
	}

	public HotelChain getCurrentHotelChain()
	{
		return currentHotelChain;
	}

	public Tile getTile()
	{
		return tile;
	}

	public Color getBackgroundColor()
	{
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor)
	{
		this.backgroundColor = backgroundColor;
	}

	public Color getForegroundColor()
	{
		return foregroundColor;
	}

	public void setForegroundColor(Color foregroundColor)
	{
		this.foregroundColor = foregroundColor;
	}
}
