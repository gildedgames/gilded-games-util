package com.gildedgames.util.ui;

import java.util.List;

import com.gildedgames.util.ui.data.Dimensions2D;

public interface UIElementHolder
{

	void add(UIElement element);

	void remove(UIElement element);
	
	void clear();
	
	List<UIElement> getElements();

}
