package com.gildedgames.util.core.gui;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Mouse;

import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.input.InputProvider;

public class InputProviderMC implements InputProvider
{
	
	protected Minecraft mc;
	
	protected int screenWidth, screenHeight;
	
	public InputProviderMC(Minecraft mc)
	{
		this.mc = mc;
	}
	
	public void setScreen(int screenWidth, int screenHeight)
	{
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	
	@Override
	public int getScreenWidth()
	{
		return this.screenWidth;
	}
	
	@Override
	public int getScreenHeight()
	{
		return this.screenHeight;
	}

	@Override
	public int getMouseX()
	{
		return Mouse.getX() * this.getScreenWidth() / this.mc.displayWidth;
	}

	@Override
	public int getMouseY()
	{
		return this.getScreenHeight() - Mouse.getY() * this.getScreenHeight() / this.mc.displayHeight - 1;
	}

	@Override
	public boolean isHovered(Dimensions2D dim)
	{
		if (dim == null)
		{
			return false;
		}
		
		return this.getMouseX() >= dim.getX() && this.getMouseY() >= dim.getY() && this.getMouseX() < dim.getX() + dim.getWidth() && this.getMouseY() < dim.getY() + dim.getHeight();
	}

}
