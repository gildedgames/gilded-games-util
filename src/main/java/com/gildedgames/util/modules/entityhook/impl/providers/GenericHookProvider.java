package com.gildedgames.util.modules.entityhook.impl.providers;

import com.gildedgames.util.modules.entityhook.api.IEntityHookFactory;
import com.gildedgames.util.modules.entityhook.api.IEntityHookPool;
import com.gildedgames.util.modules.entityhook.api.IEntityHookProvider;
import com.gildedgames.util.modules.entityhook.impl.hooks.EntityHook;
import com.gildedgames.util.modules.entityhook.impl.pools.EntityHookPool;
import net.minecraft.entity.Entity;

import java.util.UUID;

public class GenericHookProvider<T extends EntityHook> implements IEntityHookProvider<T>
{
	protected final String id;

	protected final IEntityHookFactory<T> factory;

	public GenericHookProvider(String id, IEntityHookFactory<T> factory)
	{
		this.id = id;
		this.factory = factory;
	}

	@Override
	public void loadHook(T hook)
	{
		hook.onLoaded();
	}

	@Override
	public void unloadHook(T hook)
	{
		hook.onUnloaded();
	}

	@Override
	public void updateHook(T hook)
	{
		hook.onUpdate();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getHook(Entity entity)
	{
		return (T) entity.getExtendedProperties(this.getId());
	}

	@Override
	public boolean canAttachToEntity(Entity entity)
	{
		return true;
	}

	@Override
	public boolean isAttachedToEntity(Entity entity)
	{
		return this.getHook(entity) != null;
	}

	@Override
	public IEntityHookFactory<T> getFactory()
	{
		return this.factory;
	}

	@Override
	public String getId()
	{
		return this.id;
	}
}
