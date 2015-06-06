package com.gildedgames.util.core.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.core.gui.util.decorators.DarkenedBackground;
import com.gildedgames.util.core.gui.viewing.MinecraftUIViewer;
import com.gildedgames.util.core.gui.viewing.MinecraftUIWrapper;
import com.gildedgames.util.tab.common.tab.TabBackpack;
import com.gildedgames.util.ui.UICore;
import com.gildedgames.util.ui.common.BasicUI;
import com.gildedgames.util.ui.common.UIFrame;

public class TestTab extends TabBackpack
{
	
	private BasicUI ui;
	
	@Override
	public void onOpen(EntityPlayer player)
	{
		this.ui = new DarkenedBackground(new TestUI(null));
		
		UICore.locate().open("test", new UIFrame(this.ui), MinecraftUIViewer.instance());
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
			
			return wrapper.getFramedElement() != null && wrapper.getFramedElement().getClass() == this.ui.getClass();
		}
		
		return false;
	}
	
}