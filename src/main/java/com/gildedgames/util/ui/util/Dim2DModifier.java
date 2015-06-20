package com.gildedgames.util.ui.util;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2DHolder;

public class Dim2DModifier implements Dim2DHolder
{
	
	private List<Dim2D> dims = new ArrayList<Dim2D>();
	
	private List<Dim2DHolder> holders = new ArrayList<Dim2DHolder>();
	
	public Dim2DModifier()
	{
		
	}
	
	public Dim2DModifier addDim(Dim2DHolder holder)
	{
		this.dims.add(holder.getDim());
		
		return this;
	}
	
	public Dim2DModifier removeDim(Dim2DHolder holder)
	{
		this.dims.remove(holder.getDim());
		
		return this;
	}
	
	public Dim2DModifier addDim(Dim2D dim)
	{
		this.dims.add(dim);
		
		return this;
	}
	
	public Dim2DModifier removeDim(Dim2D dim)
	{
		this.dims.remove(dim);
		
		return this;
	}
	
	public Dim2DModifier addHolder(Dim2DHolder holder)
	{
		this.holders.add(holder);
		
		return this;
	}
	
	public Dim2DModifier removeHolder(Dim2DHolder holder)
	{
		this.holders.remove(holder);
		
		return this;
	}

	@Override
	public Dim2D getDim()
	{
		Dim2D combinedResult = new Dim2D();
		
		for (Dim2D dim : this.dims)
		{
			if (dim != null)
			{
				//combinedResult.set(Dim2D.combine(combinedResult, dim));
				
				combinedResult.addPos(dim.getPos());
			}
		}
		
		for (Dim2DHolder holder : this.holders)
		{
			if (holder != null && holder.getDim() != null)
			{
				combinedResult.set(Dim2D.combine(combinedResult, holder.getDim()));
			}
		}
		
		return combinedResult;
	}
	
	@Override
	public String toString()
	{
		return this.getDim().toString();
	}

}
