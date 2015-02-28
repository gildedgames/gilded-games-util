package com.gildedgames.util.ui.event;

import com.gildedgames.util.ui.UIElement;
import com.gildedgames.util.ui.UIElementHolder;
import com.gildedgames.util.ui.data.Dimensions2D;

public abstract class UIEvent implements UIElement
{
	
	private boolean enabled = true;

	@Override
	public void init(UIElementHolder elementHolder, Dimensions2D screen)
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
