package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.UIBasicAbstract;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public class UIRectangle extends UIBasicAbstract
{

	protected DrawingData startColor, endColor;
	
	public UIRectangle(Dimensions2D dim)
	{
		this(dim, false);
	}
	
	public UIRectangle(Dimensions2D dim, boolean shouldRender)
	{
		this(dim, new DrawingData());
		
		this.setVisible(shouldRender);
	}
	
	public UIRectangle(Dimensions2D dim, DrawingData data)
	{
		this(dim, data, null);
	}
	
	public UIRectangle(Dimensions2D dim, DrawingData startColor, DrawingData endColor)
	{
		super(null, dim);

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
			graphics.drawRectangle(this.getDimensions(), this.startColor);
		}
		else
		{
			graphics.drawGradientRectangle(this.getDimensions(), this.startColor, this.endColor);
		}
		
	}

}
