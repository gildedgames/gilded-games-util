package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.data.rect.Dim2D;
import com.gildedgames.util.ui.data.rect.ModDim2D;
import com.gildedgames.util.ui.data.rect.RectHolder;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public class RectangleElement extends GuiFrame
{

	protected DrawingData startColor, endColor;
	
	protected RectHolder holder;

	public RectangleElement(RectHolder holder)
	{
		this(holder, false);
	}
	
	public RectangleElement(RectHolder holder, boolean shouldRender)
	{
		this(holder, new DrawingData());
		
		this.setVisible(shouldRender);
	}
	
	public RectangleElement(RectHolder holder, DrawingData data)
	{
		this(holder, data, null);
	}
	
	public RectangleElement(RectHolder holder, DrawingData startColor, DrawingData endColor)
	{
		super(Dim2D.flush());

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
	public ModDim2D dim()
	{
		return this.holder.dim();
	}
	
	@Override
	public void tick(TickInfo tickInfo, InputProvider input)
	{
		super.tick(tickInfo, input);
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
