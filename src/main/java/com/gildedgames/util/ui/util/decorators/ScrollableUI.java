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
		super(scrollArea.clone());

		this.scrolledView = new ScissorableUI(scrollArea, scrolledView);
		this.scrollBar = scrollBar;
	}
	
	@Override
	public void onInit(UIElementContainer container, InputProvider input)
	{
		super.onInit(container, input);
		
		this.scrollBar.getDim().setHeight(this.scrolledView.getScissoredArea().getHeight());
		this.scrolledView.getDim().setWidth(this.scrolledView.getScissoredArea().getWidth() - this.scrollBar.getDim().getWidth());
		
		this.scrollBar.getDim().setPos(new Pos2D());
		this.scrolledView.getDim().setPos(new Pos2D(this.scrollBar.getDim().getWidth(), 0));

		this.scrollBar.getDim().setCentering(false);
		this.scrolledView.getDim().setCentering(false);
		
		Dim2DHolder scrollingArea = new Dim2DModifier().addDim(this.scrolledView.getScissoredArea());
	
		this.scrollBar.setScrollingArea(scrollingArea);
		
		container.setElement("scrolledView", this.scrolledView);
		container.setElement("scrollBar", this.scrollBar);
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		int scrollValue = (int) (this.scrollBar.getScrollPercentage() * (this.scrolledView.getDim().getHeight() - this.scrolledView.getScissoredArea().getHeight()));

		this.scrolledView.getDim().setY(-scrollValue);
		
		super.draw(graphics, input);
	}

}