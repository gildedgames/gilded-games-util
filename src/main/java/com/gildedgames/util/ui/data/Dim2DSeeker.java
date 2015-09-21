package com.gildedgames.util.ui.data;

import com.gildedgames.util.ui.data.Dim2D.Dim2DBuilder;
import com.gildedgames.util.ui.data.Dim2D.Dim2DModifier;

public abstract class Dim2DSeeker<S> implements Dim2DHolder
{
	
	protected S seekFrom;

	public Dim2DSeeker()
	{
		
	}
	
	public Dim2DSeeker(S seekFrom)
	{
		this.seekFrom = seekFrom;
	}

	@Override
	public Dim2DModifier modDim()
	{
		return new Dim2DModifier(this);
	}

	@Override
	public Dim2DBuilder copyDim()
	{
		return Dim2D.build(this);
	}
	
}