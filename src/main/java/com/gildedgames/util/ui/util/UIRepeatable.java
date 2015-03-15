package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.input.InputProvider;

public class UIRepeatable extends UIScissorable
{

	public UIRepeatable(Dimensions2D repeatArea, UIView view)
	{
		super(repeatArea, view);
	}

	@Override
	public void draw(IGraphics graphics, InputProvider input)
	{
		float textureHeight = this.getDimensions().getHeight();

		int textureCount = (int) (this.getCutoff().getHeight() / textureHeight);

		Dimensions2D oldDim = this.getDimensions().clone();

		for (int drawCount = 0; drawCount <= textureCount; drawCount++)
		{
			super.draw(graphics, input);
			this.getDimensions().addY(textureHeight);
		}
		
		this.setDimensions(oldDim);
	}
	
}