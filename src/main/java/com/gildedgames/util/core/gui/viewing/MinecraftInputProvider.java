package com.gildedgames.util.core.gui.viewing;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.input.Mouse;

import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2DCollection;
import com.gildedgames.util.ui.data.Dim2DHolder;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.input.InputProvider;

public class MinecraftInputProvider implements InputProvider
{

	protected Minecraft mc;

	protected int screenWidth, screenHeight, scaleFactor, xOffset, yOffset;
	
	protected ScaledResolution resolution;

	public MinecraftInputProvider(Minecraft mc)
	{
		this.mc = mc;
		this.refreshResolution();
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
	public void refreshResolution()
	{
		this.resolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
	}

	@Override
	public int getScreenWidth()
	{
		this.refreshResolution();
		
		return this.resolution.getScaledWidth();
	}

	@Override
	public int getScreenHeight()
	{
		this.refreshResolution();
		
		return this.resolution.getScaledHeight();
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

		return this.getMouseX() >= dim.x() && this.getMouseY() >= dim.y() && this.getMouseX() < dim.x() + dim.width() && this.getMouseY() < dim.y() + dim.height();
	}

	@Override
	public int getScaleFactor()
	{
		return this.resolution.getScaleFactor();
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
		if (holder == null)
		{
			return false;
		}

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
