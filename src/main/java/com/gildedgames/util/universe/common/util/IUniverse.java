package com.gildedgames.util.universe.common.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IUniverse
{
	
	String getUnlocalizedName();
	
	@SideOnly(Side.CLIENT)
	ResourceLocation getPreviewTexture();
	
	void onTravelTo(EntityPlayer player, boolean hasTravelBefore);
	
	void onTravelFrom(EntityPlayer player, boolean hasLeftBefore);
	
}
