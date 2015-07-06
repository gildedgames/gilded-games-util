package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public class TextElement extends GuiFrame
{
	private final Text text;

	public TextElement(Text text, Pos2D minPos)
	{
		super(Dim2D.build().pos(minPos).width(text.width()).height(text.height()).scale(text.scale).compile());
		this.text = text;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
		graphics.drawText(this.text.text, this.getDim(), this.text.drawingData);
	}

}
