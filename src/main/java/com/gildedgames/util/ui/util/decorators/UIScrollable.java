package com.gildedgames.util.ui.util.decorators;

import com.gildedgames.util.ui.UIBasicAbstract;
import com.gildedgames.util.ui.UIContainer;
import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.UIScrollBar;

public class UIScrollable extends UIBasicAbstract
{

	protected UIScrollBar scrollBar;

	protected UIScissorable scrolledView;
	
	public UIScrollable(Dimensions2D scrollArea, UIView scrolledView, UIScrollBar scrollBar)
	{
		super(Dimensions2D.combine(scrollBar.getDimensions(), scrollArea));

		this.scrolledView = new UIScissorable(this.getDimensions().clone(), scrolledView);
		this.scrollBar = scrollBar;
	}
	
	@Override
	public void onInit(UIContainer container, InputProvider input)
	{
		super.onInit(container, input);
		
		this.scrollBar.setContentDimensions(this.scrolledView.getDimensions());
		
		container.add(this.scrolledView);
		container.add(this.scrollBar);
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		int maxYPos = this.scrolledView.getDimensions().withoutOrigin().getHeight() - this.scrolledView.getScissoredArea().withoutOrigin().getHeight();

		int scrollValue = (int) -(this.scrollBar.getScrollPercentage() * maxYPos);

		this.scrolledView.getDimensions().setY(scrollValue);
		
		super.draw(graphics, input);
	}

}