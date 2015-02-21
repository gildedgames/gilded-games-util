package com.gildedgames.util.ui.data;

import javax.vecmath.Color4f;

public class DrawingData
{

	protected final float scale;
	
	protected final Color4f color;
	
	public DrawingData()
	{
		this(1.0F);
	}
	
	public DrawingData(float scale)
	{
		this(scale, new Color4f(1.0F, 1.0F, 1.0F, 1.0F));
	}
	
	public DrawingData(float scale, Color4f color)
	{
		this.scale = scale;
		this.color = color;
	}
	
	public int getColorHex()
	{
		return Integer.valueOf(String.format("%02X%02X%02X%02X", this.getRed(), this.getGreen(), this.getBlue(), this.getAlpha()));
	}
	
	public Color4f getColor()
	{
		return this.color;
	}
	
	public float getAlpha()
	{
		return this.color.w;
	}
	
	public float getRed()
	{
		return this.color.x;
	}
	
	public float getGreen()
	{
		return this.color.y;
	}
	
	public float getBlue()
	{
		return this.color.z;
	}
	
	public float getScale()
	{
		return this.scale;
	}
	
}
