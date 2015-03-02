package com.gildedgames.util.ui;

import java.util.List;

public interface UIElementContainer
{

	void add(UIElement element);

	void remove(UIElement element);
	
	void clear();
	
	boolean contains(UIElement element);
	
	/**
	 * Any element that is an instance of the provided class will be removed.
	 * @param classToRemove
	 */
	void clear(Class<? extends UIElement> classToRemove);
	
	List<UIElement> getElements();
	
	List<UIView> queryAll(Object... input);

}