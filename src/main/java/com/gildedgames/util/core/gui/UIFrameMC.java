package com.gildedgames.util.core.gui;

import java.awt.Color;
import java.util.List;

import net.minecraft.client.Minecraft;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.ui.UIBase;
import com.gildedgames.util.ui.UIElementContainer;
import com.gildedgames.util.ui.UIFrame;
import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.listeners.ButtonState;
import com.gildedgames.util.ui.util.UIRectangle;

public class UIFrameMC extends UIFrame
{

	private boolean drawBackground = true;
	
	private Minecraft mc = Minecraft.getMinecraft();

	public UIFrameMC(UIBase parent, Dimensions2D dim)
	{ 
		super(parent, dim);
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
	public void init(UIElementContainer container, InputProvider input)
	{
		super.init(container, input);
		
		if (this.shouldDrawBackground())
		{
			DrawingData startColor = new DrawingData(new Color(-1072689136, true));
			DrawingData endColor = new DrawingData(new Color(-804253680, true));
			
			container.add(new UIRectangle(new Dimensions2D().setArea(input.getScreenWidth(), input.getScreenHeight()), startColor, endColor));
		}
	}
	
	@Override
	public boolean onKeyState(char charTyped, int keyTyped, List<ButtonState> states)
	{
		if (keyTyped == 1 || keyTyped == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
			UtilCore.locate().view((UIView)null);
        }
		
		return super.onKeyState(charTyped, keyTyped, states);
	}

}