/** Copyright Ryan Ylitalo and BytePerceptions LLC. 
  * 
  * Licensed under the Apache License, Version 2.0 (the "License"); 
  * you may not use this file except in compliance with the License. 
  * You may obtain a copy of the License at 
  * 
  *      http://www.apache.org/licenses/LICENSE-2.0 
  * 
  * Unless required by applicable law or agreed to in writing, software 
  * distributed under the License is distributed on an "AS IS" BASIS, 
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
  * See the License for the specific language governing permissions and 
  * limitations under the License. 
  */ 
package com.byteperceptions.require.gamestate;

import java.util.ArrayList;
import java.util.List;

import com.byteperceptions.require.model.Player;


/**
 * @author Ryan Ylitalo
 *
 */
public class GameState
{
	private TileRegistryState tileRegistryState;
	private List<PlayerGameState> playerGameStates;
	private int turnCounter;

	public GameState()
	{
		playerGameStates = new ArrayList<PlayerGameState>();
	}

	public TileRegistryState getTileRegistryState()
	{
		return tileRegistryState;
	}

	public void setTileRegistryState(TileRegistryState tileRegistryState)
	{
		this.tileRegistryState = tileRegistryState;
	}

	public void addPlayerGameState(PlayerGameState playerGameState)
	{
		playerGameStates.add(playerGameState);
	}

	public PlayerGameState getPlayerGameState(Player player)
	{
		for (PlayerGameState playerGameState : playerGameStates)
		{
			if (playerGameState.getPlayer().equals(player))
			{
				return playerGameState;
			}
		}
		throw new RuntimeException("Unable to find player game state for : " + player);
	}

	public void setTurnCounter(int turnCounter)
	{
		this.turnCounter = turnCounter;
	}

	public int getTurnCounter()
	{
		return turnCounter;
	}

	@Override
	public String toString()
	{
		return "Turn : " + turnCounter;
	}
}
