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
package com.byteperceptions.require.gui.stockpurchase;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import com.byteperceptions.require.model.BankStockRegistry;
import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.ManualPlayer;
import com.byteperceptions.require.model.StockShare;
import com.byteperceptions.require.model.TileRegistry;


/**
 * @author Ryan Ylitalo
 *
 */
public class BuyStocksDialog extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private static final Dimension PREFERRED_SIZE = new Dimension(300, 300);
	private JButton okButton;
	private JButton cancelButton;
	private JButton endGameButton;
	private JLabel availableCashLabel;
	
	private List<StocksPurchaseButton> stocksToPurchaseButtons = new ArrayList<StocksPurchaseButton>(
			3);
	private List<HotelPurchaseButton> availableHotelStocksToPurchase = new ArrayList<HotelPurchaseButton>(
			7);
	private ManualPlayer player;
	private int totalPlayerCash;
	
	//This is only a mechanism to return the purchased stocks from the dialog
	private transient List<StockShare> purchasedShares;

	public BuyStocksDialog(ManualPlayer player)
	{
		super((Frame) null, true);
		this.player = player;
		totalPlayerCash = player.getCash();

		this.okButton = new JButton("OK");
		this.okButton.addActionListener(this);
		this.cancelButton = new JButton("Cancel");
		this.cancelButton.addActionListener(this);
		this.endGameButton = new JButton("End Game");
		this.endGameButton.addActionListener(this);

		JPanel stocksPanel = new JPanel();
		stocksPanel.setLayout(new GridLayout(1, 2));
		stocksPanel.add(createAvailableStocksButtonPanel());
		stocksPanel.add(createCurrentlyPurchasedStocksButton());
		stocksPanel.setPreferredSize(PREFERRED_SIZE);
		stocksPanel.setMinimumSize(PREFERRED_SIZE);
		stocksPanel.setSize(PREFERRED_SIZE);
		stocksPanel.setMaximumSize(PREFERRED_SIZE);

		JPanel rootPanel = new JPanel();
		rootPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridheight = 4;
		constraints.gridwidth = 4;
		constraints.fill = GridBagConstraints.BOTH;
		rootPanel.add(stocksPanel, constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridheight = 1;
		constraints.gridwidth = 4;
		constraints.fill = GridBagConstraints.BOTH;
		rootPanel.add(createAvailableCashPanel(), constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridheight = 1;
		constraints.gridwidth = 4;
		constraints.fill = GridBagConstraints.BOTH;
		rootPanel.add(createButtonPanel(), constraints);
		rootPanel.setPreferredSize(PREFERRED_SIZE);
		rootPanel.setMinimumSize(PREFERRED_SIZE);
		rootPanel.setSize(PREFERRED_SIZE);
		rootPanel.setMaximumSize(PREFERRED_SIZE);

		setContentPane(rootPanel);

		Dimension dialogDimension = new Dimension(325, 400);
		this.setPreferredSize(dialogDimension);
		this.setMinimumSize(dialogDimension);
		this.setSize(dialogDimension);
		this.setMaximumSize(dialogDimension);

	}

	private Component createAvailableCashPanel()
	{
		JPanel buttonPanel = new JPanel();
		availableCashLabel = new JLabel("Cash Available : $" + totalPlayerCash);
		buttonPanel.add(availableCashLabel);
		return buttonPanel;
	}

	/**
	 * @return
	 */
	private Component createCurrentlyPurchasedStocksButton()
	{
		JPanel stocksPurchasedPanel = new JPanel();
		stocksPurchasedPanel.setPreferredSize(PREFERRED_SIZE);
		stocksPurchasedPanel.setMinimumSize(PREFERRED_SIZE);
		stocksPurchasedPanel.setSize(PREFERRED_SIZE);
		stocksPurchasedPanel.setMaximumSize(PREFERRED_SIZE);

		stocksPurchasedPanel.setLayout(new GridLayout(3, 0));
		stocksPurchasedPanel.setBorder(new TitledBorder(new SoftBevelBorder(
				BevelBorder.RAISED), "Stock Purchases"));

		for (int i = 0; i < 3; i++)
		{
			StocksPurchaseButton button = new StocksPurchaseButton();
			button.addActionListener(this);
			stocksPurchasedPanel.add(button);
			stocksToPurchaseButtons.add(button);
		}

		return stocksPurchasedPanel;
	}

	private JPanel createButtonPanel()
	{
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 3));
		buttonPanel.setBorder(new TitledBorder(new SoftBevelBorder(
				BevelBorder.RAISED), ""));
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		
		if( TileRegistry.getInstance().canEndGame())
		{
			buttonPanel.add(endGameButton);
		}
		
		return buttonPanel;
	}

	/**
	 * @return
	 */
	private JPanel createAvailableStocksButtonPanel()
	{
		JPanel availableStocksButtonPanel = new JPanel();
		availableStocksButtonPanel.setPreferredSize(PREFERRED_SIZE);
		availableStocksButtonPanel.setMinimumSize(PREFERRED_SIZE);
		availableStocksButtonPanel.setSize(PREFERRED_SIZE);
		availableStocksButtonPanel.setMaximumSize(PREFERRED_SIZE);
		availableStocksButtonPanel.setLayout(new GridLayout(0, 1));
		availableStocksButtonPanel.setBorder(new TitledBorder(
				new SoftBevelBorder(BevelBorder.RAISED), "Available Hotels"));

		for (HotelChain chain : TileRegistry.getInstance()
				.getActiveHotelChains())
		{
			if (BankStockRegistry.getInstance().getNumberOfShares(chain) > 0)
			{
				HotelPurchaseButton button = createStockPurchaseButton(chain);
				setButtonVisibility(button);
				availableHotelStocksToPurchase.add(button);
				availableStocksButtonPanel.add(button);
			}
		}

		return availableStocksButtonPanel;
	}

	private void setButtonVisibility(HotelPurchaseButton button)
	{
		if (button.getSharesRemaining() == 0)
		{
			button.setEnabled(false);
			button.setText("None Available");
		}
		else if (totalPlayerCash < button.getHotelChain().getPrice())
		{
			button.setEnabled(false);
			button.setText("Insufficient Funds");
		}
		else
		{
			button.setEnabled(true);
			button.setText(button.getHotelChain().getLabel()  + " $" + button.getHotelChain().getPrice());
		}
	}

	private HotelPurchaseButton createStockPurchaseButton(HotelChain hotelChain)
	{
		HotelPurchaseButton button = new HotelPurchaseButton(hotelChain);
		button.setBorder(new LineBorder(Color.GRAY));
		button.addActionListener(this);
		return button;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() instanceof HotelPurchaseButton)
		{
			HotelPurchaseButton button = (HotelPurchaseButton) e.getSource();
			HotelChain hotelChain = button.getHotelChain();
			for (StocksPurchaseButton purchaseButton : stocksToPurchaseButtons)
			{
				if (purchaseButton.getHotelPurchaseButton() == null)
				{
					purchaseButton.setHotelPurchaseButton(button);
					totalPlayerCash -= hotelChain.getPrice();
					refreshPurchaseButtons();
					return;
				}
			}
		}
		else if (e.getSource() instanceof StocksPurchaseButton)
		{
			StocksPurchaseButton stocksPurchaseButton = (StocksPurchaseButton) e
					.getSource();
			if (stocksPurchaseButton.getHotelPurchaseButton() != null)
			{
				stocksPurchaseButton.getHotelPurchaseButton()
						.incrementSharesRemaining();
				totalPlayerCash += stocksPurchaseButton
						.getHotelPurchaseButton().getHotelChain().getPrice();
			}

			stocksPurchaseButton.setHotelPurchaseButton(null);
			refreshPurchaseButtons();
		}
		else if (e.getSource() == cancelButton)
		{
			this.dispose();
		}
		else if (e.getSource() == okButton)
		{
			saveStockPurchases();
			this.dispose();
		}
		else if(e.getSource() == endGameButton)
		{
			saveStockPurchases();
			player.setShouldEndGame(true);
			this.dispose();
		}
	}

	private void refreshPurchaseButtons()
	{
		for (HotelPurchaseButton button : availableHotelStocksToPurchase)
		{
			setButtonVisibility(button);
		}
		availableCashLabel.setText("Cash Available : $" + totalPlayerCash);
	}

	private void saveStockPurchases()
	{
		purchasedShares = new ArrayList<StockShare>(3);
		for (StocksPurchaseButton purchaseButton : stocksToPurchaseButtons)
		{
			if (purchaseButton.getHotelPurchaseButton() != null)
			{
				StockShare purchasedShare = BankStockRegistry.getInstance().brokerSharePurchase(
						purchaseButton.getHotelPurchaseButton().getHotelChain(), player);
				purchasedShares.add(purchasedShare);
			}
		}

	}

	public List<StockShare> getPurchasedShares()
	{
		return purchasedShares;
	}

}
