package com.gildedgames.util.ui.util.decorators;

import com.gildedgames.util.core.gui.util.GuiFactory;
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

		this.scrolledGui = new ScissorableGui(Dim2D.build().addModifier(this, ModifierType.ALL).flush(), scrolledGui);
		this.scrollBar = scrollBar;
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		this.scrollBar.modDim().resetPos().addModifier(this, ModifierType.HEIGHT).flush();

		this.scrolledGui.modDim().resetPos().addModifier(new Dim2DGetter()
		{

			@Override
			public Dim2D getDim()
			{
				ScrollBar scrollBar = ScrollableGui.this.scrollBar;

				float scrollPercentage = scrollBar.getScrollPercentage();

				int scrolledElementHeight = ScrollableGui.this.scrolledGui.getDim().height();
				int scissoredHeight = ScrollableGui.this.scrolledGui.getScissoredArea().height();

				int scrollValue = (int) -(scrollPercentage * (scrolledElementHeight - scissoredHeight));

				return Dim2D.build().x(scrollBar.getDim().width()).y(scrollValue).addWidth(-scrollBar.getDim().width()).flush();
			}

		}, ModifierType.WIDTH, ModifierType.POS).flush();

		this.scrollBar.modDim().resetPos().flush();

		this.scrollBar.modDim().center(false).flush();
		this.scrolledGui.modDim().center(false).flush();

		Dim2DCollection scrollingArea = new Dim2DCollection().addHolder(this);

		this.scrollBar.setScrollingAreas(scrollingArea);
		this.scrollBar.setContentArea(this.scrolledGui);
		
		int backdropPadding = 6;
		int embeddedPadding = 1;
		
		Dim2D backdropDim = Dim2D.build().buildWith(this).area().build().addArea(backdropPadding * 2, backdropPadding * 2).pos(-backdropPadding, -backdropPadding).flush();
		Dim2D embeddedDim = Dim2D.build().buildWith(this).area().build().addArea(embeddedPadding * 2, embeddedPadding * 2).pos(-embeddedPadding, -embeddedPadding).flush();;
		
		
		this.content().setElement("backdrop", GuiFactory.createPanel(backdropDim));
		this.content().setElement("backdropEmbedded", GuiFactory.createPanelEmbedded(embeddedDim));

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
