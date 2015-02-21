package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.UIElementHolder;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.graphics.Sprite;

public class UITexture extends UIGraphicalAbstract
{

	protected final Sprite sprite;

	protected DrawingData data;
	
	public UITexture(Sprite sprite, Dimensions2D dim)
	{
		this(sprite, dim, new DrawingData());
	}
	
	public UITexture(Sprite sprite, Dimensions2D dim, DrawingData data)
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
	public void draw(IGraphics graphics)
	{
		graphics.drawSprite(this.sprite, this.dimensions, this.data);
	}

	@Override
	public void init(UIElementHolder elementHolder, Dimensions2D screenDimensions)
	{
		
	}

}
