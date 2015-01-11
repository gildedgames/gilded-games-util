package com.gildedgames.util.worldhook.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import com.gildedgames.util.worldhook.WorldHookCore;
import com.gildedgames.util.worldhook.common.world.IWorld;

public class WorldPool<W extends IWorldHook> implements IWorldPool<W>
{

	private final IWorldFactory<W> factory;

	private List<W> hooks = new ArrayList<W>();

	private String poolName;

	public WorldPool(IWorldFactory<W> factory, String poolName)
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
		}

		output.setTag("worlds", tagList);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		final NBTTagList tagList = input.getTagList("worlds", 9);

		for (int i = 0; i < tagList.tagCount(); i++)
		{
			final NBTTagCompound newTag = tagList.getCompoundTagAt(i);
			final int dimId = newTag.getInteger("dimId");
			final W hook = this.factory.create(WorldHookCore.getWrapper(dimId));
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

		return this.createHook(WorldHookCore.getWrapper(world));
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
		return new ArrayList<W>(this.hooks);
	}

	@Override
	public void clear()
	{
		this.hooks = new ArrayList<W>();
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
