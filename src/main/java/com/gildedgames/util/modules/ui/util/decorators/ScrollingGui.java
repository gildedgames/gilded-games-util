package com.gildedgames.util.modules.ui.util.decorators;

import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.modules.ui.common.Decorator;
import com.gildedgames.util.modules.ui.common.Gui;
import com.gildedgames.util.modules.ui.common.GuiFrame;
import com.gildedgames.util.modules.ui.data.rect.Dim2D;
import com.gildedgames.util.modules.ui.data.rect.ModDim2D;
import com.gildedgames.util.modules.ui.data.rect.Rect;
import com.gildedgames.util.modules.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.util.modules.ui.graphics.Graphics2D;
import com.gildedgames.util.modules.ui.input.InputProvider;
import com.gildedgames.util.modules.ui.util.ScrollBar;
import com.gildedgames.util.modules.ui.util.TextureElement;
import com.gildedgames.util.modules.ui.util.rect.RectCollection;
import com.gildedgames.util.modules.ui.util.rect.RectGetter;

public class ScrollingGui extends GuiFrame implements Decorator<GuiFrame>
{

	protected ScissorableGui scrolledGui;

	public float scrollPercent;

	public ScrollingGui(Rect windowSize, Gui scrolledGui)
	{
		this.dim().set(windowSize);

		Rect dim = ModDim2D.build().add(this, ModifierType.ALL).mod().flush();

		this.scrolledGui = new ScissorableGui(dim, scrolledGui);
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		this.scrolledGui.dim().add(new RectGetter()
		{

			private float prevScrollPer, scrollPer;

			@Override
			public Rect assembleRect()
			{
				this.prevScrollPer = ScrollingGui.this.scrollPercent;

				double scrolledElementHeight = ModDim2D.clone(ScrollingGui.this.scrolledGui).clear(ModifierType.HEIGHT).height();
				double scissoredHeight = ScrollingGui.this.scrolledGui.getScissoredArea().height();

				int scrollValue = (int) -(this.prevScrollPer * (scrolledElementHeight - scissoredHeight));

				return ModDim2D.build().mod()
						.y(scrollValue)
						.flush();
			}

			@Override
			public boolean shouldReassemble()
			{
				this.scrollPer =  ScrollingGui.this.scrollPercent;

				if (this.scrollPer != this.prevScrollPer)
				{
					this.prevScrollPer = this.scrollPer;

					return true;
				}

				return false;
			}

		}, ModifierType.Y).mod().resetPos().flush();

		this.scrolledGui.dim().mod().resetPos().center(false).flush();

		this.content().set("scrolledGui", this.scrolledGui);
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
	}

	@Override
	public GuiFrame getDecoratedElement()
	{
		return this.scrolledGui;
	}

}
