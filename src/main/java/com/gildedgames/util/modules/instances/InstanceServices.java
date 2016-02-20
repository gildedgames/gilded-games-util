package com.gildedgames.util.modules.instances;

import com.gildedgames.util.modules.player.common.IPlayerHookPool;
import com.gildedgames.util.modules.player.common.PlayerHookPool;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InstanceServices
{
	private IPlayerHookPool<PlayerInstances> players;

	private List<InstanceHandler<?>> instances;

	private final Side side;

	public InstanceServices(Side side)
	{
		this.side = side;
	}

	public IPlayerHookPool<PlayerInstances> getPlayers()
	{
		if (this.players == null)
		{
			this.players = new PlayerHookPool<>("instances", new PlayerInstancesFactory(), this.side);
		}

		return this.players;
	}

	public Collection<InstanceHandler<?>> getHandlers()
	{
		if (this.instances == null)
		{
			this.instances = new ArrayList<>();
		}
		return this.instances;
	}

	protected void addHandler(InstanceHandler<?> handler)
	{
		this.getHandlers().add(handler);
	}
}
