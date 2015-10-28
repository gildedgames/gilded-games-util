package com.gildedgames.util.core.gui.util.events;

import com.gildedgames.util.core.gui.util.wrappers.MinecraftTextBackground;
import com.gildedgames.util.ui.event.GuiEvent;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.GuiCanvas;
import com.gildedgames.util.ui.util.Text;
import com.gildedgames.util.ui.util.TextElement;

public class MinecraftHoveredDesc extends GuiEvent
{
	
	protected Text text;
	
	protected MinecraftTextBackground background;
	
	protected TextElement textElement;
	
	public MinecraftHoveredDesc(Text text)
	{
		this.text = text;
	}

	@Override
	public void initEvent()
	{
		
	}
	
	@Override
	public void initContent(InputProvider input)
	{
		this.dim().mod().width(this.text.scaledWidth()).height(this.text.scaledHeight()).flush();
		
		this.background = new MinecraftTextBackground(this.dim());
		this.textElement = new TextElement(this.text, this.dim());
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
		
		this.textElement.dim().mod().x(input.getMouseX() + 12).y(input.getMouseY() - 12).flush();
		this.background.dim().mod().x(input.getMouseX() + 12).y(input.getMouseY() - 12).flush();
		
		if (input.isHovered(this.getGui()))
		{
			GuiCanvas.fetch("hoveredDesc").set("background", this.background);
			GuiCanvas.fetch("hoveredDesc").set("text", this.textElement);
		}
		else
		{
			GuiCanvas.fetch("hoveredDesc").remove("background", this.background);
			GuiCanvas.fetch("hoveredDesc").remove("text", this.textElement);
		}
	}

}
