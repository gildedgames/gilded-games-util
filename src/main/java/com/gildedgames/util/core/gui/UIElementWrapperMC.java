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
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.KeyEvent;
import com.gildedgames.util.ui.input.KeyEventPool;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseEvent;
import com.gildedgames.util.ui.input.MouseEventPool;
import com.gildedgames.util.ui.input.MouseMotion;
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
		KeyEventPool pool = new KeyEventPool(new KeyEvent(charTyped, keyTyped, ButtonState.PRESSED));
		
		this.viewWrapper.onKeyEvent(pool);
		this.elementWrapper.onKeyEvent(pool);
	}

	@Override
	protected final void mouseClicked(int mouseX, int mouseY, int mouseButtonIndex)
	{
		MouseEventPool pool = new MouseEventPool(new MouseEvent(MouseButton.fromIndex(mouseButtonIndex), ButtonState.PRESSED));

		this.viewWrapper.onMouseEvent(INPUT, pool);
		this.elementWrapper.onMouseEvent(INPUT, pool);
	}

	@Override
	public final void mouseReleased(int mouseX, int mouseY, int mouseButtonIndex)
	{
		MouseEventPool pool = new MouseEventPool(new MouseEvent(MouseButton.fromIndex(mouseButtonIndex), ButtonState.RELEASED));

		this.viewWrapper.onMouseEvent(INPUT, pool);
		this.elementWrapper.onMouseEvent(INPUT, pool);
	}
	
	@Override
	protected final void mouseClickMove(int mouseX, int mouseY, int mouseButtonIndex, long timeSinceLastClick)
	{
		MouseEventPool pool = new MouseEventPool(new MouseEvent(MouseButton.fromIndex(mouseButtonIndex), ButtonState.DOWN, MouseMotion.MOVING));

		this.viewWrapper.onMouseEvent(INPUT, pool);
		this.elementWrapper.onMouseEvent(INPUT, pool);
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
