package com.gildedgames.util.modules.instances;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class InstanceTeleporter extends Teleporter
{

	public InstanceTeleporter(WorldServer worldIn)
	{
		super(worldIn);
	}

	@Override
	public void placeInPortal(Entity entityIn, float rotationYaw)
	{
	}

	@Override
	public boolean placeInExistingPortal(Entity entityIn, float rotationYaw)
	{
		return false;
	}

	@Override
	public boolean makePortal(Entity p_85188_1_)
	{
		return false;
	}

	@Override
	public void removeStalePortalLocations(long worldTime)
	{
	}
}
