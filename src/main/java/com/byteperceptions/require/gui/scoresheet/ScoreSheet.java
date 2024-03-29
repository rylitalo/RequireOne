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
package com.byteperceptions.require.gui.scoresheet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import com.byteperceptions.require.BoardChangedListener;
import com.byteperceptions.require.RequireChangeListener;
import com.byteperceptions.require.gamestate.GameStateBroker;
import com.byteperceptions.require.gamestate.GameStateChangeListener;
import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.model.StockShare;
import com.byteperceptions.require.model.Tile;
import com.byteperceptions.require.registry.PlayerRegistry;
import com.byteperceptions.require.turn.TurnChangedListener;
import com.byteperceptions.require.turn.TurnRegistry;


/**
 * @author Ryan Ylitalo
 *
 */
public class ScoreSheet extends JPanel implements BoardChangedListener,
		TurnChangedListener, GameStateChangeListener
{
	private static final long serialVersionUID = 1L;

	private static final Dimension PREFERRED_SIZE = new Dimension(400, 300);

	private List<HotelStatisticsButton> hotelStatisticsButtons;
	private Color ORIGINAL_BUTTON_COLOR = new JButton().getBackground();
	private HashMap<Player, List<JButton>> buttonsForPlayerMap;

	public ScoreSheet()
	{
		hotelStatisticsButtons = new ArrayList<HotelStatisticsButton>();
		this.setLayout(new GridLayout(2, 1));

		this.setPreferredSize(PREFERRED_SIZE);
		this.setMinimumSize(PREFERRED_SIZE);
		this.setSize(PREFERRED_SIZE);
		this.setMaximumSize(PREFERRED_SIZE);

		buttonsForPlayerMap = new HashMap<Player, List<JButton>>();

		this.add(createPlayerScoreSheet());
		this.add(createHotelChainStatistics());
		RequireChangeListener.getInstance().addBoardChangedListener(this);
		TurnRegistry.getInstance().addTurnChangedListener(this);
		turnChanged(TurnRegistry.getInstance().currentPlayerTurn(), null, null,
				null);
		GameStateBroker.getInstance().addGameStateChangeListener(this);
	}

	/**
	 * @return
	 */
	private JPanel createHotelChainStatistics()
	{
		JPanel hotelChainStatistics = new JPanel();
		hotelChainStatistics.setLayout(new GridLayout(3, 10));
		hotelChainStatistics.setBorder(new TitledBorder(new SoftBevelBorder(
				BevelBorder.RAISED), "Hotels"));

		addButtonToPanel("Available", null, hotelChainStatistics);
		addButtonToPanel(" ", null, hotelChainStatistics);
		addSharesAvailableButtonToPanel(HotelChain.LUXOR, hotelChainStatistics);
		addSharesAvailableButtonToPanel(HotelChain.TOWER, hotelChainStatistics);
		addSharesAvailableButtonToPanel(HotelChain.WORLDWIDE,
				hotelChainStatistics);
		addSharesAvailableButtonToPanel(HotelChain.FESTIVAL,
				hotelChainStatistics);
		addSharesAvailableButtonToPanel(HotelChain.AMERICAN,
				hotelChainStatistics);
		addSharesAvailableButtonToPanel(HotelChain.CONTINENTAL,
				hotelChainStatistics);
		addSharesAvailableButtonToPanel(HotelChain.IMPERIAL,
				hotelChainStatistics);

		addButtonToPanel("Size", null, hotelChainStatistics);
		addButtonToPanel(" ", null, hotelChainStatistics);
		addChainSizeButtonToPanel(HotelChain.LUXOR, hotelChainStatistics);
		addChainSizeButtonToPanel(HotelChain.TOWER, hotelChainStatistics);
		addChainSizeButtonToPanel(HotelChain.WORLDWIDE, hotelChainStatistics);
		addChainSizeButtonToPanel(HotelChain.FESTIVAL, hotelChainStatistics);
		addChainSizeButtonToPanel(HotelChain.AMERICAN, hotelChainStatistics);
		addChainSizeButtonToPanel(HotelChain.CONTINENTAL, hotelChainStatistics);
		addChainSizeButtonToPanel(HotelChain.IMPERIAL, hotelChainStatistics);

		addButtonToPanel("Price", null, hotelChainStatistics);
		addButtonToPanel(" ", null, hotelChainStatistics);
		addChainPriceButtonToPanel(HotelChain.LUXOR, hotelChainStatistics);
		addChainPriceButtonToPanel(HotelChain.TOWER, hotelChainStatistics);
		addChainPriceButtonToPanel(HotelChain.WORLDWIDE, hotelChainStatistics);
		addChainPriceButtonToPanel(HotelChain.FESTIVAL, hotelChainStatistics);
		addChainPriceButtonToPanel(HotelChain.AMERICAN, hotelChainStatistics);
		addChainPriceButtonToPanel(HotelChain.CONTINENTAL, hotelChainStatistics);
		addChainPriceButtonToPanel(HotelChain.IMPERIAL, hotelChainStatistics);

		return hotelChainStatistics;
	}

	public JPanel createPlayerScoreSheet()
	{
		JPanel playerScoreSheet = new JPanel();
		playerScoreSheet.setLayout(new GridLayout(0, 9));
		playerScoreSheet.setBorder(new TitledBorder(new SoftBevelBorder(
				BevelBorder.RAISED), "Score Sheet"));

		addButtonToPanel(" ", null, playerScoreSheet);
		addButtonToPanel("Cash", null, playerScoreSheet);
		addButtonToPanel("L", Color.RED, playerScoreSheet);
		addButtonToPanel("T", Color.YELLOW, playerScoreSheet);
		addButtonToPanel("W", Color.ORANGE, playerScoreSheet);
		addButtonToPanel("F", Color.GREEN, playerScoreSheet);
		JButton americanButton = addButtonToPanel("A", Color.BLUE,
				playerScoreSheet);
		addButtonToPanel("C", Color.CYAN, playerScoreSheet);
		addButtonToPanel("I", Color.PINK, playerScoreSheet);

		for (Player player : PlayerRegistry.getInstance().getAllPlayers())
		{
			List<JButton> playerButtons = new ArrayList<JButton>();
			playerButtons.add(addButtonToPanel(player.getName(), null,
					playerScoreSheet));
			playerButtons.add(addPlayerCashButton(player, playerScoreSheet));
			playerButtons.add(addHotelStockButton(HotelChain.LUXOR, player,
					playerScoreSheet));
			playerButtons.add(addHotelStockButton(HotelChain.TOWER, player,
					playerScoreSheet));
			playerButtons.add(addHotelStockButton(HotelChain.WORLDWIDE, player,
					playerScoreSheet));
			playerButtons.add(addHotelStockButton(HotelChain.FESTIVAL, player,
					playerScoreSheet));
			playerButtons.add(addHotelStockButton(HotelChain.AMERICAN, player,
					playerScoreSheet));
			playerButtons.add(addHotelStockButton(HotelChain.CONTINENTAL,
					player, playerScoreSheet));
			playerButtons.add(addHotelStockButton(HotelChain.IMPERIAL, player,
					playerScoreSheet));
			buttonsForPlayerMap.put(player, playerButtons);
		}

		return playerScoreSheet;

	}

	/**
	 * @param luxor
	 * @param player
	 * @param playerScoreSheet
	 */
	private JButton addHotelStockButton(HotelChain hotelChain, Player player,
			JPanel playerScoreSheet)
	{
		HotelStockButton button = new HotelStockButton(hotelChain, player);
		button.setBorder(new LineBorder(Color.GRAY));
		hotelStatisticsButtons.add(button);
		playerScoreSheet.add(button);
		return button;
	}

	public JButton addButtonToPanel(String label, Color color, JPanel component)
	{
		JButton button = new JButton(label);
		button.setBorder(new LineBorder(Color.GRAY));
		if (color != null)
		{
			button.setBackground(color);
			button.setOpaque(true);
		}
		component.add(button);
		return button;
	}

	public void addChainSizeButtonToPanel(HotelChain hotelChain,
			JPanel component)
	{
		HotelSizeButton button = new HotelSizeButton(hotelChain);
		button.setBorder(new LineBorder(Color.GRAY));
		hotelStatisticsButtons.add(button);
		component.add(button);
	}

	public void addSharesAvailableButtonToPanel(HotelChain hotelChain,
			JPanel component)
	{
		HotelSharesAvailableButton button = new HotelSharesAvailableButton(
				hotelChain);
		button.setBorder(new LineBorder(Color.GRAY));
		hotelStatisticsButtons.add(button);
		component.add(button);
	}

	public void addChainPriceButtonToPanel(HotelChain hotelChain,
			JPanel component)
	{
		HotelPriceButton button = new HotelPriceButton(hotelChain);
		button.setBorder(new LineBorder(Color.GRAY));
		hotelStatisticsButtons.add(button);
		component.add(button);
	}

	public JButton addPlayerCashButton(Player player, JPanel component)
	{
		PlayerCashButton button = new PlayerCashButton(player);
		button.setBorder(new LineBorder(Color.GRAY));
		hotelStatisticsButtons.add(button);
		component.add(button);
		return button;
	}

	/**
	 * @see com.byteperceptions.require.BoardChangedListener#fireBoardChangedEvent()
	 */
	@Override
	public void fireBoardChangedEvent()
	{
		for (HotelStatisticsButton button : hotelStatisticsButtons)
		{
			button.setText(button.getButtonValueLabel());
		}
	}

	@Override
	public void turnChanged(Player nextPlayerTurn, Tile previouslyPlayedTile,
			Player previousPlayerTurn, List<StockShare> purchasedShares)
	{
		highlightRowForPlayer(nextPlayerTurn);
	}

	private void highlightRowForPlayer(Player player)
	{
		for (Map.Entry<Player, List<JButton>> entry : buttonsForPlayerMap
				.entrySet())
		{
			List<JButton> buttons = entry.getValue();
			if (entry.getKey() == player)
			{
				for (JButton button : buttons)
				{
					button.setBackground(Color.LIGHT_GRAY);
					button.setOpaque(true);
				}
			}
			else
			{
				for (JButton button : buttons)
				{
					button.setBackground(ORIGINAL_BUTTON_COLOR);
					button.setOpaque(true);
				}
			}
		}
	}

	@Override
	public void gameStateChanged()
	{
		highlightRowForPlayer(TurnRegistry.getInstance().currentPlayerTurn());
		
	}
}
