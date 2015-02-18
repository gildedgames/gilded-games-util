package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.UIDimensions;
import com.gildedgames.util.ui.UIPosition;

public class UIDimensionsGeneric implements UIDimensions
{

	protected UIPosition position = new UIPosition(0, 0);
	
	protected final double width, height;
	
	protected double scale = 1.0F;
	
	public UIDimensionsGeneric(double width, double height)
	{
		this.width = width;
		this.height = height;
	}
	
	public UIDimensionsGeneric(UIPosition position, double width, double height)
	{
		this(width, height);
		
		this.position = position;
	}
	
	public UIDimensionsGeneric(UIPosition position, double width, double height, double scale)
	{
		this(position, width, height);
		
		this.scale = scale;
	}

	@Override
	public UIPosition getPosition()
	{
		return this.position;
	}

	@Override
	public void setPosition(UIPosition position)
	{
		this.position = position;
	}

	@Override
	public double getWidth()
	{
		return this.width;
	}

	@Override
	public double getHeight()
	{
		return this.height;
	}

	@Override
	public double getScale()
	{
		return this.scale;
	}

	@Override
	public void setScale(double scale)
	{
		this.scale = scale;
	}
	
}
