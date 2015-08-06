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
import com.gildedgames.util.ui.util.TextureElement;

public class ScrollableGui extends GuiFrame
{

	protected ScrollBar scrollBar;

	protected ScissorableGui scrolledGui;
	
	protected TextureElement backdrop, backdropEmbedded;
	
	protected final int padding;
	
	public ScrollableGui(Dim2D windowSize, Gui scrolledGui)
	{
		this(windowSize, scrolledGui, GuiFactory.createScrollBar());
	}
	
	public ScrollableGui(Dim2D windowSize, Gui scrolledGui, ScrollBar scrollBar)
	{
		this(windowSize, scrolledGui, scrollBar, GuiFactory.panel(Dim2D.flush()), GuiFactory.panelEmbedded(Dim2D.flush()), 7);
	}

	public ScrollableGui(Dim2D windowSize, Gui scrolledGui, ScrollBar scrollBar, TextureElement backdrop, TextureElement backdropEmbedded, int padding)
	{
		super(windowSize);
		
		this.padding = padding;
		
		int posPadding = this.padding + 1;
		int areaPadding = -this.padding * 2 - 2;

		this.scrolledGui = new ScissorableGui(Dim2D.build().addModifier(this, ModifierType.ALL).pos(posPadding, posPadding).area(areaPadding, areaPadding).flush(), scrolledGui);
		this.scrollBar = scrollBar;
		
		this.backdrop = backdrop;
		this.backdropEmbedded = backdropEmbedded;
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

				int scrolledElementHeight = ScrollableGui.this.scrolledGui.getDim().withoutModifiers(ModifierType.HEIGHT).height();
				int scissoredHeight = ScrollableGui.this.scrolledGui.getScissoredArea().height();

				int scrollValue = (int) -(scrollPercentage * (scrolledElementHeight - scissoredHeight));

				return Dim2D.build().x(scrollBar.getDim().withoutModifiers(ModifierType.POS).maxX()).y(ScrollableGui.this.padding + scrollValue).addHeight(-ScrollableGui.this.padding).addWidth(-scrollBar.getDim().width() - (ScrollableGui.this.padding * 2)).flush();
			}

		}, ModifierType.AREA, ModifierType.POS).flush();

		this.scrollBar.modDim().resetPos().flush();

		this.scrollBar.modDim().center(false).pos(this.padding + 1, this.padding + 1).height(-this.padding * 2 - 2).flush();
		this.scrolledGui.modDim().center(false).flush();

		Dim2DCollection scrollingArea = new Dim2DCollection().addHolder(this);

		this.scrollBar.setScrollingAreas(scrollingArea);
		this.scrollBar.setContentArea(this.scrolledGui);

		Dim2D backdropDim = Dim2D.build().buildWith(this).area().build().flush();
		Dim2D embeddedDim = Dim2D.build().buildWith(this).area().build().addArea(-this.padding * 2, -this.padding * 2).pos(this.padding, this.padding).flush();
		
		this.backdrop.setDim(backdropDim);
		this.backdropEmbedded.setDim(embeddedDim);
		
		this.content().set("backdrop", this.backdrop);
		this.content().set("backdropEmbedded", this.backdropEmbedded);

		this.content().set("scrolledView", this.scrolledGui);
		this.content().set("scrollBar", this.scrollBar);
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
	}

	@Override
	public void tick(TickInfo tickInfo, InputProvider input)
	{
		super.tick(tickInfo, input);
	}

}
