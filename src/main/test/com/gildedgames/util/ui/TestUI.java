package com.gildedgames.util.ui;

import javax.vecmath.Color4f;

import net.minecraft.client.gui.GuiScreen;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.gui.Resource;
import com.gildedgames.util.core.gui.UIFrameMinecraft;
import com.gildedgames.util.ui.UIElementHolder;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.data.Position2D;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.graphics.Sprite;
import com.gildedgames.util.ui.listeners.ButtonState;
import com.gildedgames.util.ui.listeners.MouseButton;
import com.gildedgames.util.ui.util.UITexture;

public class TestUI extends UIFrameMinecraft
{

	public TestUI(GuiScreen parent)
	{
		super(parent, UIFrameMinecraft.SCREEN_DIMENSIONS);
	}

	@Override
	public void init(UIElementHolder elementHolder, Dimensions2D screenDimensions)
	{
		final Resource hopperTexture = new Resource(UtilCore.MOD_ID, "textures/gui/universe_hopper/base.png");
		final Sprite hopperSprite = new Sprite(hopperTexture, 0, 0, 256, 256, 256, 256);
		
		Dimensions2D dim = new Dimensions2D(256, 256);
		
		UITexture hopperResized = new UITexture(hopperSprite, dim.copyWith(new Position2D(60, 60)));

		elementHolder.add(new UITexture(hopperSprite, dim));
		elementHolder.add(hopperResized);
		
		hopperResized.setDrawingData(new DrawingData(1.2F, new Color4f(0.0F, 0.0F, 1.0F, 0.4F)));
	}

	@Override
	public void draw(IGraphics graphics)
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
