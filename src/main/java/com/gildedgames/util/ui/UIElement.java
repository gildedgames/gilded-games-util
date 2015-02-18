package com.gildedgames.util.ui;

public interface UIElement
{
	
	void init(UIElementHolder elementHolder, UIDimensions screenDimensions);
	
	boolean isEnabled();
	
	void setEnabled(boolean enabled);
	
}
