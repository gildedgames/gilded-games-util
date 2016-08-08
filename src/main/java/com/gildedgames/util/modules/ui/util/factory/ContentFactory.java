package com.gildedgames.util.modules.ui.util.factory;

import com.gildedgames.util.modules.ui.common.Ui;
import com.gildedgames.util.modules.ui.data.rect.Rect;
import com.google.common.collect.ImmutableMap;

import java.util.LinkedHashMap;

public interface ContentFactory<T extends Ui>
{

	LinkedHashMap<String, T> provideContent(ImmutableMap<String, Ui> currentContent, Rect contentArea);

}
