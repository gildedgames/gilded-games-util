package com.gildedgames.util.core.gui;

import com.gildedgames.util.ui.UIDimensions;
import com.gildedgames.util.ui.UIElementHolder;
import com.gildedgames.util.ui.listeners.ButtonState;
import com.gildedgames.util.ui.listeners.MouseButton;

public class TestUI extends UIFrameMinecraft
{

	public TestUI(UIFrameMinecraft parent)
	{
		super(parent);
	}

	@Override
	public void initContent(UIElementHolder elementHolder, UIDimensions screenDimensions)
	{
		
	}

	@Override
	public void draw(GraphicsMinecraft graphics)
	{
		
	}

	@Override
	public double getWidth()
	{
		return 0;
	}

	@Override
	public double getHeight()
	{
		return 0;
	}

	@Override
	public boolean onKeyState(char charTyped, int keyTyped, ButtonState state)
	{
		return false;
	}

	@Override
	public void onMouseState(int mouseX, int mouseY, MouseButton button, ButtonState state)
	{
		
	}

	@Override
	public void onMouseScroll(int mouseX, int mouseY, int scrollDifference)
	{
		
	}

}
