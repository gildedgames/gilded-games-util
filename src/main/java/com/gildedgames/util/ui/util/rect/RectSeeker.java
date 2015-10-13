package com.gildedgames.util.ui.util.rect;

import com.gildedgames.util.ui.data.rect.ModDim2D;
import com.gildedgames.util.ui.data.rect.RectHolder;

public abstract class RectSeeker<S> implements RectHolder
{
	
	protected S seekFrom;

	private ModDim2D dim = new ModDim2D(this);

	public RectSeeker()
	{
		
	}
	
	public RectSeeker(S seekFrom)
	{
		this.seekFrom = seekFrom;
	}

	@Override
	public ModDim2D dim()
	{
		return this.dim;
	}
	
}