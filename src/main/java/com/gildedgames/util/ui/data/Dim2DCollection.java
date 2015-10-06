package com.gildedgames.util.ui.data;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.ui.data.Dim2D.Dim2DBuilder;
import com.google.common.collect.ImmutableList;

public class Dim2DCollection implements Dim2DHolder
{

	private List<Dim2DHolder> holders = new ArrayList<Dim2DHolder>();
	
	private List<Dim2DSeekable> seekables = new ArrayList<Dim2DSeekable>();
	
	private List<Dim2DListener> listeners = new ArrayList<Dim2DListener>();
	
	public Dim2DCollection()
	{
		
	}
	
	public Dim2DCollection addDim(Dim2D dim)
	{
		this.holders.add(new Dim2DSingle(dim));
		
		return this;
	}
	
	public Dim2DCollection removeDim(Dim2D dim)
	{
		this.holders.remove(new Dim2DSingle(dim));
		
		return this;
	}
	
	public Dim2DCollection addHolder(Dim2DHolder holder)
	{
		this.holders.add(holder);
		
		return this;
	}
	
	public Dim2DCollection removeHolder(Dim2DHolder holder)
	{
		this.holders.remove(holder);
		
		return this;
	}
	
	public Dim2DCollection addSeekable(Dim2DSeekable seekable)
	{
		this.seekables.add(seekable);
		
		return this;
	}
	
	public Dim2DCollection removeSeekable(Dim2DSeekable seekable)
	{
		this.seekables.remove(seekable);
		
		return this;
	}

	public ImmutableList<Dim2D> getDims()
	{
		List<Dim2D> results = new ArrayList<Dim2D>();
		
		for (Dim2DSeekable seekable : this.seekables)
		{
			for(Dim2DSeeker seeker : seekable.getDimSeekers())
			{
				results.add(seeker.getDim());
			}
		}
		
		for (Dim2DHolder holder : this.holders)
		{
			results.add(holder.getDim());
		}

		return ImmutableList.copyOf(results);
	}

	public ImmutableList<Dim2DHolder> getDimHolders()
	{
		List<Dim2DHolder> results = new ArrayList<Dim2DHolder>();
		
		for (Dim2DSeekable seekable : this.seekables)
		{
			for(Dim2DSeeker seeker : seekable.getDimSeekers())
			{
				results.add(seeker);
			}
		}
		
		results.addAll(this.holders);

		return ImmutableList.copyOf(results);
	}

	@Override
	public Dim2D getDim()
	{
		Dim2DSingle resultHolder = new Dim2DSingle();
		
		for (Dim2DHolder holder : this.holders)
		{
			if (holder != null && holder.getDim() != null)
			{
				resultHolder.setDim(Dim2D.combine(resultHolder.getDim(), holder.getDim()));
			}
		}
		
		for (Dim2DSeekable seekable : this.seekables)
		{
			for(Dim2DSeeker seeker : seekable.getDimSeekers())
			{
				if (seeker != null && seeker.getDim() != null)
				{
					resultHolder.setDim(Dim2D.combine(resultHolder.getDim(), seeker.getDim()));
				}
			}
		}
		
		return resultHolder.getDim();
	}
	
	@Override
	public String toString()
	{
		return this.getDim().toString();
	}

	@Override
	public void setDim(Dim2D dim)
	{
		
	}

	@Override
	public Dim2D.Dim2DModifier modDim()
	{
		return null;
	}

	@Override
	public Dim2DBuilder copyDim()
	{
		return null;
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
