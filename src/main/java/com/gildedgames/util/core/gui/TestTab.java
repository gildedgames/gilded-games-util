package com.gildedgames.util.core.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.core.gui.util.decorators.MinecraftGui;
import com.gildedgames.util.core.gui.viewing.MinecraftGuiViewer;
import com.gildedgames.util.core.gui.viewing.MinecraftGuiWrapper;
import com.gildedgames.util.tab.common.tab.TabBackpack;
import com.gildedgames.util.ui.UiCore;
import com.gildedgames.util.ui.common.GuiFrame;

public class TestTab extends TabBackpack
{

	private GuiFrame gui;

	@Override
	public void onOpen(EntityPlayer player)
	{
		this.gui = new MinecraftGui(new TestGui());

		UiCore.locate().open("test", this.gui, MinecraftGuiViewer.instance());
	}

	@Override
	public void onClose(EntityPlayer player)
	{
		super.onClose(player);

		UiCore.locate().close(MinecraftGuiViewer.instance());
	}

	@Override
	public boolean isTabValid(GuiScreen gui)
	{
		if (gui instanceof MinecraftGuiWrapper)
		{
			MinecraftGuiWrapper wrapper = (MinecraftGuiWrapper) gui;

			return wrapper.getFrame() != null && wrapper.getFrame().getClass() == this.gui.getClass();
		}

		return false;
	}

}
