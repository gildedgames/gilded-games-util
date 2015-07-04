package com.gildedgames.util.ui.util.decorators;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2D.ModifierType;
import com.gildedgames.util.ui.data.Dim2DCollection;
import com.gildedgames.util.ui.data.Dim2DGetter;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.ScrollBar;

public class ScrollableGui extends GuiFrame
{

	protected ScrollBar scrollBar;

	protected ScissorableGui scrolledGui;

	public ScrollableGui(Dim2D windowSize, Gui scrolledGui, ScrollBar scrollBar)
	{
		super(windowSize);

		this.scrolledGui = new ScissorableGui(Dim2D.build().addModifier(this, ModifierType.POS, ModifierType.WIDTH, ModifierType.HEIGHT).compile(), scrolledGui);
		this.scrollBar = scrollBar;
	}

	@Override
	public void init(InputProvider input)
	{
		super.init(input);

		this.scrollBar.modDim().resetPos().addModifier(this, ModifierType.HEIGHT).compile();

		this.scrolledGui.modDim().resetPos().addModifier(new Dim2DGetter()
		{

			@Override
			public Dim2D getDim()
			{
				ScrollBar scrollBar = ScrollableGui.this.scrollBar;

				float scrollPercentage = scrollBar.getScrollPercentage();

				int scrolledElementHeight = ScrollableGui.this.scrolledGui.getDim().getHeight();
				int scissoredHeight = ScrollableGui.this.scrolledGui.getScissoredArea().getHeight();

				int scrollValue = (int) -(scrollPercentage * (scrolledElementHeight - scissoredHeight));

				return Dim2D.build(ScrollableGui.this).x(scrollBar.getDim().getWidth()).y(scrollValue).addWidth(-scrollBar.getDim().getWidth()).compile();
			}

		}, ModifierType.WIDTH, ModifierType.POS).compile();

		this.scrollBar.modDim().resetPos().compile();

		this.scrollBar.modDim().center(false).compile();
		this.scrolledGui.modDim().center(false).compile();

		Dim2DCollection scrollingArea = new Dim2DCollection().addHolder(this);

		this.scrollBar.setScrollingAreas(scrollingArea);

		this.content().setElement("scrolledView", this.scrolledGui);
		this.content().setElement("scrollBar", this.scrollBar);
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
	}

	@Override
	public void tick(InputProvider input, TickInfo tickInfo)
	{
		super.tick(input, tickInfo);
	}

}
