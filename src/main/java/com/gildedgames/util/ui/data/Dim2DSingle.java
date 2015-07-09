package com.gildedgames.util.ui.data;

import com.gildedgames.util.ui.data.Dim2D.Dim2DBuilder;
import com.gildedgames.util.ui.data.Dim2D.Dim2DModifier;

public class Dim2DSingle implements Dim2DHolder
{
	
	private Dim2D dim = Dim2D.flush();
	
	public Dim2DSingle()
	{
		
	}
	
	public Dim2DSingle(Dim2D dim)
	{
		this.dim = dim;
	}

	@Override
	public Dim2D getDim()
	{
		return this.dim;
	}

	@Override
	public void setDim(Dim2D dim)
	{
		this.dim = dim;
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
