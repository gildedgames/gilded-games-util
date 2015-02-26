package com.gildedgames.util.universe.client.gui;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.tab.common.util.TabGeneric;
import com.gildedgames.util.universe.common.UniverseAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Arrays;

public class TabUniverseHopper extends TabGeneric
{
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(UtilCore.MOD_ID, "textures/gui/tab_icons/universeHopper.png");

	@Override
	public String getUnlocalizedName()
	{
		return "tab.universeHopper.name";
	}

	@Override
	public boolean isTabValid(GuiScreen gui)
	{
		return GuiUniverseHopper.class.equals(gui.getClass());
	}

	@Override
	public void onOpen(EntityPlayer player)
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiUniverseHopper());
	}

	@Override
	public void onClose(EntityPlayer player)
	{
		
	}
	
	@Override
	public Container getCurrentContainer(EntityPlayer player, World world, int posX, int posY, int posZ)
	{
		return null;
	}

	@Override
	public boolean isEnabled()
	{
		return UniverseAPI.instance().getUniverses().size() > 1;
	}

	@Override
	public boolean isRemembered()
	{
		return false;
	}

	@Override
	public ResourceLocation getIconTexture()
	{
		return TEXTURE;
	}

}
