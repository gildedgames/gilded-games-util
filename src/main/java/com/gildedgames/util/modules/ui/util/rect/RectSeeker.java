package com.gildedgames.util.modules.ui.util.rect;

import com.gildedgames.util.modules.ui.data.rect.ModDim2D;
import com.gildedgames.util.modules.ui.data.rect.RectHolder;

public abstract class RectSeeker<S> implements RectHolder
{

	protected S seekFrom;

	private ModDim2D dim;

	public RectSeeker()
	{
		this.dim = new ModDim2D();
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

	@Override
	public void updateState()
	{

	}

}
