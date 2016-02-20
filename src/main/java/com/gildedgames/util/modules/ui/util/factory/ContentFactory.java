package com.gildedgames.util.modules.ui.util.factory;

import java.util.LinkedHashMap;

import com.gildedgames.util.modules.ui.common.Ui;
import com.gildedgames.util.modules.ui.data.rect.Rect;
import com.google.common.collect.ImmutableMap;

public interface ContentFactory<T extends Ui>
{

	LinkedHashMap<String, T> provideContent(ImmutableMap<String, Ui> currentContent, Rect contentArea);

}
