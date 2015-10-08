package com.gildedgames.util.ui.data;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.ui.data.Dim2D.Dim2DBuilder;
import com.gildedgames.util.ui.data.Dim2D.Dim2DModifier;

public class Dim2DSingle implements Dim2DHolder
{
	
	private Dim2D dim;
	
	private List<Dim2DListener> listeners = new ArrayList<Dim2DListener>();
	
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
		if (this.dim == null)
		{
			this.dim = Dim2D.flush();
		}
		
		return this.dim;
	}

	@Override
	public void setDim(Dim2D dim)
	{
		this.dim = dim;
		
		for (Dim2DListener listener : this.listeners)
		{
			listener.notifyChange();
		}
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
