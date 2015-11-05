/**
 * 
 */
package com.byteperceptions.require;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JDev
 * 
 */
public class RequireChangeListener
{
	private static final RequireChangeListener INSTANCE = new RequireChangeListener();
	private List<BoardChangedListener> boardChangedListeners;

	private RequireChangeListener()
	{
		boardChangedListeners = new ArrayList<BoardChangedListener>();
	}
	
	public static RequireChangeListener getInstance(){
		return INSTANCE;
	}

	public void fireBoardChangedEvent()
	{
		for (BoardChangedListener boardChangedListener : boardChangedListeners)
		{
			boardChangedListener.fireBoardChangedEvent();
		}
	}

	public void addBoardChangedListener(
		BoardChangedListener boardChangedListener)
	{
		boardChangedListeners.add(boardChangedListener);
	}
}
