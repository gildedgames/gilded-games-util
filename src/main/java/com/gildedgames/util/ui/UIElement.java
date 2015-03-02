package com.gildedgames.util.ui;

import com.gildedgames.util.ui.input.InputProvider;

public interface UIElement
{
	
	void init(UIElementHolder elementHolder, InputProvider input);

	boolean isEnabled();

	void setEnabled(boolean enabled);
	
}