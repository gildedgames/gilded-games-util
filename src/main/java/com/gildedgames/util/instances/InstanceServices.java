package com.gildedgames.util.instances;

import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.PlayerHookPool;

public class InstanceServices
{
	private IPlayerHookPool<PlayerInstances> players;

	private final Side side;

	public InstanceServices(Side side)
	{
		this.side = side;
	}

	public IPlayerHookPool<PlayerInstances> getPlayers()
	{
		if (this.players == null)
		{
			this.players = new PlayerHookPool<PlayerInstances>("instances", new PlayerInstancesFactory(), this.side);
		}

		return this.players;
	}
}
