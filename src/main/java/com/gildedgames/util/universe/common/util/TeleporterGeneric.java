package com.gildedgames.util.universe.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterGeneric extends Teleporter
{
	
	public TeleporterGeneric(WorldServer world)
	{
		super(world);
	}

	@Override
	public boolean placeInExistingPortal(Entity entity, float rotationYaw)
	{
		return false;
	}

	@Override
	public void placeInPortal(Entity entity, float rotationYaw)
	{

	}

	@Override
    public boolean makePortal(Entity entity)
    {
		return false;
    }
	
	@Override
    public void removeStalePortalLocations(long p_85189_1_)
    {
		
    }
	
}
