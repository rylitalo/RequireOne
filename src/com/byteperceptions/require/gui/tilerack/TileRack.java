/**
 * 
 */
package com.byteperceptions.require.gui.tilerack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import com.byteperceptions.require.BoardChangedListener;
import com.byteperceptions.require.gamestate.GameStateBroker;
import com.byteperceptions.require.gamestate.GameStateChangeListener;
import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.model.StockShare;
import com.byteperceptions.require.model.Tile;
import com.byteperceptions.require.model.TileRegistry;
import com.byteperceptions.require.turn.TurnChangedListener;
import com.byteperceptions.require.turn.TurnRegistry;

/**
 * @author JDev
 * 
 */
public class TileRack extends JPanel implements TileRackChangeListener,
		BoardChangedListener, TurnChangedListener, GameStateChangeListener
{
	private static final long serialVersionUID = 1L;

	private static final Color STARTER_CHIP_COLOR = new Color(200, 222, 138);
	private static final Color STARTER_COMBO_CHIP_COLOR = new Color(170, 190,
			118);
	private static final Color MERGER_CHIP_COLOR = new Color(194, 126, 193);
	private static final Color ORIGINAL_BUTTON_COLOR = new JButton()
			.getBackground();

	private static final Dimension PREFERRED_SIZE = new Dimension(400, 75);

	private Player player;
	private List<TileButton> tileButtons;

	public TileRack(Player person)
	{
		tileButtons = new ArrayList<TileButton>();
		this.player = person;
		this.setBorder(new TitledBorder(
				new SoftBevelBorder(BevelBorder.RAISED), "Tile Rack"));

		this.setPreferredSize(PREFERRED_SIZE);
		this.setMinimumSize(PREFERRED_SIZE);
		this.setSize(PREFERRED_SIZE);
		this.setMaximumSize(PREFERRED_SIZE);
		this.setLayout(new FlowLayout());
		this.intializeTileButtons();
		TurnRegistry.getInstance().addTurnChangedListener(this);
		GameStateBroker.getInstance().addGameStateChangeListener(this);
	}

	/**
	 * 
	 */
	private void intializeTileButtons()
	{
		this.removeAll();
		tileButtons.clear();
		for (Tile tile : player.getTiles())
		{
			TileButton tileButton = new TileButton(tile);
			tileButton.addTileRackChangeListener(this);
			this.add(tileButton);
			tileButtons.add(tileButton);
		}
		disableUnplayableTiles();
		this.setVisible(false);
		this.setVisible(true);
	}

	/**
	 * @see com.byteperceptions.require.gui.tilerack.TileRackChangeListener#tileButtonClicked(com.byteperceptions.require.gui.tilerack.TileButton)
	 */
	@Override
	public void tileButtonClicked(TileButton currentlyPlayedChip)
	{
		removeTileButton(currentlyPlayedChip);
		List<StockShare> purchasedShares = TurnRegistry.getInstance()
				.currentPlayerTurn().purchaseStocks();
		pickNewTile();
		removeDeadChips();
		disableUnplayableTiles();

		this.setVisible(false);
		this.setVisible(true);
		TurnRegistry.getInstance().finishTurn(currentlyPlayedChip.getTile(),
				purchasedShares);
	}

	private void removeTileButton(TileButton tileButton)
	{
		Tile tilePlacedOnBoard = tileButton.getTile();
		player.getTiles().remove(tilePlacedOnBoard);
		this.remove(tileButton);
		tileButtons.remove(tileButton);
	}

	/**
	 * 
	 */
	private void removeDeadChips()
	{
		List<TileButton> deadTiles = new ArrayList<TileButton>(6);
		for (TileButton tileButton : tileButtons)
		{
			Tile tile = tileButton.getTile();
			if (TileRegistry.getInstance().isDeadChip(tile))
			{
				deadTiles.add(tileButton);
			}

		}
		for (TileButton tileButton : deadTiles)
		{
			tileButton.getTile().getChipBoardButton().setBackground(
					Color.DARK_GRAY);
			removeTileButton(tileButton);
			pickNewTile();
		}
	}

	private void pickNewTile()
	{
		Tile newTile = TileRegistry.getInstance().selectChipFromBag();
		if (newTile != null)
		{
			player.getTiles().add(newTile);
			TileButton newTileButton = new TileButton(newTile);
			newTileButton.addTileRackChangeListener(this);
			this.add(newTileButton);
			tileButtons.add(newTileButton);
		}
	}

	/**
	 * 
	 */
	private void disableUnplayableTiles()
	{
		for (TileButton button : tileButtons)
		{
			Tile tile = button.getTile();
			tile.getChipBoardButton().setBackground(Color.white);

			if (tile.isStarterChip())
			{
				button.setBackground(STARTER_CHIP_COLOR);
			}
			else if (TileRegistry.getInstance().isMergerChip(tile))
			{
				button.setBackground(MERGER_CHIP_COLOR);
			}
			else if (TileRegistry.getInstance().getAdjoiningHotelChains(tile)
					.size() == 1)
			{
				HotelChain adjoiningChain = TileRegistry.getInstance()
						.getAdjoiningHotelChains(tile).get(0);
				button.setBackground(adjoiningChain.getColor());
				if (adjoiningChain == HotelChain.AMERICAN)
				{
					button.setForeground(Color.WHITE);
				}
				else
				{
					button.setForeground(Color.BLACK);
				}
			}
			else if (isStarterWithChipOnTileRack(tile))
			{
				button.setBackground(STARTER_COMBO_CHIP_COLOR);
			}
			else
			{
				button.setBackground(ORIGINAL_BUTTON_COLOR);
			}

			if (tile.isPlayable())
			{
				button.setEnabled(true);
			}
			else
			{
				button.setEnabled(false);
			}
		}
	}

	/**
	 * @param oneTile
	 * @return
	 */
	private boolean isStarterWithChipOnTileRack(Tile oneTile)
	{
		for (TileButton button : tileButtons)
		{
			Tile otherTile = button.getTile();
			if (!oneTile.equals(otherTile))
			{
				List<Tile> adjoiningTiles = TileRegistry.getInstance()
						.getAdjoiningTiles(oneTile);
				for (Tile adjoiningTile : adjoiningTiles)
				{
					if (adjoiningTile.equals(otherTile))
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * @see com.byteperceptions.require.BoardChangedListener#fireBoardChangedEvent()
	 */
	@Override
	public void fireBoardChangedEvent()
	{
		disableUnplayableTiles();
	}

	/**
	 * @return
	 */
	public Player getPlayer()
	{
		return player;
	}

	/**
	 * @see com.byteperceptions.require.turn.TurnChangedListener#turnChanged(com.byteperceptions.require.model.Player)
	 */
	@Override
	public void turnChanged(Player nextPlayerTurn, Tile previouslyPlayedTile,
			Player previousPlayerTurn, List<StockShare> purchasedShares)
	{
		enableRackForPlayer(nextPlayerTurn);
	}

	private void enableRackForPlayer(Player currentPlayer)
	{
		if (currentPlayer == player)
		{
			// Current users turn... Enable the Tiles
			disableUnplayableTiles();
		}
		else
		{
			for (TileButton button : tileButtons)
			{
				button.getTile().getChipBoardButton().setBackground(
						ORIGINAL_BUTTON_COLOR);
				button.setEnabled(false);
			}
		}
	}

	@Override
	public void gameStateChanged()
	{
		intializeTileButtons();
		enableRackForPlayer(TurnRegistry.getInstance().currentPlayerTurn());
	}
}
