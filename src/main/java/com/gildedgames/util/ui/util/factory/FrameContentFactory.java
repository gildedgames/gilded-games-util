package com.gildedgames.util.ui.util.factory;

import java.util.LinkedHashMap;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.data.Dim2D;
import com.google.common.collect.ImmutableMap;

public class FrameContentFactory implements ContentFactory
{

	private GenericFactory<GuiFrame> frameFactory;
	
	private int buttonCount;
	
	public FrameContentFactory(GenericFactory<GuiFrame> frameFactory, int buttonCount)
	{
		this.frameFactory = frameFactory;
		this.buttonCount = buttonCount;
	}

	@Override
	public LinkedHashMap<String, Ui> provideContent(ImmutableMap<String, Ui> currentContent, Dim2D contentArea)
	{
		LinkedHashMap<String, Ui> buttons = new LinkedHashMap<String, Ui>();

		for (int count = 0; count < this.buttonCount; count++)
		{
			GuiFrame button = this.frameFactory.create();

			buttons.put("frame" + count, button);
		}

		return buttons;
	}
	
}
