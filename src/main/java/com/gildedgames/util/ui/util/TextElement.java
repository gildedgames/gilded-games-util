package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2D.ModifierType;
import com.gildedgames.util.ui.data.Dim2DGetter;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public class TextElement extends GuiFrame
{
	
	private final Text text;

	private final boolean centered;

	public TextElement(Text text, Pos2D pos, boolean centered)
	{
		super(Dim2D.build().pos(pos).scale(text.scale).centerX(centered).flush());

		this.text = text;
		this.centered = centered;
		
		this.modDim().addModifier(new Dim2DGetter<TextElement>(this)
		{

			@Override
			public Dim2D assembleDim()
			{
				return Dim2D.build().width(this.seekFrom.text.scaledWidth()).height(this.seekFrom.text.scaledHeight()).flush();
			}

			@Override
			public boolean dimHasChanged()
			{
				return true;
			}
			
		}, ModifierType.X, ModifierType.AREA).flush();
	}
	
	public void setText(String text)
	{
		this.text.text = text;
	}
	
	public String getText()
	{
		return this.text.text;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
		
		graphics.drawText(this.text.text, this.getDim(), this.text.drawingData);
	}

}
