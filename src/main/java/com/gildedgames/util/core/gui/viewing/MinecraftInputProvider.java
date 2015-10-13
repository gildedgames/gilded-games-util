package com.gildedgames.util.core.gui.viewing;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.input.Mouse;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.data.rect.Rect;
import com.gildedgames.util.ui.data.rect.RectHolder;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.rect.RectCollection;

public class MinecraftInputProvider implements InputProvider
{

	protected Minecraft mc;

	protected float screenWidth, screenHeight, scaleFactor, xOffset, yOffset;
	
	protected ScaledResolution resolution;

	public MinecraftInputProvider(Minecraft mc)
	{
		this.mc = mc;
		this.refreshResolution();
	}

	public void setScreen(float screenWidth, float screenHeight)
	{
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}

	public void setScaleFactor(float scaleFactor)
	{
		this.scaleFactor = scaleFactor;
	}
	
	@Override
	public void refreshResolution()
	{
		this.resolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
	}

	@Override
	public float getScreenWidth()
	{
		this.refreshResolution();
		
		return this.resolution.getScaledWidth();
	}

	@Override
	public float getScreenHeight()
	{
		this.refreshResolution();
		
		return this.resolution.getScaledHeight();
	}

	@Override
	public float getMouseX()
	{
		return (Mouse.getX() * this.getScreenWidth() / this.mc.displayWidth) - this.xOffset;
	}

	@Override
	public float getMouseY()
	{
		return (this.getScreenHeight() - Mouse.getY() * this.getScreenHeight() / this.mc.displayHeight - 1) - this.yOffset;
	}

	@Override
	public boolean isHovered(Rect dim)
	{
		if (dim == null)
		{
			return false;
		}

		return this.getMouseX() >= dim.x() && this.getMouseY() >= dim.y() && this.getMouseX() < dim.x() + dim.width() && this.getMouseY() < dim.y() + dim.height();
	}

	@Override
	public float getScaleFactor()
	{
		return this.resolution.getScaleFactor();
	}

	@Override
	public InputProvider copyWithMouseXOffset(float xOffset)
	{
		MinecraftInputProvider input = (MinecraftInputProvider) this.clone();

		input.xOffset = xOffset;

		return input;
	}

	@Override
	public InputProvider copyWithMouseYOffset(float yOffset)
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
	public boolean isHovered(RectHolder holder)
	{
		if (holder == null)
		{
			return false;
		}

		return this.isHovered(holder.dim());
	}
	
}
