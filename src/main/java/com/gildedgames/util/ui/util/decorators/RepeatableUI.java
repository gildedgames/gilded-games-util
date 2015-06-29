package com.gildedgames.util.ui.util.decorators;

import com.gildedgames.util.ui.common.UIFrame;
import com.gildedgames.util.ui.common.UIView;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2D.ModifierType;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public class RepeatableUI extends UIFrame
{
	
	protected ScissorableUI repeatedView;

	public RepeatableUI(Dim2D repeatArea, UIView repeatedView)
	{
		super(repeatArea);
		
		this.repeatedView = new ScissorableUI(repeatArea, repeatedView);
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		this.repeatedView.setScissoredArea(this.getDim());
		
		this.repeatedView.modDim().clearModifiers().addModifier(this, ModifierType.POS).resetPos().compile();

		int textureHeight = this.repeatedView.getDim().getHeight();
		int textureWidth = this.repeatedView.getDim().getWidth();
		
		int heightCountNeeded = 0;
		int widthCountNeeded = 0;

		if (textureHeight != 0)
		{
			heightCountNeeded = (int) (this.getDim().getHeight() / textureHeight);
		}
		
		if (textureWidth != 0)
		{
			widthCountNeeded = (int) (this.getDim().getWidth() / textureWidth);
		}

		Dim2D oldDim = this.repeatedView.getDim().clone().compile();
		Dim2D oldDimNoMods = Dim2D.build(oldDim).clearModifiers().compile();
		
		for (int heightAmount = 0; heightAmount <= heightCountNeeded; heightAmount++)
		{
			for (int widthAmount = 0; widthAmount <= widthCountNeeded; widthAmount++)
			{
				this.repeatedView.draw(graphics, input);
				
				this.repeatedView.modDim().addX(textureWidth).compile();
			}

			this.repeatedView.modDim().x(oldDimNoMods.getX()).addY(textureHeight).compile();
		}
		
		this.repeatedView.setDim(oldDim);
	}
	
}