package com.gildedgames.util.ui.util.decorators;

import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public class UIRepeatable extends UIScissorable
{

	public UIRepeatable(UIView view)
	{
		super(view.getDimensions(), view);
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		int textureHeight = this.getDimensions().getHeight();
		int textureWidth = this.getDimensions().getWidth();
		
		int heightCountNeeded = 0;
		int widthCountNeeded = 0;

		if (textureHeight != 0)
		{
			heightCountNeeded = (int) (this.getCutoff().getHeight() / textureHeight);
		}
		
		if (textureWidth != 0)
		{
			widthCountNeeded = (int) (this.getCutoff().getWidth() / textureWidth);
		}

		Dimensions2D oldDim = this.getDimensions().clone();
		
		for (int heightAmount = 0; heightAmount <= heightCountNeeded; heightAmount++)
		{
			for (int widthAmount = 0; widthAmount <= widthCountNeeded; widthAmount++)
			{
				super.draw(graphics, input);
				
				this.getDimensions().addX(textureWidth);
			}

			this.getDimensions().setX(oldDim.getX());
			this.getDimensions().addY(textureHeight);
		}
		
		this.getDimensions().set(oldDim);
	}
	
}