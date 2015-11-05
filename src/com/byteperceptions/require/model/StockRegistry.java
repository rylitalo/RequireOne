/**
 * 
 */
package com.byteperceptions.require.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author JDev
 * 
 */
public abstract class StockRegistry
{

	protected List<StockShare> availableLuxor;
	protected List<StockShare> availableTower;
	protected List<StockShare> availableWorldwide;
	protected List<StockShare> availableFestival;
	protected List<StockShare> availableAmerican;
	protected List<StockShare> availableContinental;
	protected List<StockShare> availableImperial;
	protected HashMap<HotelChain, List<StockShare>> sharesByHotelChain;

	protected StockRegistry()
	{
		availableLuxor = new ArrayList<StockShare>(25);
		availableTower = new ArrayList<StockShare>(25);
		availableWorldwide = new ArrayList<StockShare>(25);
		availableFestival = new ArrayList<StockShare>(25);
		availableAmerican = new ArrayList<StockShare>(25);
		availableContinental = new ArrayList<StockShare>(25);
		availableImperial = new ArrayList<StockShare>(25);
		
		sharesByHotelChain = new HashMap<HotelChain, List<StockShare>>();

		initializeSharesByHotelMap(HotelChain.LUXOR, availableLuxor);
		initializeSharesByHotelMap(HotelChain.TOWER, availableTower);
		initializeSharesByHotelMap(HotelChain.WORLDWIDE, availableWorldwide);
		initializeSharesByHotelMap(HotelChain.FESTIVAL, availableFestival);
		initializeSharesByHotelMap(HotelChain.AMERICAN, availableAmerican);
		initializeSharesByHotelMap(HotelChain.CONTINENTAL, availableContinental);
		initializeSharesByHotelMap(HotelChain.IMPERIAL, availableImperial);
		
		initialize();
	}

	
	/**
	 * @param hotelChain
	 * @param availableShares
	 */
	private void initializeSharesByHotelMap(HotelChain hotelChain,
		List<StockShare> availableShares)
	{
		sharesByHotelChain.put(hotelChain, availableShares);
	}
	
	public abstract void initialize();

	/**
	 * @see com.byteperceptions.require.model.StockRegistry#getNumberOfShares(com.byteperceptions.require.model.HotelChain)
	 */
	public int getNumberOfShares(HotelChain chain)
	{
		return sharesByHotelChain.get(chain).size();
	}
	
	public void addShares(List<StockShare> shares){
		for(StockShare stockShare : shares)
		{
			addShare(stockShare);
		}
	}


	/**
	 * @param stockShare
	 */
	public void addShare(StockShare stockShare)
	{
		 sharesByHotelChain.get(stockShare.getHotelChain()).add(stockShare);
	}
	
	/**
	 * @param stockShare
	 */
	public StockShare removeShare(HotelChain hotelChain)
	{
		 return sharesByHotelChain.get(hotelChain).remove(0);
	}

	public void clear()
	{
		availableLuxor.clear();
		availableTower.clear();
		availableWorldwide.clear();
		availableFestival.clear();
		availableAmerican.clear();
		availableContinental.clear();
		availableImperial.clear();
		initialize();
	}
}
