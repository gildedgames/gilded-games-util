package com.gildedgames.util.modules.ui.util;

import com.gildedgames.util.modules.ui.common.GuiFrame;
import com.gildedgames.util.modules.ui.data.DrawingData;
import com.gildedgames.util.modules.ui.data.rect.Rect;
import com.gildedgames.util.modules.ui.graphics.Graphics2D;
import com.gildedgames.util.modules.ui.input.InputProvider;

public class RectangleElement extends GuiFrame
{

	protected DrawingData startColor, endColor;

	public RectangleElement(Rect rect, DrawingData data)
	{
		this(rect, data, null);
	}

	public RectangleElement(Rect rect, DrawingData startColor, DrawingData endColor)
	{
		super(rect);

		this.startColor = startColor;
		this.endColor = endColor;
	}

	public DrawingData getDrawingData()
	{
		return this.startColor;
	}

	public void setDrawingData(DrawingData data)
	{
		this.startColor = data;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);

		if (this.endColor == null)
		{
			graphics.drawRectangle(this.dim(), this.startColor);
		}
		else
		{
			graphics.drawGradientRectangle(this.dim(), this.startColor, this.endColor);
		}
	}

}
