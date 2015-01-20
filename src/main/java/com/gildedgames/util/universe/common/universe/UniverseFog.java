package com.gildedgames.util.universe.common.universe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.universe.common.util.IUniverse;

public class UniverseFog implements IUniverse
{
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(UtilCore.MOD_ID, "textures/gui/universe_hopper/preview_fog.png");

	@Override
	public String getUnlocalizedName()
	{
		return "universe.thefog.name";
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
		//UniverseCore.teleportToDimension((EntityPlayerMP) player, -1);
	}

	@Override
	public void onTravelFrom(EntityPlayer player, boolean hasLeftBefore)
	{
		
	}

}
