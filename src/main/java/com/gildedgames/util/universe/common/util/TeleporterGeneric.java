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
    public void placeInPortal(Entity entity, double d0, double d1, double d2, float d3)
    {
		
    }
	
	@Override
	public boolean placeInExistingPortal(Entity entity, double d0, double d1, double d2, float d3)
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
