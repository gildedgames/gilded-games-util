package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.common.UIFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public class RectangleElement extends UIFrame
{

	protected DrawingData startColor, endColor;
	
	public RectangleElement(Dim2D dim)
	{
		this(dim, false);
	}
	
	public RectangleElement(Dim2D dim, boolean shouldRender)
	{
		this(dim, new DrawingData());
		
		this.setVisible(shouldRender);
	}
	
	public RectangleElement(Dim2D dim, DrawingData data)
	{
		this(dim, data, null);
	}
	
	public RectangleElement(Dim2D dim, DrawingData startColor, DrawingData endColor)
	{
		super(dim);

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
		if (this.endColor == null)
		{
			graphics.drawRectangle(this.getDim(), this.startColor);
		}
		else
		{
			graphics.drawGradientRectangle(this.getDim(), this.startColor, this.endColor);
		}
		
	}

}
