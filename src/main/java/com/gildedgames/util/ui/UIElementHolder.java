package com.gildedgames.util.ui;


public interface UIElementHolder
{

	void add(UIElement element);
	
	void remove(UIElement element);
	
	UIFrame getWrapper();
	
}
