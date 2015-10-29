package com.gildedgames.util.ui.util;

import org.lwjgl.opengl.GL11;

import com.gildedgames.util.ui.UiCore;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public class GuiCanvas extends GuiFrame
{

	private boolean disableDepth;
	
	public GuiCanvas()
	{
		
	}
	
	@Override
	public void initContent(InputProvider input)
	{
		
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		if (this.disableDepth)
		{
			GL11.glDisable(GL11.GL_DEPTH_TEST);
		}
		else
		{
			GL11.glTranslatef(0, 0, 550.0F);
		}
		
		super.draw(graphics, input);
		
		if (this.disableDepth)
		{
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		}
		else
		{
			GL11.glTranslatef(0, 0, 550.0F);
		}
	}
	
	public <T extends GuiFrame> T get(String key)
	{
		return (T) this.content().get(key);
	}
	
	public <T extends GuiFrame> void set(String key, T obj)
	{
		if (this.content().get(key) != obj)
		{
			this.content().set(key, obj);
		}
	}
	
	public void remove(String key)
	{
		this.content().remove(key);
	}
	
	public <T extends GuiFrame> void remove(String key, T obj)
	{
		if (this.content().get(key) == obj)
		{
			this.content().remove(key);
		}
	}
	
	public static GuiCanvas fetch(String key)
	{
		return GuiCanvas.fetch(key, true);
	}
	
	public static GuiCanvas fetch(String key, boolean disableDepth)
	{
		if (UiCore.locate().hasFrame())
		{
			GuiFrame currentFrame = UiCore.locate().getCurrentFrame();
			
			if (!currentFrame.events().contains(key))
			{
				currentFrame.events().set(key, new GuiCanvas());
			}
			
			GuiCanvas canvas = currentFrame.events().get(key, GuiCanvas.class);
			
			canvas.disableDepth = disableDepth;
			
			return canvas;
		}
		
		return null;
	}
	
}
