package com.gildedgames.util.ui.util.decorators;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.rect.ModDim2D;
import com.gildedgames.util.ui.data.rect.Rect;
import com.gildedgames.util.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public class RepeatableGui extends GuiFrame
{

	protected ScissorableGui repeatedGui;

	public RepeatableGui(Rect repeatArea, Gui repeatedGui)
	{
		this.dim().set(repeatArea);
		this.repeatedGui = new ScissorableGui(repeatArea, repeatedGui);
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		this.repeatedGui.getScissoredArea().set(this.dim());

		this.repeatedGui.dim().clear();
		this.repeatedGui.dim().add(this, ModifierType.POS);

		this.repeatedGui.dim().mod().resetPos().flush();

		float textureHeight = this.repeatedGui.dim().height();
		float textureWidth = this.repeatedGui.dim().width();

		float heightCountNeeded = 0;
		float widthCountNeeded = 0;

		if (textureHeight != 0)
		{
			heightCountNeeded = this.dim().height() / textureHeight;
		}

		if (textureWidth != 0)
		{
			widthCountNeeded = this.dim().width() / textureWidth;
		}

		ModDim2D oldDim = this.repeatedGui.dim().clone();

		ModDim2D oldDimNoMod = oldDim.clone().clear();

		for (int heightAmount = 0; heightAmount <= heightCountNeeded; heightAmount++)
		{
			for (int widthAmount = 0; widthAmount <= widthCountNeeded; widthAmount++)
			{
				this.repeatedGui.draw(graphics, input);

				this.repeatedGui.dim().mod().addX(textureWidth).flush();
			}

			this.repeatedGui.dim().mod().x(oldDimNoMod.x()).addY(textureHeight).flush();
		}

		this.repeatedGui.dim().set(oldDim);
	}

}
