package com.gildedgames.util.core.gui.util.decorators;

import java.awt.Color;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.GuiDecorator;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.data.UiContainer;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.util.RectangleElement;

public class MinecraftGui extends GuiDecorator<Gui>
{

	private boolean drawBackground = true;
	
	private Minecraft mc = Minecraft.getMinecraft();

	public MinecraftGui(Gui view)
	{ 
		super(view);
	}
	
	public boolean shouldDrawBackground()
	{
		return this.drawBackground;
	}
	
	public void setDrawBackground(boolean drawBackground)
	{
		this.drawBackground = drawBackground;
	}
	
	@Override
	public void preInitContent(InputProvider input)
	{
		if (this.shouldDrawBackground())
		{
			DrawingData startColor = new DrawingData(new Color(-1072689136, true));
			DrawingData endColor = new DrawingData(new Color(-804253680, true));
			
			Dim2D dim = Dim2D.build().area(input.getScreenWidth(), input.getScreenHeight()).flush();
			
			this.content().set("darkBackground", new RectangleElement(dim.toHolder(), startColor, endColor));
		}
	}
	
	@Override
	protected void postInitContent(InputProvider input)
	{
		
	}
	
	@Override
	public boolean onKeyboardInput(KeyboardInputPool pool)
	{
		if (pool.has(Keyboard.KEY_ESCAPE) || pool.has(this.mc.gameSettings.keyBindInventory.getKeyCode()))
        {
			Minecraft.getMinecraft().displayGuiScreen(null);
        }
		
		return super.onKeyboardInput(pool);
	}

}
