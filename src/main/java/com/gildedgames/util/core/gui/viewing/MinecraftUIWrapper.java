package com.gildedgames.util.core.gui.viewing;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.input.Mouse;

import com.gildedgames.util.ui.common.UIFrame;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.data.UIContainerMutable;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.KeyboardInput;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInput;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.input.MouseMotion;
import com.gildedgames.util.ui.util.UIViewerHelper;

public final class MinecraftUIWrapper extends GuiScreen
{

	protected final static MinecraftGraphics2D GRAPHICS = new MinecraftGraphics2D(Minecraft.getMinecraft());
	
	protected final static MinecraftInputProvider INPUT = new MinecraftInputProvider(Minecraft.getMinecraft());

	private final UIFrame frame;
	
	private final UIContainerMutable frameHolder = new UIContainerMutable();
	
	private boolean hasInit;

	public MinecraftUIWrapper(UIFrame frame)
	{
		this.frame = frame;
	}
	
	public UIFrame getFrame()
	{
		return this.frame;
	}
	
	@Override
	public final void initGui()
	{
		this.updateScreen();

		final ScaledResolution resolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);

		this.width = resolution.getScaledWidth();
		this.height = resolution.getScaledHeight();

		INPUT.setScreen(this.width, this.height);
		INPUT.setScaleFactor(resolution.getScaleFactor());
		
		this.frameHolder.clear();
		
		this.frameHolder.setElement("frame", this.frame);

		if (!this.hasInit)
		{
			UIViewerHelper.processInit(this.frameHolder, INPUT);
			
			this.hasInit = true;
		}
		else
		{
			UIViewerHelper.processResolutionChange(this.frameHolder, INPUT);
		}
	}

	@Override
	protected final void keyTyped(char charTyped, int keyTyped)
	{
		KeyboardInputPool pool = new KeyboardInputPool(new KeyboardInput(charTyped, keyTyped, ButtonState.PRESSED));
		
		UIViewerHelper.processKeyboardInput(this.frameHolder, pool);
	}

	@Override
	protected final void mouseClicked(int mouseX, int mouseY, int mouseButtonIndex)
	{
		MouseInputPool pool = new MouseInputPool(new MouseInput(MouseButton.fromIndex(mouseButtonIndex), ButtonState.PRESSED));

		UIViewerHelper.processMouseInput(this.frameHolder, INPUT, pool);
	}

	@Override
	public final void mouseReleased(int mouseX, int mouseY, int mouseButtonIndex)
	{
		MouseInputPool pool = new MouseInputPool(new MouseInput(MouseButton.fromIndex(mouseButtonIndex), ButtonState.RELEASED));

		UIViewerHelper.processMouseInput(this.frameHolder, INPUT, pool);
	}
	
	@Override
	protected final void mouseClickMove(int mouseX, int mouseY, int mouseButtonIndex, long timeSinceLastClick)
	{
		MouseInputPool pool = new MouseInputPool(new MouseInput(MouseButton.fromIndex(mouseButtonIndex), ButtonState.DOWN, MouseMotion.MOVING));

		UIViewerHelper.processMouseInput(this.frameHolder, INPUT, pool);
	}

	@Override
	public final void drawScreen(int mouseX, int mouseY, float partialTick)
	{
		int scrollDifference = Mouse.getDWheel();
		
		if (scrollDifference != 0)
		{
			UIViewerHelper.processMouseScroll(this.frameHolder, INPUT, scrollDifference);
		}

		UIViewerHelper.processDraw(this.frameHolder, GRAPHICS, INPUT);
	}
	
	public final void tick(TickInfo info)
	{
		UIViewerHelper.processTick(this.frameHolder, INPUT, info);
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
