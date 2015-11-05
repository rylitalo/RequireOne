package com.byteperceptions.require.strategy.scenario.playtile;

import java.util.Comparator;

public class HotelValueComparator implements Comparator<HotelValue>
{

	@Override
	public int compare(HotelValue o1, HotelValue o2)
	{
		return o1.getValue().compareTo(o2.getValue());
	}

}
