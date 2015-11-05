package com.byteperceptions.require.turn;

import com.byteperceptions.require.model.Player;

public class PlayerTurnThread implements Runnable
{

	private Player player;

	public PlayerTurnThread(Player player)
	{
		this.player = player;
	}

	@Override
	public void run()
	{
		try
		{
			Thread.sleep(500);
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
		player.takeTurn();
	}

}
