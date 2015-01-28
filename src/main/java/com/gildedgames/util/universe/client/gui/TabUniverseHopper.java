package com.gildedgames.util.universe.client.gui;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.universe.client.gui.util.TabGeneric;
import com.gildedgames.util.universe.common.UniverseAPI;

public class TabUniverseHopper extends TabGeneric
{
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(UtilCore.MOD_ID, "textures/gui/tab_icons/universeHopper.png");

	@Override
	public String getUnlocalizedName()
	{
		return "tab.universeHopper.name";
	}

	@Override
	public List getGuiClasses()
	{
		return Arrays.asList(GuiInventory.class, GuiContainerCreative.class, GuiUniverseHopper.class);
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
