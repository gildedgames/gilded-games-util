package com.gildedgames.util.ui.util.decorators;

import com.gildedgames.util.ui.common.AbstractUI;
import com.gildedgames.util.ui.common.UIView;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2DHolder;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.data.UIElementContainer;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.Dim2DModifier;
import com.gildedgames.util.ui.util.ScrollBar;

public class ScrollableUI extends AbstractUI
{

	protected ScrollBar scrollBar;

	protected ScissorableUI scrolledView;
	
	public ScrollableUI(Dim2D scrollArea, UIView scrolledView, ScrollBar scrollBar)
	{
		super(scrolledView.getDimensions());

		this.scrolledView = new ScissorableUI(scrollArea, scrolledView);
		this.scrollBar = scrollBar;
	}
	
	@Override
	public void onInit(UIElementContainer container, InputProvider input)
	{
		super.onInit(container, input);
		
		this.scrollBar.getDimensions().setHeight(this.scrolledView.getScissoredArea().getHeight());
		this.scrolledView.getDimensions().setWidth(this.scrolledView.getScissoredArea().getWidth() - this.scrollBar.getDimensions().getWidth());
		
		this.scrollBar.getDimensions().setPos(new Pos2D());
		this.scrolledView.getDimensions().setPos(new Pos2D(this.scrollBar.getDimensions().getWidth(), 0));
		
		this.scrollBar.getDimensions().setCentering(false);
		this.scrolledView.getDimensions().setCentering(false);
		
		Dim2DHolder scrollingArea = new Dim2DModifier().addDim(this.scrolledView.getScissoredArea()).addDim(this.scrollBar);
	
		this.scrollBar.setScrollingArea(scrollingArea);
		
		container.setElement("scrolledView", this.scrolledView);
		container.setElement("scrollBar", this.scrollBar);
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		int scrollValue = (int) (this.scrollBar.getScrollPercentage() * (this.scrolledView.getDimensions().getHeight() - this.scrolledView.getScissoredArea().getHeight()));

		//this.scrolledView.getDimensions().setY(-scrollValue);
		
		super.draw(graphics, input);
	}

}