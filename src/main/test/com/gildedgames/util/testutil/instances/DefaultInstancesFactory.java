package com.gildedgames.util.testutil.instances;

import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldServer;

import com.gildedgames.util.instances.InstanceFactory;
import com.gildedgames.util.instances.InstanceHandler;

public class DefaultInstancesFactory implements InstanceFactory<DefaultInstance>
{

	@Override
	public DefaultInstance createInstance(int dimId, InstanceHandler<DefaultInstance> instanceHandler)
	{
		return new DefaultInstance(dimId, instanceHandler);
	}

	@Override
	public int providerId()
	{
		return 5;
	}

	@Override
	public Class<? extends WorldProvider> getProviderType()
	{
		return WorldProviderSurface.class;
	}

	@Override
	public Teleporter getTeleporter(WorldServer worldIn)
	{
		return new Teleporter(worldIn);
	}

}
