/**
 * 
 */
package com.byteperceptions.require.model;

/**
 * @author JDev
 *
 */
public enum TileDirection
{
	
	UP(-1), DOWN(1), RIGHT(31), LEFT(-31);
	
	private int hashCodeOffset;
	private  TileDirection(int hashCodeOffset){
		this.hashCodeOffset = hashCodeOffset;
	}

	public Integer getAdjoiningHashCode(Tile tile){
		return tile.hashCode() + hashCodeOffset;
	}
}


