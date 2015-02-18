package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.UIGraphics;

public abstract class UIGraphicsAbstract<GRAPHICS> implements UIGraphics<GRAPHICS>
{

	protected boolean enabled = true, visible = true;

	protected UIDimensions dimensions;

	public UIGraphicsAbstract(UIDimensions dimensions)
	{
		this.dimensions = dimensions;
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
	public boolean isVisible()
	{
		return this.visible;
	}

	@Override
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	@Override
	public UIDimensions getDimensions()
	{
		return this.dimensions;
	}

	@Override
	public void setDimensions(UIDimensions dimensions)
	{
		this.dimensions = dimensions;
	}

}
