package com.gildedgames.util.core.gui.util.decorators;

import java.awt.Color;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

import com.gildedgames.util.core.gui.viewing.UIFrameMC;
import com.gildedgames.util.ui.UIContainer;
import com.gildedgames.util.ui.UIDecorator;
import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.util.basic.UIRectangle;

public class UIScreenMC extends UIDecorator<UIView>
{

	private boolean drawBackground = true;
	
	private Minecraft mc = Minecraft.getMinecraft();

	public UIScreenMC(UIView view)
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
	public void onInit(UIContainer container, InputProvider input)
	{
		if (this.shouldDrawBackground())
		{
			DrawingData startColor = new DrawingData(new Color(-1072689136, true));
			DrawingData endColor = new DrawingData(new Color(-804253680, true));
			
			container.add(new UIRectangle(new Dimensions2D().setArea(input.getScreenWidth(), input.getScreenHeight()), startColor, endColor));
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
