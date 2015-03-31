package com.gildedgames.util.instances;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.core.nbt.NBT;

public class InstanceHandler<T extends Instance> implements NBT
{

	private final Map<Integer, T> instances = new HashMap<Integer, T>();

	private final List<Integer> sentRequest = new ArrayList<Integer>();

	private int maxX = -10000000;

	private final int dimensionId;

	public InstanceHandler(int dimensionId)
	{
		this.dimensionId = dimensionId;
	}

	public T getInstance(int id)
	{
		return this.instances.get(id);
	}

	@Override
	public void write(NBTTagCompound output)
	{

	}

	@Override
	public void read(NBTTagCompound input)
	{

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
