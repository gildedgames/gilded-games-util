package com.gildedgames.util.ui.util.basic;

import com.gildedgames.util.ui.UIContainer;
import com.gildedgames.util.ui.UIBasic;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.graphics.Sprite;
import com.gildedgames.util.ui.input.InputProvider;

public class UITexture extends UIBasic
{

	protected final Sprite sprite;

	protected DrawingData data;
	
	public UITexture(Sprite sprite, Dimensions2D dim)
	{
		this(dim, sprite, new DrawingData());
	}
	
	public UITexture(Dimensions2D dim, Sprite sprite, DrawingData data)
	{
		super(null, dim);

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
	public void draw(Graphics2D graphics, InputProvider input)
	{
		graphics.drawSprite(this.sprite, this.getDimensions(), this.data);
	}

}
