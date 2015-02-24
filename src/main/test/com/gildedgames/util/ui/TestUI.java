package com.gildedgames.util.ui;

import javax.vecmath.Color4f;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.gui.Resource;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.data.Position2D;
import com.gildedgames.util.ui.graphics.Sprite;
import com.gildedgames.util.ui.util.UITexture;

public class TestUI extends UIFrame
{

	public TestUI()
	{
		super(new Dimensions2D(0, 0), new Dimensions2D(0, 0));
	}

	@Override
	public void init(UIElementHolder elementHolder, Dimensions2D screenDimensions)
	{
		super.init(elementHolder, screenDimensions);
		
		final Resource hopperTexture = new Resource(UtilCore.MOD_ID, "textures/gui/universe_hopper/base.png");
		final Sprite hopperSprite = new Sprite(hopperTexture, 0, 0, 256, 256, 256, 256);
		
		Dimensions2D dim = new Dimensions2D(256, 256);
		
		UITexture hopperResized = new UITexture(hopperSprite, dim.copyWith(new Position2D(60, 60)));

		elementHolder.add(new UITexture(hopperSprite, dim));
		elementHolder.add(hopperResized);
		
		hopperResized.setDrawingData(new DrawingData(1.2F, new Color4f(0.0F, 0.0F, 1.0F, 0.4F)));
	}

	@Override
	public void onFocused()
	{
		
	}
	
}
