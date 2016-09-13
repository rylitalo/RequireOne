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
package com.byteperceptions.require.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.byteperceptions.require.model.BankStockRegistry;
import com.byteperceptions.require.model.HotelChain;
import com.byteperceptions.require.model.Player;
import com.byteperceptions.require.model.StockShare;
import com.byteperceptions.require.model.Tile;
import com.byteperceptions.require.model.TileRegistry;
import com.byteperceptions.require.strategy.scenario.playtile.PlayTileScenario;
import com.byteperceptions.require.strategy.scenario.playtile.PlayTileScenarioIsMerger;
import com.byteperceptions.require.strategy.scenario.playtile.PlayTileScenarioIsMergerSoleMajorityForSingleDeceasedHotel;
import com.byteperceptions.require.strategy.scenario.playtile.PlayTileScenarioIsStarterTile;
import com.byteperceptions.require.strategy.scenario.purchasestocks.PurchaseScenario;
import com.byteperceptions.require.strategy.scenario.purchasestocks.PurchaseScenarioAreSharesAvailableForPurchase;
import com.byteperceptions.require.strategy.scenario.purchasestocks.PurchaseScenarioCanAffordStock;
import com.byteperceptions.require.strategy.scenario.purchasestocks.PurchaseScenarioCanBeMajorityOwner;
import com.byteperceptions.require.strategy.scenario.purchasestocks.PurchaseScenarioCanBeMinorityOwner;
import com.byteperceptions.require.strategy.scenario.purchasestocks.PurchaseScenarioHaveFourStockMinorityLead;
import com.byteperceptions.require.strategy.scenario.purchasestocks.PurchaseScenarioHaveFourStockMajorityLead;
import com.byteperceptions.require.strategy.scenario.purchasestocks.PurchaseScenarioSoleMajorityMinorityExists;
import com.byteperceptions.require.strategy.scenario.selectsurvivor.SelectSurvivorScenario;
import com.byteperceptions.require.strategy.scenario.selectsurvivor.SelectSurvivorScenarioNeedMoney;
import com.byteperceptions.require.strategy.scenario.starthotel.StartHotelScenario;
import com.byteperceptions.require.strategy.scenario.starthotel.StartHotelScenarioExistingShares;


/**
 * @author Ryan Ylitalo
 *
 */
public class GeneticMutationEngine
{
	private List<PurchaseScenario> purchaseScenarios = new ArrayList<PurchaseScenario>();
	private List<PlayTileScenario> playTileScenarios = new ArrayList<PlayTileScenario>();
	private List<StartHotelScenario> startHotelScenarios = new ArrayList<StartHotelScenario>();
	private List<SelectSurvivorScenario> selectSurvivorScenarios = new ArrayList<SelectSurvivorScenario>();

	private static final GeneticMutationEngine INSTANCE = new GeneticMutationEngine();

	private GeneticMutationEngine()
	{
		purchaseScenarios.add(new PurchaseScenarioCanAffordStock());
		purchaseScenarios.add(new PurchaseScenarioCanBeMajorityOwner());
		purchaseScenarios.add(new PurchaseScenarioSoleMajorityMinorityExists());
		purchaseScenarios
				.add(new PurchaseScenarioAreSharesAvailableForPurchase());
		purchaseScenarios.add(new PurchaseScenarioCanBeMinorityOwner());
		purchaseScenarios.add(new PurchaseScenarioHaveFourStockMinorityLead());
		purchaseScenarios.add(new PurchaseScenarioHaveFourStockMajorityLead());

		playTileScenarios
				.add(new PlayTileScenarioIsMergerSoleMajorityForSingleDeceasedHotel());
		playTileScenarios.add(new PlayTileScenarioIsStarterTile());
		playTileScenarios.add(new PlayTileScenarioIsMerger());

		startHotelScenarios.add(new StartHotelScenarioExistingShares());

		selectSurvivorScenarios.add(new SelectSurvivorScenarioNeedMoney());
	}

	public static GeneticMutationEngine getInstance()
	{
		return INSTANCE;
	}

	public List<StockShare> purchaseStockShares(Player player)
	{

		List<StockShare> purchasedShares = new ArrayList<StockShare>(3);
		for (int i = 0; i < 3; i++)
		{
			List<HotelChain> activeHotelChains = TileRegistry.getInstance()
					.getActiveHotelChains();

			int maxValueStock = 0;
			HotelChain hotelChainToPurchase = null;

			for (HotelChain activeChain : activeHotelChains)
			{
				int valueOfStockPurchase = 0;
				for (PurchaseScenario purchaseScenario : purchaseScenarios)
				{
					int scenarioValue = purchaseScenario.calculateValue(
							player, activeChain, 3 - i);
					System.out.println(player + ":" +(i+1)+ ":" +activeChain + " :" + purchaseScenario.getClass().getSimpleName() + ":"+  scenarioValue);
					
					valueOfStockPurchase += scenarioValue;
				}
				System.out.println(player + ":" +(i+1)+ ":" +activeChain + " :" +valueOfStockPurchase);
				
				if (valueOfStockPurchase > maxValueStock
						&& valueOfStockPurchase >= 0)
				{
					maxValueStock = valueOfStockPurchase;
					hotelChainToPurchase = activeChain;
				}
			}

			if (hotelChainToPurchase != null)
			{
				StockShare stockShare = BankStockRegistry.getInstance()
						.brokerSharePurchase(hotelChainToPurchase, player);
				purchasedShares.add(stockShare);
				System.out.println(player + " purchased:" +(i+1)+ ":" +stockShare.getHotelChain());
				
			}
		}

		return purchasedShares;
	}

