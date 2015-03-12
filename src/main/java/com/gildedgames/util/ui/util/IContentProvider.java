package com.gildedgames.util.ui.util;

import java.util.List;

import com.gildedgames.util.ui.UIElement;
import com.google.common.collect.ImmutableList;

public interface IContentProvider
{
	
	/**
	 * Recreated every refresh
	 * @param currentContent
	 * @return
	 */
	List<UIElement> provideContent(ImmutableList<UIElement> currentContent);
	
}