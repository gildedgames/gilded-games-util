package com.gildedgames.util.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.core.gui.util.decorators.UIScreenMC;
import com.gildedgames.util.core.gui.viewing.UIFrameMC;
import com.gildedgames.util.tab.common.tab.TabBackpack;

public class TestTab extends TabBackpack
{
	
	private UIBasic ui;
	
	@Override
	public void onOpen(EntityPlayer player)
	{
		this.ui = new UIScreenMC(new TestUI(null));
		
		Minecraft.getMinecraft().displayGuiScreen(new UIFrameMC(this.ui));
	}

	@Override
	public boolean isTabValid(GuiScreen gui)
	{
		if (gui instanceof UIFrameMC)
		{
			UIFrameMC wrapper = (UIFrameMC)gui;
			
			return wrapper.getFramedElement() != null && wrapper.getFramedElement().getClass() == this.ui.getClass();
		}
		
		return false;
	}
	
}