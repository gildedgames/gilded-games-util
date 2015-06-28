package com.gildedgames.util.core.gui.util.decorators;

import java.awt.Color;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

import com.gildedgames.util.ui.common.UIDecorator;
import com.gildedgames.util.ui.common.UIView;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.data.UIContainer;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.util.RectangleElement;

public class MinecraftDecorator extends UIDecorator<UIView>
{

	private boolean drawBackground = true;
	
	private Minecraft mc = Minecraft.getMinecraft();

	public MinecraftDecorator(UIView view)
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
	public void init(InputProvider input)
	{
		if (this.shouldDrawBackground())
		{
			DrawingData startColor = new DrawingData(new Color(-1072689136, true));
			DrawingData endColor = new DrawingData(new Color(-804253680, true));
			
			Dim2D dim = Dim2D.build().area(input.getScreenWidth(), input.getScreenHeight()).compile();
			
			this.content().setElement("darkBackground", new RectangleElement(dim, startColor, endColor));
		}
		
		super.init(input);
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

	@Override
	public UIContainer assembleAllContent()
	{
		return this.getDecoratedElement().seekContent().merge(true, this.seekDecoratorContent());
	}

}
