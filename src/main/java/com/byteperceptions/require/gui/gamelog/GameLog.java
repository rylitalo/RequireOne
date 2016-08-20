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
package com.byteperceptions.require.gui.gamelog;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import com.byteperceptions.require.gamestate.GameStateBroker;
import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.model.StockShare;
import com.byteperceptions.require.model.Tile;
import com.byteperceptions.require.turn.Turn;
import com.byteperceptions.require.turn.TurnChangedListener;
import com.byteperceptions.require.turn.TurnRegistry;


/**
 * @author Ryan Ylitalo
 *
 */
public class GameLog extends JPanel implements TurnChangedListener,
		ActionListener
{
	private static final long serialVersionUID = 1L;

	private GameLogTableModel tableModel;
	private JTable gameLogTable;
	private JButton enablePlayBackButton;
	private JButton resumePlayButton;
	private JButton resumeFromSelectionButton;
	private boolean selectionModeEnabled = false;

	public GameLog()
	{
		this.setBorder(new TitledBorder(
				new SoftBevelBorder(BevelBorder.RAISED), "Game Log"));

		gameLogTable = new JTable();
		this.setLayout(new GridLayout(2, 1));

		JScrollPane tradeTableScrollPane = new JScrollPane();
		tradeTableScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		tableModel = new GameLogTableModel();

		gameLogTable.setModel(tableModel);
		gameLogTable.setSurrendersFocusOnKeystroke(true);
		gameLogTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gameLogTable.setRowSelectionAllowed(true);

		SelectionListener listener = new SelectionListener(gameLogTable);
		gameLogTable.getSelectionModel().addListSelectionListener(listener);
		// gameLogTable.getColumnModel().getSelectionModel().addListSelectionListener(listener);

		tradeTableScrollPane.setViewportView(gameLogTable);
		tradeTableScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		this.add(tradeTableScrollPane);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new TitledBorder(new SoftBevelBorder(
				BevelBorder.RAISED), "Playback Mode"));
		enablePlayBackButton = new JButton("Playback Mode");
		enablePlayBackButton.addActionListener(this);
		enablePlayBackButton.setEnabled(false);

		resumePlayButton = new JButton("Resume");
		resumePlayButton.setEnabled(false);
		resumePlayButton.addActionListener(this);

		resumeFromSelectionButton = new JButton("Resume From Selected");
		resumeFromSelectionButton.setEnabled(false);
		resumeFromSelectionButton.addActionListener(this);

		buttonPanel.add(enablePlayBackButton);
		buttonPanel.add(resumePlayButton);
		buttonPanel.add(resumeFromSelectionButton);
		this.add(buttonPanel);

		initalizeColumnRenders(gameLogTable);

		TurnRegistry.getInstance().addTurnChangedListener(this);
	}

	private void initalizeColumnRenders(JTable table)
	{
		Enumeration<TableColumn> iterator = table.getColumnModel().getColumns();
		while (iterator.hasMoreElements())
		{
			TableColumn column = iterator.nextElement();
			column.setPreferredWidth(100);

			column.setCellRenderer(new GameLogTableCellRenderer());
		}
	}

	@Override
	public void turnChanged(Player currentTurn, Tile previouslyPlayedTile,
			Player previousPlayerTurn, List<StockShare> purchasedShares)
	{
		//Set button enabled after first player takes turn.
		enablePlayBackButton.setEnabled(true);
		tableModel.addRow(new Turn(previousPlayerTurn, purchasedShares,
				previouslyPlayedTile));
	}

	public class SelectionListener implements ListSelectionListener
	{
		JTable table;

		// It is necessary to keep the table since it is not possible
		// to determine the table from the event's source
		SelectionListener(JTable table)
		{
			this.table = table;
		}

		public void valueChanged(ListSelectionEvent e)
		{
			// Find the Table Row that was selected, and then update all the
			// Trade Details tabs with the
			// proper trade details.
			if (e.getSource() == table.getSelectionModel()
					&& table.getRowSelectionAllowed()
					&& !e.getValueIsAdjusting())
			{
				int selectedRowIdex = table.getSelectedRow();
				if (selectedRowIdex >= 0 && selectionModeEnabled)
				{
					resumeFromSelectionButton.setEnabled(true);
					GameStateBroker.getInstance().restoreGameState(
							gameLogTable.getSelectedRow(), true);
				}
				else
				{
					resumeFromSelectionButton.setEnabled(false);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == enablePlayBackButton)
		{
			selectionModeEnabled = true;
			enablePlayBackButton.setEnabled(false);
			resumePlayButton.setEnabled(true);
			if ( gameLogTable.getSelectedRow() >= 0 && selectionModeEnabled)
			{
				resumeFromSelectionButton.setEnabled(true);
				GameStateBroker.getInstance().restoreGameState(
						gameLogTable.getSelectedRow(), true);
			}
			
		}
		else if (e.getSource() == resumePlayButton)
		{
			GameStateBroker.getInstance().resumeGame();
			resumePlayButton.setEnabled(false);
			resumeFromSelectionButton.setEnabled(false);
			enablePlayBackButton.setEnabled(true);
			selectionModeEnabled = false;
		}
		else if (e.getSource() == resumeFromSelectionButton)
		{
			int selectedRow = gameLogTable.getSelectedRow();
			tableModel.restoreGameState(selectedRow);
			GameStateBroker.getInstance().restoreGameState(
					selectedRow, false);
			resumePlayButton.setEnabled(false);
			resumeFromSelectionButton.setEnabled(false);
			enablePlayBackButton.setEnabled(true);
			selectionModeEnabled = false;
		}
	}
}
