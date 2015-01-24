package com.gildedgames.util.universe.common.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public interface IUniverse
{
	
	String getUnlocalizedName();
	
	@SideOnly(Side.CLIENT)
	ResourceLocation getPreviewTexture();
	
	void onTravelTo(EntityPlayer player, boolean hasTravelBefore);
	
	void onTravelFrom(EntityPlayer player, boolean hasLeftBefore);
	
}
