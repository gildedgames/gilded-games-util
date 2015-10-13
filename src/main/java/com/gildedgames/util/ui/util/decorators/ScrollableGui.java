package com.gildedgames.util.ui.util.decorators;

import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.data.rect.Rect;
import com.gildedgames.util.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.ScrollBar;
import com.gildedgames.util.ui.util.TextureElement;
import com.gildedgames.util.ui.util.rect.RectCollection;
import com.gildedgames.util.ui.util.rect.RectGetter;

public class ScrollableGui extends GuiFrame
{

	protected ScrollBar scrollBar;

	protected ScissorableGui scrolledGui;
	
	protected TextureElement backdrop, backdropEmbedded;
	
	protected final int padding;
	
	public ScrollableGui(Rect windowSize, Gui scrolledGui)
	{
		this(windowSize, scrolledGui, GuiFactory.createScrollBar());
	}
	
	public ScrollableGui(Rect windowSize, Gui scrolledGui, ScrollBar scrollBar)
	{
		this(windowSize, scrolledGui, scrollBar, GuiFactory.panel(Rect.flush()), GuiFactory.panelEmbedded(Rect.flush()), 7);
	}

	public ScrollableGui(Rect windowSize, Gui scrolledGui, ScrollBar scrollBar, TextureElement backdrop, TextureElement backdropEmbedded, int padding)
	{
		this.dim().set(windowSize);
		
		this.padding = padding;
		
		int posPadding = this.padding + 1;
		int areaPadding = -this.padding * 2 - 2;

		this.scrolledGui = new ScissorableGui(Rect.build().add(this, ModifierType.ALL).pos(posPadding, posPadding).area(areaPadding, areaPadding).flush(), scrolledGui);
		this.scrollBar = scrollBar;
		
		this.backdrop = backdrop;
		this.backdropEmbedded = backdropEmbedded;
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		this.scrollBar.dim().mod().resetPos().addModifier(this, ModifierType.HEIGHT).flush();

		this.scrolledGui.dim().mod().resetPos().addModifier(new RectGetter()
		{
			
			private boolean dimHasChanged;
			
			private float prevScrollPer, scrollPer;

			@Override
			public Rect assembleRect()
			{
				ScrollBar scrollBar = ScrollableGui.this.scrollBar;

				this.prevScrollPer = scrollBar.getScrollPercentage();

				double scrolledElementHeight = Rect.flush(ScrollableGui.this.scrolledGui).disableModifiers(ModifierType.HEIGHT).flush().height();
				double scissoredHeight = ScrollableGui.this.scrolledGui.getScissoredArea().height();

				int scrollValue = (int) -(this.prevScrollPer * (scrolledElementHeight - scissoredHeight));

				return Rect.build().x(Rect.flush(scrollBar).disableModifiers(ModifierType.POS).flush().maxX()).y(ScrollableGui.this.padding + scrollValue).addHeight(-ScrollableGui.this.padding).addWidth(-scrollBar.dim().width() - (ScrollableGui.this.padding * 2)).flush();
			}

			@Override
			public boolean shouldReassemble()
			{
				ScrollBar scrollBar = ScrollableGui.this.scrollBar;
				
				this.scrollPer = scrollBar.getScrollPercentage();
				
				if (this.scrollPer != this.prevScrollPer)
				{
					this.prevScrollPer = this.scrollPer;
					
					return true;
				}
				
				return false;
			}

		}, ModifierType.AREA, ModifierType.POS).flush();

		this.scrollBar.dim().mod().resetPos().flush();

		this.scrollBar.dim().mod().center(false).pos(this.padding + 1, this.padding + 1).height(-this.padding * 2 - 2).flush();
		this.scrolledGui.dim().mod().center(false).flush();

		RectCollection scrollingArea = RectCollection.build().addHolder(this).flush();

		this.scrollBar.setScrollingAreas(scrollingArea);
		this.scrollBar.setContentArea(this.scrolledGui);

		Rect backdropDim = Rect.build().buildWith(this).area().build().flush();
		Rect embeddedDim = Rect.build().buildWith(this).area().build().addArea(-this.padding * 2, -this.padding * 2).pos(this.padding, this.padding).flush();
		
		this.backdrop.setDim(backdropDim);
		this.backdropEmbedded.setDim(embeddedDim);
		
		this.content().set("backdrop", this.backdrop);
		this.content().set("backdropEmbedded", this.backdropEmbedded);

		this.content().set("scrolledGui", this.scrolledGui);
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
