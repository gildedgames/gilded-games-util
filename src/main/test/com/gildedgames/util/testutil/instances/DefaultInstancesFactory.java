package com.gildedgames.util.testutil.instances;

import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;

import com.gildedgames.util.modules.instances.InstanceFactory;
import com.gildedgames.util.modules.instances.InstanceHandler;

public class DefaultInstancesFactory implements InstanceFactory<DefaultInstance>
{
	
	private int providerId;
	
	private Class<? extends WorldProvider> provider;
	
	public DefaultInstancesFactory(int providerId, Class<? extends WorldProvider> provider)
	{
		this.providerId = providerId;
		this.provider = provider;
	}

	@Override
	public DefaultInstance createInstance(int dimId, InstanceHandler<DefaultInstance> instanceHandler)
	{
		return new DefaultInstance(dimId, instanceHandler);
	}

	@Override
	public int providerId()
	{
		return this.providerId;
	}

	@Override
	public Class<? extends WorldProvider> getProviderType()
	{
		return this.provider;
	}

	@Override
	public Teleporter getTeleporter(WorldServer worldIn)
	{
		return new Teleporter(worldIn);
	}

}
