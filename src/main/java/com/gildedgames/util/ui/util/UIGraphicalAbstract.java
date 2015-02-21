package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.UIGraphical;
import com.gildedgames.util.ui.data.Dimensions2D;

public abstract class UIGraphicalAbstract implements UIGraphical
{

	protected boolean enabled = true, visible = true;

	protected Dimensions2D dimensions;

	public UIGraphicalAbstract(Dimensions2D dimensions)
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
	public Dimensions2D getDimensions()
	{
		return this.dimensions;
	}

	@Override
	public void setDimensions(Dimensions2D dimensions)
	{
		this.dimensions = dimensions;
	}

}
