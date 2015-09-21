package com.gildedgames.util.ui.util.factory;

import java.util.LinkedHashMap;

import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.event.view.MouseEventGui;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInput;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.google.common.collect.ImmutableMap;

public class TestButtonFactory2 implements ContentFactory
{
	
	private int buttonCount;
	
	public TestButtonFactory2()
	{
		this(28);
	}
	
	public TestButtonFactory2(int buttonCount)
	{
		this.buttonCount = buttonCount;
	}

	@Override
	public LinkedHashMap<String, Ui> provideContent(ImmutableMap<String, Ui> currentContent, Dim2D contentArea)
	{
		LinkedHashMap<String, Ui> buttons = new LinkedHashMap<String, Ui>();

		for (int count = 0; count < this.buttonCount; count++)
		{
			GuiFrame button = GuiFactory.button(Pos2D.flush(), 20, String.valueOf(count + 1), false);

			buttons.put("button" + count, button);
		}

		return buttons;
	}

}
