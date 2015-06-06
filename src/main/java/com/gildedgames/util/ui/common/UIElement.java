package com.gildedgames.util.ui.common;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.data.UIElementContainer;
import com.gildedgames.util.ui.input.InputProvider;

public interface UIElement extends NBT
{
	
	/**
	 * Will not be called if this element is disabled with isEnabled()
	 * @param input
	 */
	void tick(InputProvider input, TickInfo tickInfo);
	
	void onInit(UIElementContainer container, InputProvider input);
	
	void onResolutionChange(UIElementContainer container, InputProvider input);
	
	boolean isEnabled();
	
	void setEnabled(boolean enabled);
	
}