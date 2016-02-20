package com.gildedgames.util.modules.ui.graphics;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.modules.ui.data.rect.Rect;
import com.gildedgames.util.modules.ui.data.rect.RectHolder;
import com.gildedgames.util.modules.ui.graphics.Sprite.UV;
import com.gildedgames.util.modules.ui.util.rect.RectCollection;

public interface UVBehavior
{
	
	boolean shouldRecalculateUVs(Sprite sprite, RectHolder areaToDraw);
	
	void recalculateUVs(Sprite sprite, RectHolder areaToDraw);

	/**
	 * All Dim2D objects within these pairs must be relative to the provided Dim2DHolder.
	 * It is recommended to add the holder as a positional modifier to all pairs.
	 * @param areaToDraw
	 * @return
	 */
	List<UVDimPair> getDrawnUVsFor(Sprite sprite, RectHolder areaToDraw);
	
	class UVDimPair
	{
		
		private final UV uv;
		
		private final Rect dim;
		
		public UVDimPair(UV uv, Rect dim)
		{
			this.uv = uv;
			this.dim = dim;
		}
		
		public UV getUV()
		{
			return this.uv;
		}
		
		public Rect getRect()
		{
			return this.dim;
		}
		
		public static List<Rect> toDims(List<UVDimPair> pairs)
		{
			List<Rect> dims = new ArrayList<>();
			
			for (UVDimPair pair : pairs)
			{
				dims.add(pair.getRect());
			}
			
			return dims;
		}
		
		public static List<RectHolder> toDimHolders(List<UVDimPair> pairs)
		{
			List<RectHolder> dims = new ArrayList<>();
			
			for (UVDimPair pair : pairs)
			{
				dims.add(RectCollection.flush(pair.getRect()));
			}
			
			return dims;
		}
		
	}
	
}
