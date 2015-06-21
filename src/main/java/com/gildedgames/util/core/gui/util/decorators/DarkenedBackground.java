package com.gildedgames.util.core.gui.util.decorators;

import java.awt.Color;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

import com.gildedgames.util.ui.common.UIDecorator;
import com.gildedgames.util.ui.common.UIView;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.data.UIElementContainer;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.util.RectangleElement;

public class DarkenedBackground extends UIDecorator<UIView>
{

	private boolean drawBackground = true;
	
	private Minecraft mc = Minecraft.getMinecraft();

	public DarkenedBackground(UIView view)
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
	public void onInit(UIElementContainer container, InputProvider input)
	{
		if (this.shouldDrawBackground())
		{
			DrawingData startColor = new DrawingData(new Color(-1072689136, true));
			DrawingData endColor = new DrawingData(new Color(-804253680, true));
			
			container.setElement("darkBackground", new RectangleElement(Dim2D.build().area(input.getScreenWidth(), input.getScreenHeight()).commit(), startColor, endColor));
		}
		
		super.onInit(container, input);
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
