package com.gildedgames.util.instances;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import com.gildedgames.util.core.nbt.NBT;

public abstract class Instance implements NBT
{
	/**
	 * Dynamic creation to preserve memory.
	 * When many instances on the server exist,
	 * all these arraylists may get crowdy. 
	 */
	private List<PlayerInstances> playersIn;

	public abstract BlockPos getSpawnPos(PlayerInstances player);

	private AxisAlignedBB boundingBox;

	private List<PlayerInstances> playersIn()
	{
		if (this.playersIn == null)
		{
			this.playersIn = new ArrayList<PlayerInstances>();
		}
		return this.playersIn;
	}

	public Collection<PlayerInstances> getPlayersIn()
	{
		return this.playersIn();
	}
}
