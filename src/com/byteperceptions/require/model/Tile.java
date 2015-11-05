/**
 * 
 */
package com.byteperceptions.require.model;

import java.awt.Color;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import com.byteperceptions.require.gamestate.TileState;
import com.byteperceptions.require.turn.TurnRegistry;

/**
 * @author JDev
 * 
 */
public class Tile
{

	private JButton chipBoardButton;
	private boolean alreadyPlayed;
	private HotelChain currentHotelChain;

	/**
	 * @return the hotelChain
	 */
	public HotelChain getHotelChain()
	{
		return currentHotelChain;
	}

	char[] letters =
	{ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I' };

	private int numberPosition;
	private int characterPosition;

	public Tile(int numberPosition, int characterPosition)
	{
		this.numberPosition = numberPosition;
		this.characterPosition = characterPosition;
		this.alreadyPlayed = false;
		this.chipBoardButton = new JButton(getButtonLabel());
		this.chipBoardButton.setMargin(new Insets(0, 0, 0, 0));
	}

	/**
	 * @return
	 */
	public String getButtonLabel()
	{
		return numberPosition + (letters[characterPosition] + " ");
	}

	public void playTile()
	{
		chipBoardButton.setBackground(Color.LIGHT_GRAY);

		boolean isMergerChip = TileRegistry.getInstance().isMergerChip(this);
		if (isMergerChip)
		{
			Player currentPlayer = TurnRegistry.getInstance()
					.currentPlayerTurn();
			HotelChain survivingChain = currentPlayer
					.selectSurvivingMergerChain(this);

			List<HotelChain> adjoiningChains = TileRegistry.getInstance()
					.getAdjoiningHotelChains(this);

			// Bigger companies need to merge first
			List<HotelChain> mergeOrder = new ArrayList<HotelChain>();

			for (HotelChain chain : adjoiningChains)
			{
				if (chain != survivingChain)
				{
					mergeOrder.add(0, chain);
					int index = 0;
					while (mergeOrder.size() > index + 1)
					{
						HotelChain firstChain = mergeOrder.get(index);
						HotelChain secondChain = mergeOrder.get(index + 1);
						if (firstChain.getSize() < secondChain.getSize())
						{
							mergeOrder.remove(firstChain);
							mergeOrder.add(index + 1, firstChain);
						}
						else if (firstChain.getSize() == secondChain.getSize())
						{
							HotelChain chainToMergeFirst = currentPlayer
									.selectHotelToMergeFirst(firstChain,
											secondChain);
							if (chainToMergeFirst == secondChain)
							{
								mergeOrder.remove(firstChain);
								mergeOrder.add(index + 1, firstChain);
							}
						}
						index++;
					}
				}
			}

			for (HotelChain chain : mergeOrder)
			{
				chain.mergeHotelChain(survivingChain);
			}
		}

		boolean hasAdjoiningTileBeenPlayed = TileRegistry.getInstance()
				.hasAdjoiningTileBeenPlayed(this);
		if (hasAdjoiningTileBeenPlayed)
		{
			List<HotelChain> hotelChains = TileRegistry.getInstance()
					.getAdjoiningHotelChains(this);

			// There is an adjoining tile, but not an existing hotel chain yet.
			if (hotelChains.size() == 0)
			{
				// TODO - pop up a selection window to select hotel chain.
				List<HotelChain> availableHotels = TileRegistry.getInstance()
						.getAvailableHotelChains();
				if (!availableHotels.isEmpty())
				{
					Player player = TurnRegistry.getInstance()
							.currentPlayerTurn();
					this.currentHotelChain = player
							.selectHotelChainToStart(availableHotels);
					TileRegistry.getInstance().getAvailableHotelChains()
							.remove(this.currentHotelChain);
					TileRegistry.getInstance().getActiveHotelChains().add(
							this.currentHotelChain);

					// Give Free Stock to the one who started this company
					BankStockRegistry.getInstance().issueFreeStock(
							this.currentHotelChain, player);
				}
			}
			else if (hotelChains.size() == 1)
			{
				this.currentHotelChain = hotelChains.get(0);
			}
			TileRegistry.getInstance().paintAdjoiningTiles(this,
					this.currentHotelChain);
		}
		alreadyPlayed = true;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Tile))
		{
			return false;
		}

		Tile otherObject = (Tile) obj;

		return otherObject.getCharacterPosition() == getCharacterPosition()
				&& otherObject.getNumberPosition() == getNumberPosition();
	}

	/**
	 * @return the numberPosition
	 */
	public int getNumberPosition()
	{
		return numberPosition;
	}

	/**
	 * @return the characterPosition
	 */
	public int getCharacterPosition()
	{
		return characterPosition;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return characterPosition + 31 * numberPosition;
	}

	/**
	 * @return
	 */
	public JButton getChipBoardButton()
	{
		return chipBoardButton;
	}

	public boolean isAlreadyPlayed()
	{
		return alreadyPlayed;
	}

	/**
	 * @return
	 */
	public boolean isPlayable()
	{
		if (isStarterChip())
		{
			List<HotelChain> availableHotels = TileRegistry.getInstance()
					.getAvailableHotelChains();
			if (availableHotels.isEmpty())
			{
				return false;
			}
		}
		else if (TileRegistry.getInstance().isDeadChip(this))
		{
			return false;
		}

		return true;

	}

	public boolean isStarterChip()
	{
		boolean hasAdjoiningTileBeenPlayed = TileRegistry.getInstance()
				.hasAdjoiningTileBeenPlayed(this);
		if (hasAdjoiningTileBeenPlayed)
		{
			List<HotelChain> hotelChains = TileRegistry.getInstance()
					.getAdjoiningHotelChains(this);

			// There is an adjoining tile, but not an existing hotel chain yet.
			if (hotelChains.size() == 0)
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * @see java.awt.Component#toString()
	 */
	@Override
	public String toString()
	{
		return getButtonLabel();
	}

	/**
	 * @param hotelChain
	 */
	public void setHotelChain(HotelChain hotelChain)
	{
		this.currentHotelChain = hotelChain;
	}

	public void restoreGameState(TileState tileState)
	{
		setHotelChain(tileState.getCurrentHotelChain());
		this.alreadyPlayed = tileState.isAlreadyPlayed();
		getChipBoardButton().setBackground(tileState.getBackgroundColor());
		getChipBoardButton().setForeground(tileState.getForegroundColor());
	}
}
