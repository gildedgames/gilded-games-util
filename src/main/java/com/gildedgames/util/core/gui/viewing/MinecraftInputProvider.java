package com.gildedgames.util.core.gui.viewing;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Mouse;

import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2DCollection;
import com.gildedgames.util.ui.data.Dim2DHolder;
import com.gildedgames.util.ui.input.InputProvider;

public class MinecraftInputProvider implements InputProvider
{
	
	protected Minecraft mc;
	
	protected int screenWidth, screenHeight, scaleFactor, xOffset, yOffset;
	
	public MinecraftInputProvider(Minecraft mc)
	{
		this.mc = mc;
	}
	
	public void setScreen(int screenWidth, int screenHeight)
	{
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	
	public void setScaleFactor(int scaleFactor)
	{
		this.scaleFactor = scaleFactor;
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
		return (Mouse.getX() * this.getScreenWidth() / this.mc.displayWidth) - this.xOffset;
	}

	@Override
	public int getMouseY()
	{
		return (this.getScreenHeight() - Mouse.getY() * this.getScreenHeight() / this.mc.displayHeight - 1) - this.yOffset;
	}

	@Override
	public boolean isHovered(Dim2D dim)
	{
		if (dim == null)
		{
			return false;
		}
		
		return this.getMouseX() >= dim.getX() && this.getMouseY() >= dim.getY() && this.getMouseX() < dim.getX() + dim.getWidth() && this.getMouseY() < dim.getY() + dim.getHeight();
	}

	@Override
	public int getScaleFactor()
	{
		return this.scaleFactor;
	}

	@Override
	public InputProvider copyWithMouseXOffset(int xOffset)
	{
		MinecraftInputProvider input = (MinecraftInputProvider) this.clone();
		
		input.xOffset = xOffset;
		
		return input;
	}
	
	@Override
	public InputProvider copyWithMouseYOffset(int yOffset)
	{
		MinecraftInputProvider input = (MinecraftInputProvider) this.clone();
		
		input.yOffset = yOffset;
		
		return input;
	}

	@Override
	public InputProvider clone()
	{
		MinecraftInputProvider input = new MinecraftInputProvider(this.mc);
		
		input.screenWidth = this.screenWidth;
		input.screenHeight = this.screenHeight;
		input.scaleFactor = this.scaleFactor;
		input.xOffset = this.xOffset;
		input.yOffset = this.yOffset;
		
		return input;
	}

	@Override
	public boolean isHovered(Dim2DHolder holder)
	{
		return this.isHovered(holder.getDim());
	}

	@Override
	public boolean isHovered(Dim2DCollection collection)
	{
		for (Dim2DHolder holder : collection.getDimHolders())
		{
			if (this.isHovered(holder))
			{
				return true;
			}
		}
		
		return false;
	}
	
}
