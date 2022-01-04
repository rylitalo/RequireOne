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
package com.byteperceptions.require;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

import com.byteperceptions.require.gamestate.GameStateBroker;
import com.byteperceptions.require.gui.chipboard.ChipBoard;
import com.byteperceptions.require.gui.gamelog.GameLog;
import com.byteperceptions.require.gui.scoresheet.ScoreSheet;
import com.byteperceptions.require.gui.tilerack.TileRack;
import com.byteperceptions.require.model.BankStockRegistry;
import com.byteperceptions.require.model.TileRegistry;
import com.byteperceptions.require.registry.PlayerRegistry;
import com.byteperceptions.require.turn.TurnRegistry;


/**
 * @author Ryan Ylitalo
 *
 */
public class RequireGameBoard implements ActionListener
{

	private JMenuItem newGameMenuItem;
	private JFrame frame;

	/**
	 * @param args
	 */
	public RequireGameBoard()
	{
		frame = new JFrame("Require");
		frame.setJMenuBar(createMenu());
		frame.setSize(950, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		init();
	}

	private void init()
	{
		frame.getContentPane().removeAll();
		ChipBoard chipBoard = new ChipBoard();

		try
		{
			 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			// handle exception
		}

		Require acquireGame = new Require(4, "You");

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		// constraints.weighty = 100;
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridheight = 10;
		leftPanel.add(chipBoard, constraints);

		constraints = new GridBagConstraints();
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 0;
		constraints.gridy = 11;
		constraints.gridheight = 1;
		leftPanel.add(new TileRack(acquireGame.getManualPlayer()), constraints);

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridBagLayout());

		constraints = new GridBagConstraints();
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridheight = 1;
		rightPanel.add(new ScoreSheet(), constraints);

		constraints = new GridBagConstraints();
		constraints.weighty = 2;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridheight = 2;
		rightPanel.add(new GameLog(), constraints);

		frame.getContentPane().setLayout(new GridBagLayout());

		constraints = new GridBagConstraints();
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 3;

		frame.getContentPane().add(leftPanel, constraints);

		constraints = new GridBagConstraints();
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 3;
		constraints.gridy = 0;
		constraints.gridwidth = 1;

		frame.getContentPane().add(rightPanel, constraints);
		// frame.setSize(1250, 800);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(false);
		frame.setVisible(true);
		
		//Capture the Initial Game State
		GameStateBroker.getInstance().captureGameState();
	}

	public JMenuBar createMenu()
	{
		// Where the GUI is created:
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;

		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the first menu.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription(
				"The only menu in this program that has menu items");
		menuBar.add(menu);

		// a group of JMenuItems
		menuItem = new JMenuItem("New Game", KeyEvent.VK_N);
		menuItem.addActionListener(this);
		newGameMenuItem = menuItem;
		menu.add(menuItem);

		return menuBar;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == newGameMenuItem)
		{
			PlayerRegistry.getInstance().clear();
			TileRegistry.getInstance().clear();
			BankStockRegistry.getInstance().clear();
			TurnRegistry.getInstance().clear();
			init();
		}
	}

	public static void main(String[] args)
	{
		new RequireGameBoard();
	}

}
