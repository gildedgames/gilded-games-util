package com.gildedgames.util.core.gui;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import com.gildedgames.util.ui.UIElementHolder;
import com.gildedgames.util.ui.UIFrame;
import com.gildedgames.util.ui.listeners.ButtonState;
import com.gildedgames.util.ui.listeners.MouseButton;
import com.gildedgames.util.ui.util.UIDimensions;
import com.gildedgames.util.ui.util.UIElementWrapper;

public abstract class UIFrameMinecraft extends GuiScreen implements UIFrame<GraphicsMinecraft>
{

	protected final static GraphicsMinecraft GRAPHICS = new GraphicsMinecraft(Minecraft.getMinecraft());

	protected static UIDimensions SCREEN_DIMENSIONS = new UIDimensions(0, 0);

	private final UIElementWrapper<GraphicsMinecraft> elementWrapper;

	private GuiScreen parent;

	public UIFrameMinecraft(GuiScreen parent, UIDimensions holderDimensions)
	{
		this.parent = parent;
		this.elementWrapper = new UIElementWrapper<GraphicsMinecraft>(holderDimensions, SCREEN_DIMENSIONS, GraphicsMinecraft.class);
	}

	public GuiScreen getParent()
	{
		return this.parent;
	}

	public UIElementWrapper<GraphicsMinecraft> getWrapper()
	{
		return this.elementWrapper;
	}

	@Override
	public final void init(UIElementHolder elementHolder, UIDimensions screenDimensions)
	{
		this.initContent(elementHolder, screenDimensions);
	}

	public abstract void initContent(UIElementHolder elementHolder, UIDimensions screenDimensions);

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
	public Class<? extends GraphicsMinecraft> getGraphicsClass()
	{
		return GraphicsMinecraft.class;
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

		SCREEN_DIMENSIONS = SCREEN_DIMENSIONS.copyAndSetDimensions(this.width, this.height);

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
	public UIDimensions getDimensions()
	{
		return this.elementWrapper.getDimensions();
	}

	@Override
	public void setDimensions(UIDimensions dimensions)
	{
		this.elementWrapper.setDimensions(dimensions);
	}

}
