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

import com.byteperceptions.require.model.HotelChain;


/**
 * @author Ryan Ylitalo
 *
 */
public class StockSharesUnavailableException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public StockSharesUnavailableException(HotelChain chain){
		super("There are no shares available to purchase for hotel chain : "  + chain);
	}
}
