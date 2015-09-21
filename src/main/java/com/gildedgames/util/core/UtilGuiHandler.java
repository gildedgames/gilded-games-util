package com.gildedgames.util.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import com.gildedgames.util.universe.client.gui.GuiUniverseHopper;

public class UtilGuiHandler implements IGuiHandler
{
	
	public static final int hopUniverseID = 0;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (!player.worldObj.isRemote)
		{
			return null;
		}

		switch (ID)
		{
			case UtilGuiHandler.hopUniverseID:
				return new GuiUniverseHopper();
		}

		return null;
	}

}
