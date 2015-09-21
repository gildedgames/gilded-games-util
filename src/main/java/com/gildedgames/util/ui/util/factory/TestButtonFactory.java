package com.gildedgames.util.ui.util.factory;

import java.util.LinkedHashMap;

import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Pos2D;
import com.google.common.collect.ImmutableMap;

public class TestButtonFactory implements ContentFactory
{

	@Override
	public LinkedHashMap<String, Ui> provideContent(ImmutableMap<String, Ui> currentContent, Dim2D contentArea)
	{
		LinkedHashMap<String, Ui> buttons = new LinkedHashMap<String, Ui>();

		for (int count = 0; count < 100; count++)
		{
			buttons.put("button" + count, GuiFactory.button(Pos2D.flush(), contentArea.width(), "Button " + (count + 1), false));
		}

		return buttons;
	}

}
