package com.gildedgames.util.ui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.gui.UIElementWrapperMinecraft;
import com.gildedgames.util.tab.common.tab.TabBackpack;

public class TestTab extends TabBackpack
{
	
	@Override
	public void onOpen(EntityPlayer player)
	{
		UtilCore.locate().view(new TestUI());
	}

	@Override
	public boolean isTabValid(GuiScreen gui)
	{
		if (gui instanceof UIElementWrapperMinecraft)
		{
			UIElementWrapperMinecraft wrapper = (UIElementWrapperMinecraft)gui;
			
			return wrapper.getView().getClass() == TestUI.class;
		}
		
		return false;
	}
	
}
