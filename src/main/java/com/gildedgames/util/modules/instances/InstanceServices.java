package com.gildedgames.util.modules.instances;

import com.gildedgames.util.modules.entityhook.api.IEntityHookPool;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InstanceServices
{
	private List<InstanceHandler<?>> instances;

	private final Side side;

	public InstanceServices(Side side)
	{
		this.side = side;
	}

	public IEntityHookPool<PlayerInstances> getPlayerHooks()
	{
		return PlayerInstances.PROVIDER.getPool();
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
