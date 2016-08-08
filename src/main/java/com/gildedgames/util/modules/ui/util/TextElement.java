package com.gildedgames.util.modules.ui.util;

import com.gildedgames.util.modules.ui.common.GuiFrame;
import com.gildedgames.util.modules.ui.data.rect.Dim2D;
import com.gildedgames.util.modules.ui.data.rect.Rect;
import com.gildedgames.util.modules.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.util.modules.ui.graphics.Graphics2D;
import com.gildedgames.util.modules.ui.input.InputProvider;
import com.gildedgames.util.modules.ui.util.rect.RectGetter;

public class TextElement extends GuiFrame
{

	private final Text text;

	public TextElement(Text text, Rect rect)
	{
		super(Dim2D.build(rect).scale(text.scale).flush());

		this.text = text;

		this.dim().add(new RectGetter<TextElement>(this)
		{

			@Override
			public Rect assembleRect()
			{
				return Dim2D.build().width(TextElement.this.text.width()).height(TextElement.this.text.height()).flush();
			}

			@Override
			public boolean shouldReassemble()
			{
				return true;
			}

		}, ModifierType.AREA);
	}

	public Text getText()
	{
		return this.text;
	}

	public void setData(String text)
	{
		this.text.text = text;
	}

	public String getData()
	{
		return this.text.text;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);

		graphics.drawText(this.text.text, this.dim(), this.text.drawingData);
	}

}
