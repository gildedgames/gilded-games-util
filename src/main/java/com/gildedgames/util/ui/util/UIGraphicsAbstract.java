package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.UIGraphics;
import com.gildedgames.util.ui.UIPosition;

public abstract class UIGraphicsAbstract<GRAPHICS> implements UIGraphics<GRAPHICS>
{
	
	protected UIPosition position = new UIPosition(0, 0);
	
	protected final double width, height;
	
	protected double scale = 1.0D;
	
	protected boolean enabled = true, visible = true;
	
	public UIGraphicsAbstract(double width, double height)
	{
		this.width = width;
		this.height = height;
	}
	
	public UIGraphicsAbstract(UIPosition position, double width, double height)
	{
		this(width, height);
		
		this.position = position;
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

	@Override
	public boolean isEnabled()
	{
		return this.enabled;
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	@Override
	public boolean isGraphicsCompatible(Object graphics)
	{
		return false;
	}

	@Override
	public boolean isVisible()
	{
		return this.visible;
	}

	@Override
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

}
