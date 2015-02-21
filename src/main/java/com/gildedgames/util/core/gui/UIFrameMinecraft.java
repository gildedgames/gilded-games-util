package com.gildedgames.util.core.gui;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import com.gildedgames.util.ui.UIElement;
import com.gildedgames.util.ui.UIElementHolder;
import com.gildedgames.util.ui.UIFrame;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.listeners.ButtonState;
import com.gildedgames.util.ui.listeners.MouseButton;
import com.gildedgames.util.ui.util.UIElementWrapper;

public abstract class UIFrameMinecraft extends GuiScreen implements UIFrame, UIElementHolder
{

	protected final static GraphicsMinecraft GRAPHICS = new GraphicsMinecraft(Minecraft.getMinecraft());

	protected static Dimensions2D SCREEN_DIMENSIONS = new Dimensions2D(0, 0);

	private final UIElementWrapper elementWrapper;

	private GuiScreen parent;

	public UIFrameMinecraft(GuiScreen parent, Dimensions2D holderDimensions)
	{
		this.parent = parent;
		this.elementWrapper = new UIElementWrapper(holderDimensions, SCREEN_DIMENSIONS);
	}

	public GuiScreen getParent()
	{
		return this.parent;
	}

	public UIElementWrapper getWrapper()
	{
		return this.elementWrapper;
	}
	
	@Override
	public void add(UIElement element)
	{
		this.elementWrapper.add(element);
	}

	@Override
	public void remove(UIElement element)
	{
		this.elementWrapper.remove(element);
	}

	@Override
	public boolean isEnabled()
	{
		return this.getWrapper().isEnabled();
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		this.getWrapper().setEnabled(enabled);
	}

	@Override
	public boolean isVisible()
	{
		return this.getWrapper().isVisible();
	}

	@Override
	public void setVisible(boolean visible)
	{
		this.getWrapper().setVisible(visible);
	}

	@Override
	public final void initGui()
	{
		this.updateScreen();

		final ScaledResolution resolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);

		this.width = resolution.getScaledWidth();
		this.height = resolution.getScaledHeight();

		SCREEN_DIMENSIONS = SCREEN_DIMENSIONS.copyWith(this.width, this.height);

		this.init(this.elementWrapper, SCREEN_DIMENSIONS);

		this.getWrapper().init(this.getWrapper(), SCREEN_DIMENSIONS);
	}

	@Override
	protected final void keyTyped(char charTyped, int keyTyped)
	{
		if (this.isEnabled() && this.onKeyState(charTyped, keyTyped, ButtonState.PRESS))
		{
			return;
		}

		this.getWrapper().onKeyState(charTyped, keyTyped, ButtonState.PRESS);
	}

	@Override
	protected final void mouseClicked(int mouseX, int mouseY, int mouseButtonIndex)
	{
		MouseButton button = MouseButton.fromIndex(mouseButtonIndex);

		if (this.isEnabled())
		{
			this.onMouseState(mouseX, mouseY, button, ButtonState.PRESS);
		}

		this.getWrapper().onMouseState(mouseX, mouseY, button, ButtonState.PRESS);
	}

	@Override
	public final void mouseReleased(int mouseX, int mouseY, int mouseButtonIndex)
	{
		MouseButton button = MouseButton.fromIndex(mouseButtonIndex);

		if (this.isEnabled())
		{
			this.onMouseState(mouseX, mouseY, button, ButtonState.RELEASED);
		}

		this.getWrapper().onMouseState(mouseX, mouseY, button, ButtonState.RELEASED);
	}

	@Override
	public final void drawScreen(int mouseX, int mouseY, float partialTick)
	{
		if (this.isVisible())
		{
			this.draw(GRAPHICS);
		}

		this.getWrapper().draw(GRAPHICS);
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

	@Override
	public Dimensions2D getDimensions()
	{
		return this.elementWrapper.getDimensions();
	}

	@Override
	public void setDimensions(Dimensions2D dimensions)
	{
		this.elementWrapper.setDimensions(dimensions);
	}

}
