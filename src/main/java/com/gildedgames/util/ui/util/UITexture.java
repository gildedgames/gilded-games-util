package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.UIElementHolder;
import com.gildedgames.util.ui.UIFrame;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.graphics.Sprite;
import com.gildedgames.util.ui.input.InputProvider;

public class UITexture extends UIFrame
{

	protected final Sprite sprite;

	protected DrawingData data;
	
	public UITexture(Sprite sprite, Dimensions2D dim)
	{
		this(dim, sprite, new DrawingData());
	}
	
	public UITexture(Dimensions2D dim, Sprite sprite, DrawingData data)
	{
		super(dim);

		this.sprite = sprite;
		this.data = data;
	}
	
	public DrawingData getDrawingData()
	{
		return this.data;
	}
	
	public void setDrawingData(DrawingData data)
	{
		this.data = data;
	}

	@Override
	public void draw(IGraphics graphics, InputProvider input)
	{
		graphics.drawSprite(this.sprite, this.getFocusArea(), this.data);
	}

	@Override
	public void init(UIElementHolder holder, InputProvider input)
	{
		super.init(holder, input);
	}

	@Override
	public void onFocused(InputProvider input)
	{
		super.onFocused(input);
	}

}
