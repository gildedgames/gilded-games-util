package com.gildedgames.util.core.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.core.PaynUI;
import com.gildedgames.util.core.gui.util.decorators.MinecraftDecorator;
import com.gildedgames.util.core.gui.viewing.MinecraftUIViewer;
import com.gildedgames.util.core.gui.viewing.MinecraftUIWrapper;
import com.gildedgames.util.tab.common.tab.TabBackpack;
import com.gildedgames.util.ui.UICore;
import com.gildedgames.util.ui.common.UIFrame;

public class TestTab extends TabBackpack
{
	
	private UIFrame ui;
	
	@Override
	public void onOpen(EntityPlayer player)
	{
		this.ui = new MinecraftDecorator(new PaynUI());
		
		UICore.locate().open("test", this.ui, MinecraftUIViewer.instance());
	}
	
	@Override
	public void onClose(EntityPlayer player)
	{
		super.onClose(player);
		
		UICore.locate().close(MinecraftUIViewer.instance());
	}

	@Override
	public boolean isTabValid(GuiScreen gui)
	{
		if (gui instanceof MinecraftUIWrapper)
		{
			MinecraftUIWrapper wrapper = (MinecraftUIWrapper)gui;
			
			return wrapper.getFrame() != null && wrapper.getFrame().getClass() == this.ui.getClass();
		}
		
		return false;
	}
	
}