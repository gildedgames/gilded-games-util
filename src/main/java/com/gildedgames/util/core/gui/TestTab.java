package com.gildedgames.util.core.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.core.gui.util.decorators.MinecraftGui;
import com.gildedgames.util.core.gui.viewing.MinecraftUIViewer;
import com.gildedgames.util.core.gui.viewing.MinecraftUIWrapper;
import com.gildedgames.util.tab.common.tab.TabBackpack;
import com.gildedgames.util.ui.UiCoreTemp;
import com.gildedgames.util.ui.common.GuiFrame;

public class TestTab extends TabBackpack
{

	private GuiFrame gui;

	@Override
	public void onOpen(EntityPlayer player)
	{
		this.gui = new MinecraftGui(new TestGui());

		UiCoreTemp.locate().open("test", this.gui, MinecraftUIViewer.instance());
	}

	@Override
	public void onClose(EntityPlayer player)
	{
		super.onClose(player);

		UiCoreTemp.locate().close(MinecraftUIViewer.instance());
	}

	@Override
	public boolean isTabValid(GuiScreen gui)
	{
		if (gui instanceof MinecraftUIWrapper)
		{
			MinecraftUIWrapper wrapper = (MinecraftUIWrapper) gui;

			return wrapper.getFrame() != null && wrapper.getFrame().getClass() == this.gui.getClass();
		}

		return false;
	}

}
