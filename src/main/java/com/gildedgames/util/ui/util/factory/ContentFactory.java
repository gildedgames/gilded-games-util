package com.gildedgames.util.ui.util.factory;

import java.util.LinkedHashMap;

import com.gildedgames.util.ui.common.UIElement;
import com.gildedgames.util.ui.data.ImmutableDim2D;
import com.google.common.collect.ImmutableMap;

public interface ContentFactory
{

	LinkedHashMap<String, UIElement> provideContent(ImmutableMap<String, UIElement> currentContent, ImmutableDim2D contentArea);
	
}