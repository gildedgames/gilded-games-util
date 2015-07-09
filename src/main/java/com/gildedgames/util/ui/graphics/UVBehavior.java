package com.gildedgames.util.ui.graphics;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2DHolder;
import com.gildedgames.util.ui.graphics.Sprite.UV;

public interface UVBehavior
{
	
	boolean shouldRecalculateUVs(Sprite sprite, Dim2DHolder areaToDraw);
	
	void recalculateUVs(Sprite sprite, Dim2DHolder areaToDraw);

	/**
	 * All Dim2D objects within these pairs must be relative to the provided Dim2DHolder.
	 * It is recommended to add the holder as a positional modifier to all pairs.
	 * @param areaToDraw
	 * @return
	 */
	List<UVDimPair> getDrawnUVsFor(Sprite sprite, Dim2DHolder areaToDraw);
	
	public static class UVDimPair
	{
		
		private final UV uv;
		
		private final Dim2D dim;
		
		public UVDimPair(UV uv, Dim2D dim)
		{
			this.uv = uv;
			this.dim = dim;
		}
		
		public UV getUV()
		{
			return this.uv;
		}
		
		public Dim2D getDim()
		{
			return this.dim;
		}
		
		public static List<Dim2D> toDims(List<UVDimPair> pairs)
		{
			List<Dim2D> dims = new ArrayList<Dim2D>();
			
			for (UVDimPair pair : pairs)
			{
				dims.add(pair.getDim());
			}
			
			return dims;
		}
		
		public static List<Dim2DHolder> toDimHolders(List<UVDimPair> pairs)
		{
			List<Dim2DHolder> dims = new ArrayList<Dim2DHolder>();
			
			for (UVDimPair pair : pairs)
			{
				dims.add(pair.getDim().toHolder());
			}
			
			return dims;
		}
		
	}
	
}
