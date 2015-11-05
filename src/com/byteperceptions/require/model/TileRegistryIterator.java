/**
 * 
 */
package com.byteperceptions.require.model;

import java.util.Iterator;

/**
 * @author JDev
 * 
 */
public class TileRegistryIterator implements Iterator<Tile>, Iterable<Tile>
{

	private Iterator<Tile> iterator;

	public TileRegistryIterator()
	{
		iterator = TileRegistry.getInstance().getTileBoardIterator();
	}

	/**
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext()
	{
		return iterator.hasNext();
	}

	/**
	 * @see java.util.Iterator#next()
	 */
	@Override
	public Tile next()
	{
		return iterator.next();
	}

	/**
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove()
	{
		// Don't remove any tiles from the list
	}

	/**
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Tile> iterator()
	{
		return this;
	}

}
