package com.gildedgames.util.modules.ui.util;

import org.lwjgl.opengl.GL11;

import com.gildedgames.util.modules.ui.UiModule;
import com.gildedgames.util.modules.ui.common.GuiFrame;
import com.gildedgames.util.modules.ui.graphics.Graphics2D;
import com.gildedgames.util.modules.ui.input.InputProvider;

public class GuiCanvas extends GuiFrame
{

	private boolean disableDepth = true;
	
	private float depth;
	
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
		GL11.glPushMatrix();
		
		if (this.disableDepth)
		{
			GL11.glDisable(GL11.GL_DEPTH_TEST);
		}
		else
		{
			GL11.glTranslatef(0, 0, this.depth);
		}
		
		super.draw(graphics, input);
		
		if (this.disableDepth)
		{
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		}
		else
		{
			GL11.glTranslatef(0, 0, this.depth);
		}
		
		GL11.glPopMatrix();
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
		return GuiCanvas.fetch(key, 0.0F);
	}
	
	public static GuiCanvas fetch(String key, float depth)
	{
		if (UiModule.locate().hasFrame())
		{
			GuiFrame currentFrame = UiModule.locate().getCurrentFrame();
			
			if (!currentFrame.events().contains(key))
			{
				currentFrame.events().set(key, new GuiCanvas());
			}
			
			GuiCanvas canvas = currentFrame.events().get(key, GuiCanvas.class);
			
			if (depth != 0.0F)
			{
				canvas.disableDepth = false;
				canvas.depth = depth;
			}

			return canvas;
		}
		
		return null;
	}
	
}
