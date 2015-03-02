package com.gildedgames.util.ui.event;

import com.gildedgames.util.ui.UIElement;
import com.gildedgames.util.ui.UIElementHolder;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.input.InputProvider;

public abstract class UIEvent implements UIElement
{
	
	private boolean enabled = true;

	@Override
	public void init(UIElementHolder elementHolder, InputProvider input)
	{
		
	}

	@Override
	public boolean isEnabled()
	{
		return this.enabled;
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

}
