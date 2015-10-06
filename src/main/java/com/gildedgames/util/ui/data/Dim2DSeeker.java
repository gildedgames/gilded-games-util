package com.gildedgames.util.ui.data;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.ui.data.Dim2D.Dim2DBuilder;
import com.gildedgames.util.ui.data.Dim2D.Dim2DModifier;

public abstract class Dim2DSeeker<S> implements Dim2DHolder
{
	
	protected S seekFrom;
	
	private List<Dim2DListener> listeners = new ArrayList<Dim2DListener>();

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
	
	@Override
	public List<Dim2DListener> dimListeners()
	{
		return this.listeners;
	}

	@Override
	public void addDimListener(Dim2DListener listener)
	{
		this.listeners.add(listener);
	}

	@Override
	public void removeDimListener(Dim2DListener listener)
	{
		this.listeners.remove(listener);
	}
	
}