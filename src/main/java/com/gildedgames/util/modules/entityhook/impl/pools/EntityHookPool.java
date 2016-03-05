package com.gildedgames.util.modules.entityhook.impl.pools;

import com.gildedgames.util.modules.entityhook.api.IEntityHookPool;
import com.gildedgames.util.modules.entityhook.impl.hooks.EntityHook;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class EntityHookPool<T extends EntityHook> implements IEntityHookPool<T>
{
	private final HashMap<UUID, T> loaded = new HashMap<>();

	@Override
	public Collection<T> getAttachedHooks()
	{
		return this.loaded.values();
	}

	@Override
	public void addHook(T hook)
	{
		this.loaded.put(hook.getUniqueId(), hook);
	}

	@Override
	public void removeHook(T hook)
	{
		this.loaded.remove(hook.getUniqueId());
	}

	public T getHook(UUID uuid)
	{
		return this.loaded.get(uuid);
	}
}
