package com.gildedgames.util.ui.event;

import com.gildedgames.util.ui.UIContainer;
import com.gildedgames.util.ui.UIElement;
import com.gildedgames.util.ui.input.InputProvider;

public abstract class ElementEvent implements UIElement
{
	
	private boolean enabled = true;

	@Override
	public void onInit(UIContainer elementcontainer, InputProvider input)
	{
		
	}
	
	@Override
	public void onResolutionChange(UIContainer container, InputProvider input)
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
