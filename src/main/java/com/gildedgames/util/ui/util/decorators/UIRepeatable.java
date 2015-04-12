package com.gildedgames.util.ui.util.decorators;

import com.gildedgames.util.ui.UIBasicAbstract;
import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public class UIRepeatable extends UIBasicAbstract
{
	
	protected UIScissorable repeatedView;

	protected Dimensions2D repeatedArea = new Dimensions2D();
	
	public UIRepeatable(UIView repeatedView)
	{
		super(new Dimensions2D());
		
		this.repeatedView = new UIScissorable(new Dimensions2D(), repeatedView);
		
		this.repeatedView.getDimensions().setOrigin(this);
	}
	
	public Dimensions2D getRepeatedArea()
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

		Dimensions2D oldDim = this.repeatedView.getDimensions().clone();
		
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