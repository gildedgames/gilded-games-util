package com.gildedgames.util.modules.entityhook.impl.providers;

import com.gildedgames.util.modules.entityhook.api.IEntityHookFactory;
import com.gildedgames.util.modules.entityhook.api.IEntityHookPool;
import com.gildedgames.util.modules.entityhook.impl.hooks.EntityHook;
import com.gildedgames.util.modules.entityhook.impl.pools.EntityHookPool;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

public class PlayerHookProvider<T extends EntityHook> extends GenericHookProvider<T>
{
	protected final EntityHookPool<T> pool = new EntityHookPool<>();

	public PlayerHookProvider(String id, IEntityHookFactory<T> factory)
	{
		super(id, factory);
	}

	@Override
	public void loadHook(T hook)
	{
		this.pool.addHook(hook);

		super.loadHook(hook);
	}

	@Override
	public void unloadHook(T hook)
	{
		this.pool.removeHook(hook);

		super.unloadHook(hook);
	}

	public T getHook(UUID uuid)
	{
		return this.pool.getHook(uuid);
	}

	public IEntityHookPool<T> getPool()
	{
		return this.pool;
	}

	@Override
	public boolean canAttachToEntity(Entity entity)
	{
		return entity instanceof EntityPlayer;
	}
}
