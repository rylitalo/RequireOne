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
package com.byteperceptions.require.gui.mergertransaction;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.byteperceptions.require.model.BankStockRegistry;
import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.ManualPlayer;
import com.byteperceptions.require.registry.ImageRegistry;


/**
 * @author Ryan Ylitalo
 *
 */
public class MergerTransactionDialog extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private static final Font BORDER_FONT = new Font(new JLabel().getFont()
			.getName(), Font.BOLD, 15);
	private static final Font STOCK_NUMBER_FONT = new Font(new JLabel()
			.getFont().getName(), Font.BOLD, 30);

	private static final ImageIcon UP_ARROW_ICON = new ImageIcon(
			loadImage("images/up_arrow.png"));
	private static final ImageIcon DOWN_ARROW_ICON = new ImageIcon(
			loadImage("images/down_arrow.png"));
	
	

	private JButton okButton;

	private ManualPlayer player;
	private HotelChain mergerSurvivor;
	private HotelChain mergerDeceased;

	private JButton incrementSharesSellButton;
	private JButton decrementSharesSellButton;

	private JButton incrementSharesTradeButton;
	private JButton decrementSharesTradeButton;

	private int numberOfSharesToKeep;
	private int numberOfSharesToTrade;
	private int numberOfSharesToSell;

	private JLabel numberOfSharesToKeepLabel;
	private JLabel numberOfSharesToSellLabel;
	private JLabel numberOfSharesToTradeLabel;

	private JButton keepAllButton;
	private JButton sellRemainingButton;
	private JButton tradeMaximumButton;

	public MergerTransactionDialog(ManualPlayer player,
			HotelChain mergerSurvivor, HotelChain mergerDeceased)
	{
		super((Frame) null, true);
		this.player = player;

		this.mergerSurvivor = mergerSurvivor;
		this.mergerDeceased = mergerDeceased;
		numberOfSharesToKeep = player.getStockRegistry().getNumberOfShares(
				mergerDeceased);
		numberOfSharesToTrade = 0;
		numberOfSharesToSell = 0;

		keepAllButton = new JButton("All");
		keepAllButton.addActionListener(this);

		sellRemainingButton = new JButton("Remaining");
		sellRemainingButton.addActionListener(this);

		tradeMaximumButton = new JButton("Maximum");
		tradeMaximumButton.addActionListener(this);

		incrementSharesSellButton = new JButton(UP_ARROW_ICON);
		incrementSharesSellButton.addActionListener(this);

		decrementSharesSellButton = new JButton(DOWN_ARROW_ICON);
		decrementSharesSellButton.addActionListener(this);

		incrementSharesTradeButton = new JButton(UP_ARROW_ICON);
		incrementSharesTradeButton.addActionListener(this);
		if (BankStockRegistry.getInstance().getNumberOfShares(mergerSurvivor) == 0)
		{
			incrementSharesTradeButton.setEnabled(false);
		}

		decrementSharesTradeButton = new JButton(DOWN_ARROW_ICON);
		decrementSharesTradeButton.addActionListener(this);

		okButton = new JButton("OK");
		okButton.addActionListener(this);

		JPanel rootPanel = new JPanel();
		rootPanel.setLayout(new GridLayout(4, 1));
		rootPanel.add(createKeepSharesPanel());
		rootPanel.add(createTradeSharesPanel());
		rootPanel.add(createSellSharesPanel());
		rootPanel.add(okButton);

		setContentPane(rootPanel);

		Dimension dialogDimension = new Dimension(325, 400);
		this.setPreferredSize(dialogDimension);
		this.setMinimumSize(dialogDimension);
		this.setSize(dialogDimension);
		this.setMaximumSize(dialogDimension);

	}

	private JPanel createSellSharesPanel()
	{
		JPanel panel = createBorderedPanel(null, Color.BLACK, "Sell");
		panel.setLayout(new GridLayout(1, 3));

		numberOfSharesToSellLabel = createStockLabel(numberOfSharesToSell);
		panel.add(numberOfSharesToSellLabel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 2));

		JPanel spacerPanel1 = new JPanel();
		JPanel spacerPanel2 = new JPanel();

		buttonPanel.add(incrementSharesSellButton);
		buttonPanel.add(spacerPanel1);
		buttonPanel.add(decrementSharesSellButton);
		buttonPanel.add(spacerPanel2);
		panel.add(buttonPanel);

		panel.add(sellRemainingButton);
		return panel;
	}

	private Component createTradeSharesPanel()
	{
		Color borderColor = Color.BLACK;

		JPanel panel = createBorderedPanel(mergerSurvivor.getColor(), borderColor,  "Trade");
		panel.setLayout(new GridLayout(1, 3));

		numberOfSharesToTradeLabel = createStockLabel(numberOfSharesToTrade);
		panel.add(numberOfSharesToTradeLabel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 2));

		JPanel spacerPanel1 = new JPanel();
		spacerPanel1.setBackground(mergerSurvivor.getColor());

		JPanel spacerPanel2 = new JPanel();
		spacerPanel2.setBackground(mergerSurvivor.getColor());

		buttonPanel.add(incrementSharesTradeButton);
		buttonPanel.add(spacerPanel1);
		buttonPanel.add(decrementSharesTradeButton);
		buttonPanel.add(spacerPanel2);
		panel.add(buttonPanel);

		panel.add(tradeMaximumButton);
		return panel;
	}

	private Component createKeepSharesPanel()
	{
		Color borderColor = Color.BLACK;

		JPanel panel = createBorderedPanel(mergerDeceased.getColor(), borderColor,  "Keep");
		panel.setLayout(new GridLayout(1, 3));

		numberOfSharesToKeepLabel = createStockLabel(numberOfSharesToKeep);
		panel.add(numberOfSharesToKeepLabel);

		// Spacer Panel
		JPanel spacerPanel = new JPanel();
		spacerPanel.setBackground(mergerDeceased.getColor());
		panel.add(spacerPanel);

		panel.add(keepAllButton);
		return panel;
	}

	private JPanel createBorderedPanel(Color backgroundColor, Color borderColor,  String title)
	{
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(borderColor, 2, false),
				title, TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, BORDER_FONT, borderColor));
		if (backgroundColor != null)
		{
			panel.setBackground(backgroundColor);
		}
		return panel;
	}

	private JLabel createStockLabel(int numberOfStocks)
	{
		JLabel label = new JLabel("      " + numberOfStocks);
		label.setFont(STOCK_NUMBER_FONT);
		return label;
	}

	private void refreshLabels()
	{
		numberOfSharesToKeepLabel.setText("      " + numberOfSharesToKeep);
		numberOfSharesToTradeLabel.setText("      " + numberOfSharesToTrade);
		numberOfSharesToSellLabel.setText("      " + numberOfSharesToSell);
		if (BankStockRegistry.getInstance().getNumberOfShares(mergerSurvivor) <= numberOfSharesToTrade)
		{
			incrementSharesTradeButton.setEnabled(false);
		}
		else
		{
			incrementSharesTradeButton.setEnabled(true);
		}

		if (numberOfSharesToTrade == 0)
		{
			decrementSharesTradeButton.setEnabled(false);
		}
		else
		{
			decrementSharesTradeButton.setEnabled(true);
		}

		if (numberOfSharesToSell == 0)
		{
			decrementSharesSellButton.setEnabled(false);
		}
		else
		{
			decrementSharesSellButton.setEnabled(true);
		}

		if (numberOfSharesToSell == player.getStockRegistry()
				.getNumberOfShares(mergerDeceased))
		{
			incrementSharesSellButton.setEnabled(false);
		}
		else
		{
			incrementSharesSellButton.setEnabled(true);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == incrementSharesSellButton)
		{
			if (numberOfSharesToKeep > 0)
			{
				numberOfSharesToSell++;
				numberOfSharesToKeep--;
			}
		}
		else if (e.getSource() == decrementSharesSellButton)
		{
			if (numberOfSharesToKeep < player.getStockRegistry()
					.getNumberOfShares(mergerDeceased))
			{
				numberOfSharesToSell--;
				numberOfSharesToKeep++;
			}
		}
		else if (e.getSource() == incrementSharesTradeButton)
		{
			if (numberOfSharesToKeep >= 2)
			{
				numberOfSharesToTrade++;
				numberOfSharesToKeep -= 2;
			}

		}
		else if (e.getSource() == decrementSharesTradeButton)
		{
			if (numberOfSharesToTrade > 0)
			{
				numberOfSharesToTrade--;
				numberOfSharesToKeep += 2;
			}
		}
		else if (e.getSource() == keepAllButton)
		{
			numberOfSharesToKeep = player.getStockRegistry().getNumberOfShares(
					mergerDeceased);
			numberOfSharesToTrade = 0;
			numberOfSharesToSell = 0;
		}
		else if (e.getSource() == sellRemainingButton)
		{
			while (numberOfSharesToKeep > 0)
			{
				numberOfSharesToKeep--;
				numberOfSharesToSell++;
			}
		}
		else if (e.getSource() == tradeMaximumButton)
		{
			numberOfSharesToKeep = player.getStockRegistry().getNumberOfShares(
					mergerDeceased);
			numberOfSharesToTrade = 0;
			numberOfSharesToSell = 0;

			while (numberOfSharesToKeep >= 2
					&& BankStockRegistry.getInstance().getNumberOfShares(
							mergerSurvivor) > numberOfSharesToTrade)
			{
				numberOfSharesToTrade++;
				numberOfSharesToKeep -= 2;
			}
		}
		refreshLabels();

		if (e.getSource() == okButton)
		{
			saveSharePurchases();
			dispose();
		}

	}

	private void saveSharePurchases()
	{
		BankStockRegistry.getInstance().sellShares(player, mergerDeceased,
				numberOfSharesToSell);
		BankStockRegistry.getInstance().tradeStocks(player, mergerSurvivor,
				mergerDeceased, numberOfSharesToTrade);
	}

	private static Image loadImage(String path)
	{
		Image img = null;

		try
		{
			img = loadImageWithClassloader(path, img, ClassLoader.getSystemClassLoader());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			img = loadImageWithClassloader(path, img, Thread.currentThread().getContextClassLoader());
		}
		if(img == null){
			img = ImageRegistry.getInstance().loadImage(path);
		}
		return img;
	}

	private static Image loadImageWithClassloader(String path, Image img,
			ClassLoader loader) 
	{
		URL url = loader.getResource(path);
		Toolkit tk = Toolkit.getDefaultToolkit();
		img = tk.getImage(url);
		return img;
	}

}
