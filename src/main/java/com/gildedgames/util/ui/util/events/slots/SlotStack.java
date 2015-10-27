package com.gildedgames.util.ui.util.events.slots;

import com.gildedgames.util.ui.common.GuiDecorator;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.input.InputProvider;

public class SlotStack extends GuiDecorator<GuiFrame>
{
	
	private Object data;

	public SlotStack(GuiFrame element, Object data)
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
