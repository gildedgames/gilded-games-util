package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.DimensionsHolder;

public class OriginHolder implements DimensionsHolder
{

	private Dimensions2D origin = new Dimensions2D();
	
	public OriginHolder(Dimensions2D origin)
	{
		this.origin = origin;
	}

	@Override
	public Dimensions2D getDimensions()
	{
		return this.origin;
	}

}
