package com.gildedgames.util.core.gui;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.listeners.ButtonState;
import com.gildedgames.util.ui.listeners.MouseButton;
import com.gildedgames.util.ui.util.UIElementWrapper;

public final class UIElementWrapperMC extends GuiScreen
{

	protected final static GraphicsMC GRAPHICS = new GraphicsMC(Minecraft.getMinecraft());

	protected final static InputProviderMC INPUT = new InputProviderMC(Minecraft.getMinecraft());

	private final UIElementWrapper elementWrapper, viewWrapper;

	private final UIView view;

	public UIElementWrapperMC(UIView view)
	{
		this.view = view;
		
		this.elementWrapper = new UIElementWrapper();
		this.viewWrapper = new UIElementWrapper();
		
		this.viewWrapper.add(this.view);
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

		this.elementWrapper.clear();
		
		INPUT.setScreen(this.width, this.height);

		this.viewWrapper.init(this.elementWrapper, INPUT);
		this.elementWrapper.init(this.elementWrapper, INPUT);
	}

	@Override
	protected final void keyTyped(char charTyped, int keyTyped)
	{
		this.viewWrapper.onKeyState(charTyped, keyTyped, ButtonState.PRESS);
		this.elementWrapper.onKeyState(charTyped, keyTyped, ButtonState.PRESS);
	}

	@Override
	protected final void mouseClicked(int mouseX, int mouseY, int mouseButtonIndex)
	{
		MouseButton button = MouseButton.fromIndex(mouseButtonIndex);

		this.viewWrapper.onMouseState(INPUT, button, ButtonState.PRESS);
		this.elementWrapper.onMouseState(INPUT, button, ButtonState.PRESS);
	}

	@Override
	public final void mouseReleased(int mouseX, int mouseY, int mouseButtonIndex)
	{
		MouseButton button = MouseButton.fromIndex(mouseButtonIndex);

		this.viewWrapper.onMouseState(INPUT, button, ButtonState.RELEASED);
		this.elementWrapper.onMouseState(INPUT, button, ButtonState.RELEASED);
	}

	@Override
	public final void drawScreen(int mouseX, int mouseY, float partialTick)
	{
		this.viewWrapper.draw(GRAPHICS, INPUT);
		this.elementWrapper.draw(GRAPHICS, INPUT);
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
