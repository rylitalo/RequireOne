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
package com.byteperceptions.require.strategy.scenario.playtile;

import java.util.Comparator;


/**
 * @author Ryan Ylitalo
 *
 */
public class HotelValueComparator implements Comparator<HotelValue>
{

	@Override
	public int compare(HotelValue o1, HotelValue o2)
	{
		return o1.getValue().compareTo(o2.getValue());
	}

}
