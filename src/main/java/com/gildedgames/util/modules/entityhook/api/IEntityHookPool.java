package com.gildedgames.util.modules.entityhook.api;

import com.gildedgames.util.modules.entityhook.impl.hooks.EntityHook;

import java.util.Collection;
import java.util.UUID;

public interface IEntityHookPool<T extends EntityHook>
{
	/**
	 * Returns a list of hooks this provider currently has attached.
	 *
	 * @return Collection of {@link T}
	 */
	Collection<T> getAttachedHooks();

	void addHook(T hook);

	void removeHook(T hook);

	T getHook(UUID uuid);
}
