package com.gildedgames.util.universe.common.universe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.universe.common.util.IUniverse;

public class UniverseMinecraft implements IUniverse
{
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(UtilCore.MOD_ID, "textures/gui/universe_hopper/preview_minecraft.png");

	@Override
	public String getUnlocalizedName()
	{
		return "universe.minecraft.name";
	}

	@Override
	public ResourceLocation getPreviewTexture()
	{
		return TEXTURE;
	}

	@Override
	public void onTravelTo(EntityPlayer player, boolean hasTravelBefore)
	{
		//player.setPositionAndUpdate(0, 100, 0);
		//UniverseCore.teleportToDimension((EntityPlayerMP) player, 0);
	}

	@Override
	public void onTravelFrom(EntityPlayer player, boolean hasLeftBefore)
	{
		
	}

}
