package com.gildedgames.util.ui.util.factory;

import java.util.LinkedHashMap;

import com.gildedgames.util.core.gui.util.UIFactory;
import com.gildedgames.util.ui.common.UIElement;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Pos2D;
import com.google.common.collect.ImmutableMap;

public class TestButtonFactory implements ContentFactory
{

	@Override
	public LinkedHashMap<String, UIElement> provideContent(ImmutableMap<String, UIElement> currentContent, Dim2D contentArea)
	{
		LinkedHashMap<String, UIElement> buttons = new LinkedHashMap<String, UIElement>();

		for (int count = 0; count < 100; count++)
		{
			buttons.put("button" + count, UIFactory.createButton(new Pos2D(), contentArea.getWidth(), "Button " + (count + 1), false));
		}
		
		return buttons;
	}

}