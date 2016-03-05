package com.gildedgames.util.modules.entityhook.api;

import com.gildedgames.util.modules.entityhook.impl.hooks.EntityHook;

import java.util.Collection;

public interface EntityHookServices
{
	/**
	 * Registers an {@link IEntityHookProvider}.
	 *
	 * The provider is responsible for creating and managing hooks.
	 * @param provider The provider to register.
	 */
	void registerHookProvider(IEntityHookProvider<? extends EntityHook> provider);

	/**
	 * @param id The hook provider's unique id
	 * @return Returns the hook provider assigned to {@param id}
	 */
	IEntityHookProvider<EntityHook> getProviderFromId(String id);

	/**
	 * @return Returns a collection of the currently registered hook providers.
	 */
	Collection<IEntityHookProvider<EntityHook>> getEntityHookProviders();
}
