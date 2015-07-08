package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2D.ModifierType;
import com.gildedgames.util.ui.data.Dim2DHolder;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public class RectangleElement extends GuiFrame
{

	protected DrawingData startColor, endColor;
	
	protected Dim2DHolder holder;

	public RectangleElement(Dim2DHolder holder)
	{
		this(holder, false);
	}
	
	public RectangleElement(Dim2DHolder holder, boolean shouldRender)
	{
		this(holder, new DrawingData());
		
		this.setVisible(shouldRender);
	}
	
	public RectangleElement(Dim2DHolder holder, DrawingData data)
	{
		this(holder, data, null);
	}
	
	public RectangleElement(Dim2DHolder holder, DrawingData startColor, DrawingData endColor)
	{
		super(Dim2D.compile());

		this.holder = holder;
		
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
	public Dim2D getDim()
	{
		return this.holder.getDim();
	}
	
	@Override
	public void tick(InputProvider input, TickInfo tickInfo)
	{
		super.tick(input, tickInfo);
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
		
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
