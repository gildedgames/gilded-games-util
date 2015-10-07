package com.gildedgames.util.ui.data;


public abstract class Dim2DGetter<S> extends Dim2DSeeker<S>
{
	
	private Dim2D assembledDim;
	
	private boolean recursion;
	
	public Dim2DGetter()
	{
		
	}
	
	public Dim2DGetter(S seekFrom)
	{
		this.seekFrom = seekFrom;
	}
	
	@Override
	public final void setDim(Dim2D dim)
	{
		
	}
	
	@Override
	public final Dim2D getDim()
	{
		if ((this.assembledDim == null || this.dimHasChanged()) && !this.recursion)
		{
			this.assembledDim = this.assembleDim();
			
			this.recursion = true;
			
			for (Dim2DListener listener : this.dimListeners())
			{
				if (listener != null)
				{
					listener.notifyChange();
				}
			}
			
			this.recursion = false;
		}
		
		return this.assembledDim;
	}
	
	/**
	 * Will only be called when dimHasChanged() returns true.
	 * @return
	 */
	public abstract Dim2D assembleDim();
	
	/**
	 * Calculations should be kept small here to check if the Dim2D object that will be assembled is different.
	 * @return
	 */
	public abstract boolean dimHasChanged();

}
