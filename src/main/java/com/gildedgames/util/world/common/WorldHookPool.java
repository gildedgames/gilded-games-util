package com.gildedgames.util.world.common;

import com.gildedgames.util.world.WorldModule;
import com.gildedgames.util.world.common.world.IWorld;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WorldHookPool<W extends IWorldHook> implements IWorldHookPool<W>
{

	private final IWorldHookFactory<W> factory;

	private List<W> hooks = new ArrayList<>();

	private final String poolName;

	public WorldHookPool(IWorldHookFactory<W> factory, String poolName)
	{
		this.factory = factory;
		this.poolName = poolName;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		final NBTTagList tagList = new NBTTagList();

		for (final W entry : this.hooks)
		{
			final NBTTagCompound newTag = new NBTTagCompound();
			final IWorld world = entry.getWorld();
			newTag.setInteger("dimId", world.getDimensionID());
			entry.write(newTag);
			tagList.appendTag(newTag);
		}

		output.setTag("worlds", tagList);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		final NBTTagList tagList = input.getTagList("worlds", 10);

		int count = tagList.tagCount();
		for (int i = 0; i < count; i++)
		{
			final NBTTagCompound newTag = tagList.getCompoundTagAt(i);
			final int dimId = newTag.getInteger("dimId");
			final W hook = this.factory.create(this.factory.getWorldFor(dimId));
			hook.read(newTag);
			this.hooks.add(hook);
		}
	}

	@Override
	public W get(World world)
	{
		for (final W w : this.hooks)
		{
			if (w.getWorld().isWrapperFor(world.provider.getDimensionId(), world.isRemote))
			{
				return w;
			}
		}

		return this.createHook(WorldModule.get(world));
	}

	@Override
	public W get(IWorld world)
	{
		for (final W w : this.hooks)
		{
			if (w.getWorld().equals(world))
			{
				return w;
			}
		}

		return this.createHook(world);
	}

	@Override
	public Collection<W> getWorlds()
	{
		return new ArrayList<>(this.hooks);
	}

	@Override
	public void clear()
	{
		this.hooks = new ArrayList<>();
	}

	private W createHook(IWorld world)
	{
		W hook = this.factory.create(world);
		this.hooks.add(hook);

		return hook;
	}

	@Override
	public String getPoolName()
	{
		return this.poolName;
	}

}
