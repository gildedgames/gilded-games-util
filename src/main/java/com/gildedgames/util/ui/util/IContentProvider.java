package com.gildedgames.util.ui.util;

import java.util.List;

import com.gildedgames.util.ui.UIElement;
import com.google.common.collect.ImmutableList;

public interface IContentProvider
{

	List<UIElement> provideContent(ImmutableList<UIElement> currentContent);
	
}