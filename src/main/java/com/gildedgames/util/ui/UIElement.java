package com.gildedgames.util.ui;

import com.gildedgames.util.ui.data.Dimensions2D;

public interface UIElement
{
	
	void init(UIElementHolder elementHolder, Dimensions2D screen);

	boolean isEnabled();

	void setEnabled(boolean enabled);
	
}