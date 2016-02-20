package com.gildedgames.util.modules.ui.graphics;

import java.util.List;

import com.gildedgames.util.modules.ui.data.rect.Dim2D;
import com.gildedgames.util.modules.ui.data.rect.RectHolder;
import com.google.common.collect.Lists;

public class DefaultUVBehavior implements UVBehavior
{

	@Override
	public List<UVDimPair> getDrawnUVsFor(Sprite sprite, RectHolder areaToDraw)
	{
		return Lists.newArrayList(new UVDimPair(sprite.getUV(), Dim2D.flush()));
	}

	@Override
	public boolean shouldRecalculateUVs(Sprite sprite, RectHolder areaToDraw)
	{
		return false;
	}

	@Override
	public void recalculateUVs(Sprite sprite, RectHolder areaToDraw)
	{
		
	}

}
