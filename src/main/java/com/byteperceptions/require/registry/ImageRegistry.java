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
package com.byteperceptions.require.registry;

import java.awt.Image;

import javax.swing.JApplet;


/**
 * @author Ryan Ylitalo
 *
 */
public class ImageRegistry
{
	private static final ImageRegistry INSTANCE = new ImageRegistry();
	private JApplet applet;

	private ImageRegistry()
	{
	}

	public static ImageRegistry getInstance()
	{
		return INSTANCE;
	}

	public void init(JApplet applet)
	{
		this.applet = applet;
	}

	public Image loadImage(String path)
	{
		try
		{
			return applet.getImage(applet.getDocumentBase(), path);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
