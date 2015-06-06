package com.gildedgames.util.core.gui.viewing;

import net.minecraft.client.Minecraft;

import com.gildedgames.util.ui.common.UIFrame;
import com.gildedgames.util.ui.common.UIViewer;
import com.gildedgames.util.ui.input.InputProvider;

public class MinecraftUIViewer implements UIViewer
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
	public void open(UIFrame frame)
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
