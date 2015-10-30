package com.gildedgames.util.ui.util.events.slots;

import com.gildedgames.util.ui.common.GuiDecorator;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.input.InputProvider;

public class SlotStack<T> extends GuiDecorator<GuiFrame>
{
	
	private T data;

	public SlotStack(GuiFrame element, T data)
	{
		super(element);
		
		this.data = data;
	}
	
	public T getData()
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
