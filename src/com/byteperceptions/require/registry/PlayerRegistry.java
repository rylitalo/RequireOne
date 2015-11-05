/**
 * 
 */
package com.byteperceptions.require.registry;

import java.util.ArrayList;
import java.util.List;

import com.byteperceptions.require.model.Player;

/**
 * @author JDev
 *
 */
public class PlayerRegistry
{
	private static final PlayerRegistry INSTANCE = new PlayerRegistry();
	
	private List<Player> allPlayers;
	
	private PlayerRegistry(){
		allPlayers = new ArrayList<Player>(6);
	}
	
	public static PlayerRegistry getInstance(){
		return INSTANCE;
	}

	/**
	 * @return
	 */
	public List<Player> getAllPlayers()
	{
		return allPlayers;
	}
	
	public void addPlayer(Player person){
		allPlayers.add(person);
	}

	public void clear()
	{
		allPlayers.clear();
		
	}
}
