package com.gildedgames.util.core.gui;

import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.listeners.ButtonState;
import com.gildedgames.util.ui.listeners.MouseButton;
import com.gildedgames.util.ui.util.UIElementWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;

public final class UIElementWrapperMinecraft extends GuiScreen
{

	protected final static GraphicsMinecraft GRAPHICS = new GraphicsMinecraft(Minecraft.getMinecraft());

	protected static Dimensions2D SCREEN_DIMENSIONS = new Dimensions2D(0, 0);

	private final UIElementWrapper elementWrapper;

	private final UIView view;

	public UIElementWrapperMinecraft(UIView view)
	{
		this.view = view;
		this.elementWrapper = new UIElementWrapper(SCREEN_DIMENSIONS);
		
		this.elementWrapper.add(this.view);
	}
	
	public UIView getView()
	{
		return this.view;
	}
	
	@Override
	public final void initGui()
	{
		this.updateScreen();

		final ScaledResolution resolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);

		this.width = resolution.getScaledWidth();
		this.height = resolution.getScaledHeight();

		SCREEN_DIMENSIONS = SCREEN_DIMENSIONS.copyWith(this.width, this.height);

		this.elementWrapper.init(this.elementWrapper, SCREEN_DIMENSIONS);
	}

	@Override
	protected final void keyTyped(char charTyped, int keyTyped)
	{
		this.elementWrapper.onKeyState(charTyped, keyTyped, ButtonState.PRESS);
	}

	@Override
	protected final void mouseClicked(int mouseX, int mouseY, int mouseButtonIndex)
	{
		MouseButton button = MouseButton.fromIndex(mouseButtonIndex);

		this.elementWrapper.onMouseState(mouseX, mouseY, button, ButtonState.PRESS);
	}

	@Override
	public final void mouseReleased(int mouseX, int mouseY, int mouseButtonIndex)
	{
		MouseButton button = MouseButton.fromIndex(mouseButtonIndex);

		this.elementWrapper.onMouseState(mouseX, mouseY, button, ButtonState.RELEASED);
	}

	@Override
	public final void drawScreen(int mouseX, int mouseY, float partialTick)
	{
		this.elementWrapper.draw(GRAPHICS);
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	@Override
	protected final void actionPerformed(GuiButton button) throws IOException
	{
		
	}

}
