package com.gildedgames.util.ui.util.events;

import com.gildedgames.util.ui.common.GuiDecorator;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.input.InputProvider;

public class DraggedState extends GuiDecorator<GuiFrame>
{
	
	private Object data;

	public DraggedState(GuiFrame element, Object data)
	{
		super(element);
		
		this.data = data;
	}
	
	public Object getData()
	{
		return this.data;
	}

	@Override
	protected void preInitContent(InputProvider input)
	{
		
	}

	@Override
	protected void postInitContent(InputProvider input)
	{
		
	}

}
