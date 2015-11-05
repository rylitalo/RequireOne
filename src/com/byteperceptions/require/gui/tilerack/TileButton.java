/**
 * 
 */
package com.byteperceptions.require.gui.tilerack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import com.byteperceptions.require.RequireChangeListener;
import com.byteperceptions.require.model.Tile;

public class TileButton extends JButton implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private Tile tile;
	private List<TileRackChangeListener> listeners = new ArrayList<TileRackChangeListener>();

	/**
	 * @return the tile
	 */
	public Tile getTile()
	{
		return tile;
	}

	public TileButton(Tile tile)
	{
		super(tile.getButtonLabel());
		this.tile = tile;
		this.addActionListener(this);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		tile.playTile();
		RequireChangeListener.getInstance().fireBoardChangedEvent();
		for (TileRackChangeListener listener : listeners)
		{
			listener.tileButtonClicked(this);
		}
	}

	public void addTileRackChangeListener(TileRackChangeListener listener)
	{
		listeners.add(listener);
	}

	/**
	 * @see java.awt.Component#toString()
	 */
	@Override
	public String toString()
	{
		return "TileButton : (" + tile.toString() + ")";
	}
	
	
}
