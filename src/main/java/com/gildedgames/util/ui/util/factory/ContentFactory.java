package com.gildedgames.util.ui.util.factory;

import java.util.List;

import com.gildedgames.util.ui.UIElement;
import com.google.common.collect.ImmutableList;

public interface ContentFactory
{

	List<UIElement> provideContent(ImmutableList<UIElement> currentContent);
	
}