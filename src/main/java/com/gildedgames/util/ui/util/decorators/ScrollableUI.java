package com.gildedgames.util.ui.util.decorators;

import com.gildedgames.util.ui.common.UIFrame;
import com.gildedgames.util.ui.common.UIView;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2DCollection;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.ScrollBar;

public class ScrollableUI extends UIFrame
{

	protected ScrollBar scrollBar;

	protected ScissorableUI scrolledView;
	
	public ScrollableUI(Dim2D scrollArea, UIView scrolledView, ScrollBar scrollBar)
	{
		super(scrollArea.clone());

		this.scrolledView = new ScissorableUI(Dim2D.build(scrollArea).addModifier(this).commit(), scrolledView);
		this.scrollBar = scrollBar;
	}
	
	@Override
	public void init(InputProvider input)
	{
		super.init(input);
		
		this.scrollBar.modDim().height(this.scrolledView.getScissoredArea().getHeight()).commit();
		
		this.scrolledView.modDim().width(this.scrolledView.getScissoredArea().getWidth() - this.scrollBar.getDim().getWidth()).commit();
		
		this.scrollBar.modDim().resetPos().commit();
		
		this.scrolledView.modDim().pos(new Pos2D(this.scrollBar.getDim().getWidth(), 0)).commit();

		this.scrollBar.modDim().center(false).commit();
		
		this.scrolledView.modDim().center(false).commit();

		Dim2DCollection scrollingArea = new Dim2DCollection().addSeekable(this.scrolledView);
	
		this.scrollBar.setScrollingAreas(scrollingArea);
		
		this.content().setElement("scrolledView", this.scrolledView);
		this.content().setElement("scrollBar", this.scrollBar);
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		int scrollValue = (int) (this.scrollBar.getScrollPercentage() * (this.scrolledView.getDim().getHeight() - this.scrolledView.getScissoredArea().getHeight()));

		this.scrolledView.modDim().y(-scrollValue).commit();
		
		super.draw(graphics, input);
	}

}