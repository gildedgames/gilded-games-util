package com.gildedgames.util.ui.util.factory;

import java.util.LinkedHashMap;

import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.data.Dim2D;
import com.google.common.collect.ImmutableMap;

public interface ContentFactory
{

	LinkedHashMap<String, Ui> provideContent(ImmutableMap<String, Ui> currentContent, Dim2D contentArea);
	
}