	public Tile playTile(Player player)
	{

		int maxValueTilePlay = -100000;
		Tile tileToPlay = null;
		for (Tile tile : player.getTiles())
		{
			if (tile.isPlayable())
			{

				int valueOfTilePlay = 0;
				for (PlayTileScenario playTileScenario : playTileScenarios)
				{
					valueOfTilePlay += playTileScenario.calculateValue(player,
							tile);
				}
				if (valueOfTilePlay > maxValueTilePlay)
				{
					maxValueTilePlay = valueOfTilePlay;
					tileToPlay = tile;
				}
			}
		}

		if (tileToPlay != null)
		{
			player.getTiles().remove(tileToPlay);
			tileToPlay.playTile();
		}

		return tileToPlay;
	}

	/**
	 * @param player
	 * @return
	 */
	public HotelChain selectHotelChainToStart(Player player)
	{
		int maxValueHotelStart = -100000;

		List<HotelChain> availableHotels = TileRegistry.getInstance()
				.getAvailableHotelChains();

		HotelChain hotelToStart = null;

		for (HotelChain availableHotel : availableHotels)
		{

			int valueOfHotelStart = 0;
			for (StartHotelScenario startHotelScenario : startHotelScenarios)
			{
				valueOfHotelStart += startHotelScenario.calculateValue(player,
						availableHotel);
			}
			if (valueOfHotelStart > maxValueHotelStart)
			{
				maxValueHotelStart = valueOfHotelStart;
				hotelToStart = availableHotel;
			}
		}

		return hotelToStart;
	}

	public void transactStocksAfterMerger(Player player,
			HotelChain mergerSurvivor, HotelChain mergerDeceased)
	{
		//TODO - IF Everyone else keeps and you are way back... get rid of them.
		
		if (HotelChain.getLargestChain().getSize() > 16)
		{
			int determineTradeAmount = Math.min(player.getStockRegistry()
					.getNumberOfShares(mergerDeceased) / 2, BankStockRegistry
					.getInstance().getNumberOfShares(mergerSurvivor));
			
			if((player.getStockRegistry().getNumberOfShares(mergerSurvivor) + determineTradeAmount) >= 8)
			{
				BankStockRegistry.getInstance().tradeStocks(player, mergerSurvivor,
						mergerDeceased, determineTradeAmount);
				
			}
		}

		if (HotelChain.getLargestChain().getSize() > 29)
		{
			int determineTradeAmount = Math.min(player.getStockRegistry()
					.getNumberOfShares(mergerDeceased) / 2, BankStockRegistry
					.getInstance().getNumberOfShares(mergerSurvivor));
			
			//Even Trade
			if( (mergerDeceased.getPrice() * 2) <= mergerSurvivor.getPrice()){
				BankStockRegistry.getInstance().tradeStocks(player, mergerSurvivor,
						mergerDeceased, determineTradeAmount);
			}
			
			BankStockRegistry.getInstance().sellAllShares(player,
					mergerDeceased);
		}

		if (new Random().nextInt() % 3 == 0)
		{
			int determineTradeAmount = Math.min(player.getStockRegistry()
					.getNumberOfShares(mergerDeceased) / 2, BankStockRegistry
					.getInstance().getNumberOfShares(mergerSurvivor));
			BankStockRegistry.getInstance().tradeStocks(player, mergerSurvivor,
					mergerDeceased, determineTradeAmount);
		}
	}

	public HotelChain selectSurvivingMergerChain(Player player, Tile tile)
	{
		List<HotelChain> survivingChains = TileRegistry.getInstance()
				.getSurvivingChain(tile);

		HotelChain survivingChain = null;
		int maxValueHotelSurvivor = -100000;
		for (HotelChain potentialSurvivor : survivingChains)
		{
			int valueOfHotelSurvive = 0;
			for (SelectSurvivorScenario selectSurvivorScenario : selectSurvivorScenarios)
			{
				valueOfHotelSurvive += selectSurvivorScenario.calculateValue(
						player, potentialSurvivor);
			}
			if (valueOfHotelSurvive > maxValueHotelSurvivor)
			{
				maxValueHotelSurvivor = valueOfHotelSurvive;
				survivingChain = potentialSurvivor;
			}
		}

		return survivingChain;
	}

}
