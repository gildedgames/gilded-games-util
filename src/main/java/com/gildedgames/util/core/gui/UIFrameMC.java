package com.gildedgames.util.core.gui;

import java.awt.Color;

import net.minecraft.client.Minecraft;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.ui.UIElementHolder;
import com.gildedgames.util.ui.UIFrame;
import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.listeners.ButtonState;
import com.gildedgames.util.ui.util.UIRectangle;

public class UIFrameMC extends UIFrame
{

	private boolean drawBackground = true;
	
	private Minecraft mc = Minecraft.getMinecraft();

	public UIFrameMC(Dimensions2D focusArea)
	{
		super(focusArea);
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
	public void init(UIElementHolder holder, Dimensions2D screen)
	{
		super.init(holder, screen);
		
		if (this.shouldDrawBackground())
		{
			DrawingData startColor = new DrawingData(new Color(-1072689136, true));
			DrawingData endColor = new DrawingData(new Color(-804253680, true));
			
			holder.add(new UIRectangle(new Dimensions2D().set(screen.getWidth(), screen.getHeight()), startColor, endColor));
		}
	}
	
	@Override
	public boolean onKeyState(char charTyped, int keyTyped, ButtonState state)
	{
		if (keyTyped == 1 || keyTyped == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
			UtilCore.locate().view((UIView)null);
        }
		
		return super.onKeyState(charTyped, keyTyped, state);
	}

}
