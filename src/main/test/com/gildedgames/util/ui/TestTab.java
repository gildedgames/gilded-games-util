package com.gildedgames.util.ui;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.tab.common.tab.TabBackpack;

public class TestTab extends TabBackpack
{
	@Override
	public void onOpen(EntityPlayer player)
	{
		UtilCore.locate().view(new TestUI());
	}

	@Override
	public List getGuiClasses()
	{
		return Arrays.asList(TestUI.class);
	}
}
