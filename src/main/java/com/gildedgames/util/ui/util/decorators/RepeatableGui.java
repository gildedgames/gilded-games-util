package com.gildedgames.util.ui.util.decorators;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2D.ModifierType;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public class RepeatableGui extends GuiFrame
{

	protected ScissorableGui repeatedGui;

	public RepeatableGui(Dim2D repeatArea, Gui repeatedGui)
	{
		super(repeatArea);

		this.repeatedGui = new ScissorableGui(repeatArea, repeatedGui);
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		this.repeatedGui.setScissoredArea(this.getDim());

		this.repeatedGui.modDim().clearModifiers().addModifier(this, ModifierType.POS).resetPos().compile();

		int textureHeight = this.repeatedGui.getDim().height();
		int textureWidth = this.repeatedGui.getDim().width();

		int heightCountNeeded = 0;
		int widthCountNeeded = 0;

		if (textureHeight != 0)
		{
			heightCountNeeded = this.getDim().height() / textureHeight;
		}

		if (textureWidth != 0)
		{
			widthCountNeeded = this.getDim().width() / textureWidth;
		}

		Dim2D oldDim = this.repeatedGui.getDim().clone().compile();
		Dim2D oldDimNoMods = Dim2D.build(oldDim).clearModifiers().compile();

		for (int heightAmount = 0; heightAmount <= heightCountNeeded; heightAmount++)
		{
			for (int widthAmount = 0; widthAmount <= widthCountNeeded; widthAmount++)
			{
				this.repeatedGui.draw(graphics, input);

				this.repeatedGui.modDim().addX(textureWidth).compile();
			}

			this.repeatedGui.modDim().x(oldDimNoMods.x()).addY(textureHeight).compile();
		}

		this.repeatedGui.setDim(oldDim);
	}

}
