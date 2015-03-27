package com.gildedgames.util.core.gui.viewing;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.input.Mouse;

import com.gildedgames.util.ui.UIFrame;
import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.KeyboardInput;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInput;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.input.MouseMotion;

public final class UIViewerMC extends GuiScreen
{

	protected final static GraphicsMC GRAPHICS = new GraphicsMC(Minecraft.getMinecraft());

	protected final static InputProviderMC INPUT = new InputProviderMC(Minecraft.getMinecraft());

	private final UIFrame frame;
	
	private boolean hasInit;

	public UIViewerMC(UIFrame frame)
	{
		this.frame = frame;
	}
	
	public UIView getView()
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

		if (!this.hasInit)
		{
			this.frame.onInit(INPUT);
			
			this.hasInit = true;
		}
		else
		{
			this.frame.onResolutionChange(INPUT);
		}
	}

	@Override
	protected final void keyTyped(char charTyped, int keyTyped)
	{
		KeyboardInputPool pool = new KeyboardInputPool(new KeyboardInput(charTyped, keyTyped, ButtonState.PRESSED));
		
		this.frame.onKeyboardInput(pool);
	}

	@Override
	protected final void mouseClicked(int mouseX, int mouseY, int mouseButtonIndex)
	{
		MouseInputPool pool = new MouseInputPool(new MouseInput(MouseButton.fromIndex(mouseButtonIndex), ButtonState.PRESSED));

		this.frame.onMouseInput(INPUT, pool);
	}

	@Override
	public final void mouseReleased(int mouseX, int mouseY, int mouseButtonIndex)
	{
		MouseInputPool pool = new MouseInputPool(new MouseInput(MouseButton.fromIndex(mouseButtonIndex), ButtonState.RELEASED));

		this.frame.onMouseInput(INPUT, pool);
	}
	
	@Override
	protected final void mouseClickMove(int mouseX, int mouseY, int mouseButtonIndex, long timeSinceLastClick)
	{
		MouseInputPool pool = new MouseInputPool(new MouseInput(MouseButton.fromIndex(mouseButtonIndex), ButtonState.DOWN, MouseMotion.MOVING));

		this.frame.onMouseInput(INPUT, pool);
	}

	@Override
	public final void drawScreen(int mouseX, int mouseY, float partialTick)
	{
		int scrollDifference = Mouse.getDWheel();
		
		if (scrollDifference != 0)
		{
			this.frame.onMouseScroll(INPUT, scrollDifference);
		}
		
		this.frame.draw(GRAPHICS, INPUT);
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
