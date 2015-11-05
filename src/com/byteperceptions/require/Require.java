/**
 * 
 */
package com.byteperceptions.require;

import java.util.ArrayList;
import java.util.Random;

import com.byteperceptions.require.model.ComputerPlayer;
import com.byteperceptions.require.model.ManualPlayer;
import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.registry.PlayerRegistry;
import com.byteperceptions.require.strategy.GeneticMutationPlayerStrategy;
import com.byteperceptions.require.strategy.RandomPlayerStrategy;
import com.byteperceptions.require.strategy.RyanPlayerStrategy;

public class Require
{

	private Player manualPlayer;
	private static final RandomPlayerStrategy RANDOM_PLAYER_STRATEGY = new RandomPlayerStrategy();
	private static final RyanPlayerStrategy RYAN_PLAYER_STRATEGY = new RyanPlayerStrategy();
	
	private static final GeneticMutationPlayerStrategy GENETIC_MUTATION_PLAYER_STRATEGY = new GeneticMutationPlayerStrategy();
	
	private ArrayList<String> RANDOM_PLAYERS = new ArrayList<String>();

	public Require(int numberOfPlayers, String playerName)
	{
		
		RANDOM_PLAYERS.add("Andy");
		RANDOM_PLAYERS.add("Sid");
		RANDOM_PLAYERS.add("Pete");
		RANDOM_PLAYERS.add("Mark");
//		RANDOM_PLAYERS.add("Ben");
//		RANDOM_PLAYERS.add("Allan");
//		RANDOM_PLAYERS.add("Evan");
//		RANDOM_PLAYERS.add("Mike");
		
		manualPlayer = new ManualPlayer(playerName);
		PlayerRegistry.getInstance().clear();
		PlayerRegistry.getInstance().addPlayer(manualPlayer);
		
		//PlayerRegistry.getInstance().addPlayer(new ComputerPlayer("Mutant", GENETIC_MUTATION_PLAYER_STRATEGY));
		
		for(int i = 0; i < (numberOfPlayers - 1); i++){
			PlayerRegistry.getInstance().addPlayer(selectRandomComputerPlayer());
		}
	}

	private ComputerPlayer selectRandomComputerPlayer()
	{
		Random random = new Random();
		String playerName = RANDOM_PLAYERS.remove(random.nextInt(RANDOM_PLAYERS.size()));
		return new ComputerPlayer(playerName, GENETIC_MUTATION_PLAYER_STRATEGY);
	}

	/**
	 * @return
	 */
	public Player getManualPlayer()
	{
		return manualPlayer;
	}
}
