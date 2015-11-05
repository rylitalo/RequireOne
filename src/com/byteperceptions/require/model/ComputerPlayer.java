/**
 * 
 */
package com.byteperceptions.require.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.byteperceptions.require.strategy.PlayerStrategy;
import com.byteperceptions.require.turn.TurnRegistry;

/**
 * @author JDev
 * 
 */
public class ComputerPlayer extends Player
{
	private PlayerStrategy playerStrategy;

	public ComputerPlayer(String name, PlayerStrategy playerStrategy)
	{
		super(name);
		this.playerStrategy = playerStrategy;
	}

	/**
	 * @see com.byteperceptions.require.AcquirePlayer#takeTurn()
	 */
	@Override
	public void takeTurn()
	{

		Tile tile = playerStrategy.playTile(this);
		List<StockShare> purchasedShares = purchaseStocks();
		if(tile != null)
		{
			pickNewTile();
		}
		removeDeadChips();
		TurnRegistry.getInstance().finishTurn(tile, purchasedShares);
	}

	/**
	 * 
	 */
	@Override
	public List<StockShare> purchaseStocks()
	{
		return playerStrategy.purchaseStocks(this);
	}

	

	private void removeDeadChips()
	{
		List<Tile> deadTiles = new ArrayList<Tile>(6);
		for (Tile tile : getTiles())
		{
			if (TileRegistry.getInstance().isDeadChip(tile))
			{
				deadTiles.add(tile);
			}

		}
		for (Tile tile : deadTiles)
		{
			tile.getChipBoardButton().setBackground(Color.DARK_GRAY);
			removeTile(tile);
			pickNewTile();
		}
	}

	/**
	 * 
	 */
	private void pickNewTile()
	{
		Tile newTile = TileRegistry.getInstance().selectChipFromBag();
		if (newTile != null)
		{
			getTiles().add(newTile);
		}
	}

	private void removeTile(Tile tile)
	{
		getTiles().remove(tile);
	}

	/**
	 * @see com.byteperceptions.require.model.Player#selectHotelChainToStart(java.util.List)
	 */
	@Override
	public HotelChain selectHotelChainToStart(List<HotelChain> availableHotels)
	{
		return playerStrategy.selectHotelChainToStart(this, availableHotels);
	}

	/**
	 * @see com.byteperceptions.require.model.Player#selectSurvivingMergerChain(com.byteperceptions.require.model.Tile)
	 */
	@Override
	public HotelChain selectSurvivingMergerChain(Tile tile)
	{
		return playerStrategy.selectSurvivingMergerChain(this, tile);
	}

	/**
	 * @see com.byteperceptions.require.model.Player#transactMergerStocks(com.byteperceptions.require.model.HotelChain, com.byteperceptions.require.model.HotelChain)
	 */
	@Override
	public void transactMergerStocks(HotelChain mergerSurvivor,
		HotelChain mergerDeceased)
	{
		playerStrategy.transactStocksAfterMerger(this, mergerSurvivor, mergerDeceased);
	}

	/**
	 * @see com.byteperceptions.require.model.Player#shouldEndGame()
	 */
	@Override
	public boolean shouldEndGame()
	{
		return playerStrategy.shouldEndGame(this);
	}

	@Override
	public HotelChain selectHotelToMergeFirst(HotelChain firstChain,
			HotelChain secondChain)
	{
		return playerStrategy.selectHotelToMergeFirst(firstChain, secondChain);
	}
}
