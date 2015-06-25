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

		this.scrolledView = new ScissorableUI(Dim2D.build(scrollArea).addModifier(this).compile(), scrolledView);
		this.scrollBar = scrollBar;
	}
	
	@Override
	public void init(InputProvider input)
	{
		super.init(input);
		
		this.scrollBar.modDim().height(this.scrolledView.getScissoredArea().getHeight()).compile();
		
		this.scrolledView.modDim().width(this.scrolledView.getScissoredArea().getWidth() - this.scrollBar.getDim().getWidth()).compile();
		
		this.scrollBar.modDim().resetPos().compile();
		
		this.scrolledView.modDim().pos(new Pos2D(this.scrollBar.getDim().getWidth(), 0)).compile();

		this.scrollBar.modDim().center(false).compile();
		
		this.scrolledView.modDim().center(false).compile();

		Dim2DCollection scrollingArea = new Dim2DCollection().addSeekable(this.scrolledView);
	
		this.scrollBar.setScrollingAreas(scrollingArea);
		
		this.content().setElement("scrolledView", this.scrolledView);
		this.content().setElement("scrollBar", this.scrollBar);
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		int scrollValue = (int) (this.scrollBar.getScrollPercentage() * (this.scrolledView.getDim().getHeight() - this.scrolledView.getScissoredArea().getHeight()));

		this.scrolledView.modDim().y(-scrollValue).compile();
		
		super.draw(graphics, input);
	}

}