package com.gildedgames.util.core.gui.viewing;

import net.minecraft.client.Minecraft;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.common.GuiViewer;
import com.gildedgames.util.ui.input.InputProvider;

public class MinecraftGuiViewer implements GuiViewer
{
	
	private final static MinecraftGuiViewer INSTANCE = new MinecraftGuiViewer();
	
	private final static MinecraftInputProvider INPUT = new MinecraftInputProvider(Minecraft.getMinecraft());
	
	private Minecraft mc = Minecraft.getMinecraft();
	
	private MinecraftGuiViewer()
	{
		
	}
	
	public static MinecraftGuiViewer instance()
	{
		return MinecraftGuiViewer.INSTANCE;
	}

	@Override
	public void open(GuiFrame frame)
	{
		this.mc.displayGuiScreen(new MinecraftGuiWrapper(frame));
	}

	@Override
	public void close()
	{
		this.mc.displayGuiScreen(null);
	}

	@Override
	public InputProvider getInputProvider()
	{
		return MinecraftGuiViewer.INPUT;
	}

}
