package com.gildedgames.util.core.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.input.Mouse;

public class GraphicsMinecraft
{
	
	private Minecraft minecraft;
	
	private final ScaledResolution resolution;
	
	public GraphicsMinecraft(Minecraft minecraft)
	{
		this.minecraft = minecraft;
		this.resolution = new ScaledResolution(this.minecraft, this.minecraft.displayWidth, this.minecraft.displayHeight);
	}
	
	public int getMouseX()
	{
		return Mouse.getX() * resolution.getScaledWidth() / this.minecraft.displayWidth;
	}
	
	public int getMouseY()
	{
		return Mouse.getY() * resolution.getScaledHeight() / this.minecraft.displayHeight - 1;
	}
	
	public Minecraft getMinecraft()
	{
		return this.minecraft;
	}
	
}
