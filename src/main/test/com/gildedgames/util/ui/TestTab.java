package com.gildedgames.util.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.core.gui.util.decorators.UIScreenMC;
import com.gildedgames.util.core.gui.viewing.UIViewerMC;
import com.gildedgames.util.tab.common.tab.TabBackpack;

public class TestTab extends TabBackpack
{
	
	@Override
	public void onOpen(EntityPlayer player)
	{
		Minecraft.getMinecraft().displayGuiScreen(new UIViewerMC(new UIScreenMC(new TestUI(null))));
	}

	@Override
	public boolean isTabValid(GuiScreen gui)
	{
		if (gui instanceof UIViewerMC)
		{
			UIViewerMC wrapper = (UIViewerMC)gui;
			
			return wrapper.getView() != null && wrapper.getView().getClass() == TestUI.class;
		}
		
		return false;
	}
	
}