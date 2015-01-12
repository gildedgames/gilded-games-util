package com.gildedgames.util.worldhook;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.worldhook.common.IWorldHookPool;
import com.gildedgames.util.worldhook.common.world.IWorld;
import com.gildedgames.util.worldhook.common.world.IWorldFactory;
import com.gildedgames.util.worldhook.common.world.WorldMinecraftFactory;

public class WorldServices
{

	private List<IWorldHookPool<?>> worldPools;

	private List<IWorld> worldWrappers = new ArrayList<IWorld>();

	private final IWorldFactory<?> wrapperFactory = new WorldMinecraftFactory();//Change to use different IWorld wrappers

	private final boolean isRemote;

	public WorldServices(boolean isRemote)
	{
		this.isRemote = isRemote;
	}

	public List<IWorldHookPool<?>> getPools()
	{
		if (this.worldPools == null)
		{
			this.worldPools = new ArrayList<IWorldHookPool<?>>();
		}

		return this.worldPools;
	}

	public void registerWorldPool(IWorldHookPool<?> worldPool)
	{
		this.getPools().add(worldPool);
	}

	public IWorld getWrapper(int dimId)
	{
		for (IWorld wrapper : this.worldWrappers)
		{
			if (wrapper.isWrapperFor(dimId, this.isRemote))
			{
				return wrapper;
			}
		}
		return this.wrapperFactory.create(dimId, this.isRemote);
	}

}
