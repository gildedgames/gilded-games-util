package com.gildedgames.util.core.gui;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.tab.common.tab.TabBackpack;

public class TestTab extends TabBackpack
{
	@Override
	public void onOpen(EntityPlayer player)
	{
		Minecraft.getMinecraft().displayGuiScreen(new TestUI(null));
	}

	@Override
	public List getGuiClasses()
	{
		return Arrays.asList(TestUI.class);
	}
}
