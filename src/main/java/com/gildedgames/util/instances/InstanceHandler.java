package com.gildedgames.util.instances;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.WorldServer;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.core.nbt.NBTHelper;

public class InstanceHandler<T extends Instance> implements NBT
{

	private final Map<Integer, T> instances = new HashMap<Integer, T>();

	private int maxX = -10000000;

	private int nextFreeId = 0;

	private final int dimensionId;

	private InstanceFactory<T> factory;

	public InstanceHandler(int dimensionId, InstanceFactory<T> factory)
	{
		this.dimensionId = dimensionId;
		this.factory = factory;
	}

	public T getInstance(int id)
	{
		return this.instances.get(id);
	}

	public T createNew()
	{
		WorldServer world = MinecraftServer.getServer().worldServerForDimension(this.dimensionId);
		T created = this.factory.createInstance(world, this.maxX, this.nextFreeId, this);
		if (created.getId() != this.nextFreeId)
		{
			throw new IllegalStateException("Some instance did not save instance ID correctly. This is a programmers error!");
		}
		created.generate();
		AxisAlignedBB boundingBox = created.getBoundingBox();
		if (boundingBox.minX < this.maxX)
		{
			throw new IllegalStateException("Some instance went under the minX given. This is a programmers error!");
		}
		this.maxX = (int) (boundingBox.maxX + this.factory.distanceBetweenInstances());
		this.instances.put(this.nextFreeId, created);
		this.nextFreeId++;
		return created;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		output.setInteger("nextFreeId", this.nextFreeId);
		output.setInteger("maxX", this.maxX);
		NBTTagList tagList = new NBTTagList();
		for (T instance : this.instances.values())
		{
			NBTTagCompound newTag = new NBTTagCompound();
			newTag.setInteger("id", instance.getId());
			instance.write(newTag);
			tagList.appendTag(newTag);
		}
		output.setTag("instances", tagList);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		this.nextFreeId = input.getInteger("nextFreeId");
		this.maxX = input.getInteger("maxX");
		WorldServer world = MinecraftServer.getServer().worldServerForDimension(this.dimensionId);
		for (NBTTagCompound tag : NBTHelper.getIterator(input, "instances"))
		{
			int id = tag.getInteger("id");
			T instance = this.factory.createInstance(world, -1, id, this);
			instance.read(tag);
			this.instances.put(id, instance);
		}
	}

	public int getInstancesSize()
	{
		return this.instances.size();
	}

	public Collection<T> getInstances()
	{
		return this.instances.values();
	}

}
