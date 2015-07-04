package com.gildedgames.util.core.gui.viewing;

import net.minecraft.client.Minecraft;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.common.GuiViewer;
import com.gildedgames.util.ui.input.InputProvider;

public class MinecraftUIViewer implements GuiViewer
{
	
	private final static MinecraftUIViewer INSTANCE = new MinecraftUIViewer();
	
	private final static MinecraftInputProvider INPUT = new MinecraftInputProvider(Minecraft.getMinecraft());
	
	private Minecraft mc = Minecraft.getMinecraft();
	
	private MinecraftUIViewer()
	{
		
	}
	
	public static MinecraftUIViewer instance()
	{
		return MinecraftUIViewer.INSTANCE;
	}

	@Override
	public void open(GuiFrame frame)
	{
		this.mc.displayGuiScreen(new MinecraftUIWrapper(frame));
	}

	@Override
	public void close()
	{
		this.mc.displayGuiScreen(null);
	}

	@Override
	public InputProvider getInputProvider()
	{
		return MinecraftUIViewer.INPUT;
	}

}
