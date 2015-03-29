package com.gildedgames.util.ui;

import com.gildedgames.util.ui.input.InputProvider;

public interface UIElement
{
	
	void onInit(UIContainer container, InputProvider input);
	
	void onResolutionChange(UIContainer container, InputProvider input);
	
	boolean isEnabled();
	
	void setEnabled(boolean enabled);
	
}