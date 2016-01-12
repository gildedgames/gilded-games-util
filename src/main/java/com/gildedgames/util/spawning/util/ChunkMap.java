package com.gildedgames.util.spawning.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.util.LongHashMap;
import net.minecraft.world.ChunkCoordIntPair;

public class ChunkMap<T>
{
	private LongHashMap<T> map = new LongHashMap<>();

	private List<T> values = new ArrayList<>();

	public int size()
	{
		return this.map.getNumHashElements();
	}

	public boolean isEmpty()
	{
		return this.map.getNumHashElements() == 0;
	}

	public boolean containsKey(int chunkX, int chunkZ)
	{
		return this.map.containsItem(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ));
	}

	public T get(int chunkX, int chunkZ)
	{
		return this.map.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ));
	}

	public T put(int chunkX, int chunkZ, T value)
	{
		long hash = ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ);
		T old = this.map.getValueByKey(hash);
		this.map.add(hash, value);
		this.values.add(value);
		return old;
	}

	public T remove(int chunkX, int chunkZ)
	{
		T ob = this.map.remove(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ));
		this.values.remove(ob);
		return ob;
	}

	public Collection<T> getValues()
	{
		return this.values;
	}

}
