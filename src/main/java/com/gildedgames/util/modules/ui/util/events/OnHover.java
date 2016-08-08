package com.gildedgames.util.modules.ui.util.events;

import com.gildedgames.util.modules.ui.common.Gui;
import com.gildedgames.util.modules.ui.event.GuiEvent;
import com.gildedgames.util.modules.ui.graphics.Graphics2D;
import com.gildedgames.util.modules.ui.input.InputProvider;

public abstract class OnHover<E extends Gui> extends GuiEvent<E>
{

	private boolean hovered;

	public OnHover()
	{

	}

	public abstract void onHover();

	public abstract void exitHover();

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		if (input.isHovered(this.getGui()))
		{
			if (!this.hovered)
			{
				this.onHover();

				this.hovered = true;
			}
		}
		else if (this.hovered)
		{
			this.exitHover();

			this.hovered = false;
		}
	}

	@Override
	public void initEvent()
	{

	}

}
