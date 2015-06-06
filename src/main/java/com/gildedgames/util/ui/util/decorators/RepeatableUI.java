package com.gildedgames.util.ui.util.decorators;

import com.gildedgames.util.ui.common.AbstractUI;
import com.gildedgames.util.ui.common.UIView;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public class RepeatableUI extends AbstractUI
{
	
	protected ScissorableUI repeatedView;

	protected Dim2D repeatedArea = new Dim2D();
	
	public RepeatableUI(UIView repeatedView)
	{
		super(new Dim2D());
		
		this.repeatedView = new ScissorableUI(new Dim2D(), repeatedView);
		
		this.repeatedView.getDimensions().addModifier(this);
	}
	
	public Dim2D getRepeatedArea()
	{
		return this.repeatedArea;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		this.repeatedView.getScissoredArea().set(this.getDimensions());
		
		int textureHeight = this.repeatedView.getDimensions().getHeight();
		int textureWidth = this.repeatedView.getDimensions().getWidth();
		
		int heightCountNeeded = 0;
		int widthCountNeeded = 0;

		if (textureHeight != 0)
		{
			heightCountNeeded = (int) (this.getDimensions().getHeight() / textureHeight);
		}
		
		if (textureWidth != 0)
		{
			widthCountNeeded = (int) (this.getDimensions().getWidth() / textureWidth);
		}

		Dim2D oldDim = this.repeatedView.getDimensions().clone();
		
		for (int heightAmount = 0; heightAmount <= heightCountNeeded; heightAmount++)
		{
			for (int widthAmount = 0; widthAmount <= widthCountNeeded; widthAmount++)
			{
				this.repeatedView.draw(graphics, input);
				
				this.repeatedView.getDimensions().addX(textureWidth);
			}

			this.repeatedView.getDimensions().setX(oldDim.getX());
			this.repeatedView.getDimensions().addY(textureHeight);
		}
		
		this.repeatedView.getDimensions().set(oldDim);
	}
	
}