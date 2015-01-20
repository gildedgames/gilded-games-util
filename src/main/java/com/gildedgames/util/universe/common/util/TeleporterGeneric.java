package com.gildedgames.util.universe.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterGeneric extends Teleporter
{
	
	public TeleporterGeneric(WorldServer worldIn)
	{
		super(worldIn);
	}

	@Override
    public void func_180266_a(Entity p_180266_1_, float p_180266_2_)
    {
		
    }
	
	@Override
    public boolean func_180620_b(Entity p_180620_1_, float p_180620_2_)
    {
		return false;
    }
	
	@Override
    public boolean makePortal(Entity p_85188_1_)
    {
		return false;
    }
	
	@Override
    public void removeStalePortalLocations(long p_85189_1_)
    {
		
    }
	
}
