package com.gildedgames.util.modules.entityhook.impl;

import com.gildedgames.util.modules.entityhook.api.EntityHookServices;
import com.gildedgames.util.modules.entityhook.api.IEntityHookProvider;
import com.gildedgames.util.modules.entityhook.impl.hooks.EntityHook;
import com.gildedgames.util.modules.entityhook.impl.providers.PlayerHookProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityHookImpl implements EntityHookServices
{
	private final Map<String, IEntityHookProvider<EntityHook>> entityProviders = new HashMap<>();

	private final List<PlayerHookProvider> playerProviders = new ArrayList<>();

	public Collection<PlayerHookProvider> getPlayerHookProviders()
	{
		return this.playerProviders;
	}

	public Collection<IEntityHookProvider<EntityHook>> getEntityHookProviders()
	{
		return this.entityProviders.values();
	}

	public IEntityHookProvider<EntityHook> getProviderFromId(String id)
	{
		return this.entityProviders.get(id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void registerHookProvider(IEntityHookProvider<? extends EntityHook> provider)
	{
		if (this.entityProviders.containsKey(provider.getId()))
		{
			throw new IllegalArgumentException("Identifier " + provider.getId() + " already has a provider registered");
		}

		this.entityProviders.put(provider.getId(), (IEntityHookProvider<EntityHook>) provider);

		// This allows us to cache (rather than iterate) which providers need additional logic
		if (provider instanceof PlayerHookProvider)
		{
			this.playerProviders.add((PlayerHookProvider) provider);
		}
	}
}
