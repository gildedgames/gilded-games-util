package com.gildedgames.util.core.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.ui.UIElementHolder;
import com.gildedgames.util.ui.listeners.ButtonState;
import com.gildedgames.util.ui.listeners.MouseButton;
import com.gildedgames.util.ui.util.UIDimensions;

public class TestUI extends UIFrameMinecraft
{

	public TestUI(GuiScreen parent)
	{
		super(parent, UIFrameMinecraft.SCREEN_DIMENSIONS);
	}

	@Override
	public void initContent(UIElementHolder elementHolder, UIDimensions screenDimensions)
	{
		elementHolder.add(new UITexture(new ResourceLocation(UtilCore.MOD_ID, "textures/gui/universe_hopper/base.png"), new UIDimensions(256, 256)));
	}

	@Override
	public void draw(GraphicsMinecraft graphics)
	{

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
