package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.DimensionsHolder;

public class OriginDecorator implements DimensionsHolder
{
	
	private Dimensions2D dimensions = new Dimensions2D();
	
	public OriginDecorator(Dimensions2D previousOriginHolder, Dimensions2D decorateWith)
	{
		this(previousOriginHolder.getOrigin(), decorateWith);
	}
	
	public OriginDecorator(DimensionsHolder previousOrigin, Dimensions2D decorateWith)
	{
		this.dimensions = decorateWith;
		this.dimensions.setOrigin(previousOrigin);
	}

	@Override
	public Dimensions2D getDimensions()
	{
		return this.dimensions;
	}

}
