/**
 * 
 */
package com.byteperceptions.require.registry;

import java.awt.Image;

import javax.swing.JApplet;

/**
 * @author JDev
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
		//	e.printStackTrace();
			return null;
		}
	}
}
