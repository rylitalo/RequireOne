package com.byteperceptions.require.gui.stockpurchase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import com.byteperceptions.require.model.HotelChain;

/**
 * @author JDev
 * 
 */
public class ChooseHotelChainToStartDialog extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private static final Dimension PREFERRED_SIZE = new Dimension(300, 300);
	private SelectedHotelWrapper selectedHotelWrapper;

	public ChooseHotelChainToStartDialog(SelectedHotelWrapper selectedHotelWrapper,
			List<HotelChain> availableHotels)
	{
		super((Frame) null, true);
		this.selectedHotelWrapper = selectedHotelWrapper;
		this.setPreferredSize(PREFERRED_SIZE);
		this.setMinimumSize(PREFERRED_SIZE);
		this.setSize(PREFERRED_SIZE);
		this.setMaximumSize(PREFERRED_SIZE);
		setContentPane(createAvailableStocksButtonPanel(availableHotels));
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}

	/**
	 * @return
	 */
	private JPanel createAvailableStocksButtonPanel(
			List<HotelChain> availableHotels)
	{
		JPanel availableHotelsPanel = new JPanel();
		availableHotelsPanel.setLayout(new GridLayout(0, 1));
		availableHotelsPanel.setBorder(new TitledBorder(
				new SoftBevelBorder(BevelBorder.RAISED), "Select Hotel to Start"));

		for (HotelChain chain : availableHotels)
		{
			HotelPurchaseButton button = createStockPurchaseButton(chain);
			availableHotelsPanel.add(button);
		}

		return availableHotelsPanel;
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
			selectedHotelWrapper.setSelectedHotelChain(hotelChain);
			this.dispose();
		}
	}

}
