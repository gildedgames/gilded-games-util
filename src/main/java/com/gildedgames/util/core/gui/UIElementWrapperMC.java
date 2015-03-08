package com.gildedgames.util.core.gui;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.input.Mouse;

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
		INPUT.setScaleFactor(resolution.getScaleFactor());

		this.viewWrapper.init(this.elementWrapper, INPUT);
		this.elementWrapper.init(this.elementWrapper, INPUT);
	}

	@Override
	protected final void keyTyped(char charTyped, int keyTyped)
	{
		List<ButtonState> states = Arrays.asList(ButtonState.PRESS);
		
		this.viewWrapper.onKeyState(charTyped, keyTyped, states);
		this.elementWrapper.onKeyState(charTyped, keyTyped, states);
	}

	@Override
	protected final void mouseClicked(int mouseX, int mouseY, int mouseButtonIndex)
	{
		List<MouseButton> buttons = Arrays.asList(MouseButton.fromIndex(mouseButtonIndex));
		List<ButtonState> states = Arrays.asList(ButtonState.PRESS);

		this.viewWrapper.onMouseState(INPUT, buttons, states);
		this.elementWrapper.onMouseState(INPUT, buttons, states);
	}

	@Override
	public final void mouseReleased(int mouseX, int mouseY, int mouseButtonIndex)
	{
		List<MouseButton> buttons = Arrays.asList(MouseButton.fromIndex(mouseButtonIndex));
		List<ButtonState> states = Arrays.asList(ButtonState.RELEASED);

		this.viewWrapper.onMouseState(INPUT, buttons, states);
		this.elementWrapper.onMouseState(INPUT, buttons, states);
	}
	
	@Override
	protected final void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
	{
		List<MouseButton> buttons = Arrays.asList(MouseButton.fromIndex(clickedMouseButton));
		List<ButtonState> states = Arrays.asList(ButtonState.DOWN, ButtonState.MOVING);

		this.viewWrapper.onMouseState(INPUT, buttons, states);
		this.elementWrapper.onMouseState(INPUT, buttons, states);
	}

	@Override
	public final void drawScreen(int mouseX, int mouseY, float partialTick)
	{
		int scrollDifference = Mouse.getDWheel();
		
		if (scrollDifference != 0)
		{
			this.viewWrapper.onMouseScroll(INPUT, scrollDifference);
			this.elementWrapper.onMouseScroll(INPUT, scrollDifference);
		}
		
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
