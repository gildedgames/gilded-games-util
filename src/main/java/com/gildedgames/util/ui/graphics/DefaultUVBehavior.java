package com.gildedgames.util.ui.graphics;

import java.util.List;

import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2DHolder;
import com.google.common.collect.Lists;

public class DefaultUVBehavior implements UVBehavior
{

	@Override
	public List<UVDimPair> getDrawnUVsFor(Sprite sprite, Dim2DHolder areaToDraw)
	{
		return Lists.newArrayList(new UVDimPair(sprite.getUV(), Dim2D.flush()));
	}

	@Override
	public boolean shouldRecalculateUVs(Sprite sprite, Dim2DHolder areaToDraw)
	{
		return false;
	}

	@Override
	public void recalculateUVs(Sprite sprite, Dim2DHolder areaToDraw)
	{
		
	}

}
