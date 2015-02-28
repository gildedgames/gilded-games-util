package com.gildedgames.util.core.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.input.Mouse;

import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.input.InputProvider;

public class InputProviderMC implements InputProvider
{
	
	protected Minecraft mc;
	
	protected ScaledResolution resolution;
	
	public InputProviderMC(Minecraft mc)
	{
		this.mc = mc;
	}
	
	private int getScaledWidth()
	{
		this.resolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		
		return this.resolution.getScaledWidth();
	}
	
	private int getScaledHeight()
	{
		this.resolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		
		return this.resolution.getScaledHeight();
	}

	@Override
	public int getMouseX()
	{
		return Mouse.getX() * this.getScaledWidth() / this.mc.displayWidth;
	}

	@Override
	public int getMouseY()
	{
		return this.getScaledHeight() - Mouse.getY() * this.getScaledHeight() / this.mc.displayHeight - 1;
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
