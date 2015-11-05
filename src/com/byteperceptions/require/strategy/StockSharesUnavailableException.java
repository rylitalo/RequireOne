package com.byteperceptions.require.strategy;

import com.byteperceptions.require.model.HotelChain;

public class StockSharesUnavailableException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public StockSharesUnavailableException(HotelChain chain){
		super("There are no shares available to purchase for hotel chain : "  + chain);
	}
}
