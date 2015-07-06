package com.gildedgames.util.ui.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.decorators.ScissorableGui;

public class TextBox extends GuiFrame
{

	private final Text[] text;

	public TextBox(Dim2D dim, Text... text)
	{
		super(dim);
		this.text = text;
	}

	@Override
	public void init(InputProvider input)
	{
		super.init(input);
		
		int i = 0;
		int textHeight = 0;
		
		for (Text t : this.text)
		{
			if (t.text == null || t.text.isEmpty())
			{
				continue;
			}
			final String[] strings = t.text.split("/n");

			final List<String> stringList = new ArrayList<String>(strings.length);
			Collections.addAll(stringList, strings);

			for (final String string : stringList)
			{
				final List<String> newStrings = t.font.splitStringsIntoArea(string, (int) (this.getDim().getWidth() / t.scale));

				for (final String s : newStrings)
				{
					TextElement textElement = new TextElement(new Text(s, t.drawingData.getColor(), t.scale, t.font), new Pos2D(0, textHeight));
					this.content().setElement(String.valueOf(i), textElement);
					textHeight += 1.1f * t.scaledHeight();
					i++;
				}
			}
		}
		
		this.modDim().height(textHeight).compile();
	}

}
