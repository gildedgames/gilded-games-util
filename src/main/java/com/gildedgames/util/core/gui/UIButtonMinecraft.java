package com.gildedgames.util.core.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import com.gildedgames.util.ui.UIFrame;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.Position2D;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.listeners.ButtonState;
import com.gildedgames.util.ui.listeners.MouseButton;

public class UIButtonMinecraft extends UIFrame
{
	
	protected final static Minecraft mc = Minecraft.getMinecraft();
	
	protected String text;
	
	protected GuiButton button;
	
	public UIButtonMinecraft(GuiButton button, boolean centered)
	{
		super(new Dimensions2D().setPos(new Position2D(button.xPosition, button.yPosition)).setArea(button.getButtonWidth(), button.height).setCentered(centered));
		
		this.button = button;
	}
	
	public UIButtonMinecraft(Dimensions2D dim, String text)
	{
		super(dim);
		
		this.text = text;
		
		this.button = new GuiButton(-1, (int)dim.getX(), (int)dim.getY(), (int)dim.getWidth(), (int)dim.getHeight(), text);
	}
	
	@Override
	public void draw(IGraphics graphics, InputProvider input)
	{
		super.draw(graphics, input);
		
		this.button.drawButton(mc, input.getMouseX(), input.getMouseY());
	}
	
	@Override
	public void onMouseState(InputProvider input, MouseButton button, ButtonState state)
	{
		if (state == ButtonState.PRESS)
		{
			this.button.mousePressed(mc, input.getMouseX(), input.getMouseY());
		}
		
		if (state == ButtonState.RELEASED)
		{
			this.button.mouseReleased(input.getMouseX(), input.getMouseY());
		}
	}

}
