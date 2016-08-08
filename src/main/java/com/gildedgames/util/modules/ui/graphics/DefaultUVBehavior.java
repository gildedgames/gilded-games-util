package com.gildedgames.util.modules.ui.graphics;

import com.gildedgames.util.modules.ui.data.rect.Dim2D;
import com.gildedgames.util.modules.ui.data.rect.RectHolder;
import com.google.common.collect.Lists;

import java.util.List;

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
