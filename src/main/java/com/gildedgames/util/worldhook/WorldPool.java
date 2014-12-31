package com.gildedgames.util.worldhook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class WorldPool<W extends IWorldHook> implements IWorldPool<W>
{

	final IWorldFactory<W> factory;

	final List<W> hooks = new ArrayList<W>();

	public WorldPool(IWorldFactory<W> factory)
	{
		this.factory = factory;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		final NBTTagList tagList = new NBTTagList();
		
		for (final W entry : this.hooks)
		{
			final NBTTagCompound newTag = new NBTTagCompound();
			final World world = entry.getWorld();
			newTag.setInteger("dimId", world.provider.getDimensionId());
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
			final WorldServer server = MinecraftServer.getServer().worldServerForDimension(dimId);
			final W hook = this.factory.create(server);
			hook.read(newTag);
			this.hooks.add(hook);
		}
	}

	@Override
	public W get(World world)
	{
		for (final W w : this.hooks)
		{
			if (w.getWorld().equals(world))
			{
				return w;
			}
		}

		final W hook = this.factory.create(world);
		this.hooks.add(hook);

		return hook;
	}

	@Override
	public Collection<W> getWorlds()
	{
		return new ArrayList<W>(this.hooks);
	}

	@Override
	public void addHook(World world)
	{
		this.hooks.add(this.factory.create(world));
	}
}
