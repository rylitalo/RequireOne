/**
 * 
 */
package com.byteperceptions.require.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.byteperceptions.require.RequireChangeListener;
import com.byteperceptions.require.gui.mergertransaction.MergerTransactionDialog;
import com.byteperceptions.require.gui.stockpurchase.BuyStocksDialog;
import com.byteperceptions.require.gui.stockpurchase.ChooseHotelChainToStartDialog;
import com.byteperceptions.require.gui.stockpurchase.SelectedHotelWrapper;

/**
 * @author JDev
 * 
 */
public class ManualPlayer extends Player
{
	private boolean shouldEndGame = false;

	public ManualPlayer(String name)
	{
		super(name);
	}

	/**
	 * @see com.byteperceptions.require.AcquirePlayer#takeTurn()
	 */
	@Override
	public void takeTurn()
	{
		// This is all handled manually at for the manual player;
	}

	/**
	 * @see com.byteperceptions.require.model.Player#selectHotelChainToStart()
	 */
	@Override
	public HotelChain selectHotelChainToStart(List<HotelChain> availableHotels)
	{
		SelectedHotelWrapper selectedHotelWrapper = new SelectedHotelWrapper();
		new ChooseHotelChainToStartDialog(selectedHotelWrapper, availableHotels)
				.setVisible(true);
		return selectedHotelWrapper.getSelectedHotelChain();
	}

	/**
	 * @see com.byteperceptions.require.model.Player#purchaseStocks()
	 */
	@Override
	public List<StockShare> purchaseStocks()
	{

		if (canPurchaseShares() || TileRegistry.getInstance().canEndGame())
		{
			BuyStocksDialog dialog = new BuyStocksDialog(this);
			dialog.setVisible(true);
			return dialog.getPurchasedShares();
		}
		return new ArrayList<StockShare>(3);
	}

	private boolean canPurchaseShares()
	{
		List<HotelChain> activeChains = TileRegistry.getInstance()
				.getActiveHotelChainsWithSharesForPurchase();
		for (HotelChain chain : activeChains)
		{
			if (BankStockRegistry.getInstance().getNumberOfShares(chain) > 0
					&& chain.getPrice() <= getCash())
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * @see com.byteperceptions.require.model.Player#selectSurvivingMergerChain()
	 */
	@Override
	public HotelChain selectSurvivingMergerChain(Tile tile)
	{
		HotelChain survivingChain = null;
		List<HotelChain> survivingChains = TileRegistry.getInstance()
				.getSurvivingChain(tile);
		if (survivingChains.size() > 1)
		{
			int selectMergerSurvivor = JOptionPane.showOptionDialog(null,
					"Please select the surviving hotel chain.",
					"Merger Survivor", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, survivingChains
							.toArray(), survivingChains.get(0));
			survivingChain = survivingChains.get(selectMergerSurvivor);
		}
		else
		{
			survivingChain = survivingChains.get(0);
		}

		return survivingChain;

	}

	/**
	 * @see com.byteperceptions.require.model.Player#transactMergerStocks(com.byteperceptions.require.model.HotelChain,
	 *      com.byteperceptions.require.model.HotelChain)
	 */
	@Override
	public void transactMergerStocks(HotelChain mergerSurvivor,
			HotelChain mergerDeceased)
	{
		if (getStockRegistry().getNumberOfShares(mergerDeceased) > 0)
		{
			RequireChangeListener.getInstance().fireBoardChangedEvent();
			new MergerTransactionDialog(this, mergerSurvivor, mergerDeceased)
					.setVisible(true);
		}

	}

	/**
	 * @see com.byteperceptions.require.model.Player#shouldEndGame()
	 */
	@Override
	public boolean shouldEndGame()
	{
		return shouldEndGame;
	}

	public void setShouldEndGame(boolean shouldEndGame)
	{
		this.shouldEndGame = shouldEndGame;
	}

	@Override
	public HotelChain selectHotelToMergeFirst(HotelChain firstChain,
			HotelChain secondChain)
	{
		HotelChain[] chains = {firstChain, secondChain};
		int selectFirstMerge = JOptionPane.showOptionDialog(null,
				"Please select the hotel chain to merge first.",
				"Hotel to Merge First", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, chains, firstChain);
		return chains[selectFirstMerge];
	}

}
