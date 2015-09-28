package com.gildedgames.util.ui.util.events;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.event.GuiEvent;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public class GuiEventScaleWhenHovered extends GuiEvent
{
	
	private final float selectedScale, originalScale;
	
	public GuiEventScaleWhenHovered(Gui gui, float originalScale, float selectedScale)
	{
		super(gui);
		
		this.selectedScale = selectedScale;
		this.originalScale = originalScale;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		Pos2D mousePos = Pos2D.flush(input.getMouseX(), input.getMouseY());
		
		if (this.getGui().getDim().intersects(mousePos))
		{
			this.getGui().modDim().scale(this.selectedScale).flush();
		}
		else
		{
			this.getGui().modDim().scale(this.originalScale).flush();
		}
	}
	
}
