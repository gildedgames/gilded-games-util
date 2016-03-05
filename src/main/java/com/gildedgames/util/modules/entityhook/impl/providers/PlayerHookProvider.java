package com.gildedgames.util.modules.entityhook.impl.providers;

import com.gildedgames.util.modules.entityhook.api.IEntityHookFactory;
import com.gildedgames.util.modules.entityhook.impl.hooks.EntityHook;
import com.gildedgames.util.modules.entityhook.impl.pools.EntityHookPool;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerHookProvider<T extends EntityHook> extends GenericHookProvider<T>
{
	protected final EntityHookPool<T> pool = new EntityHookPool<>();

	public PlayerHookProvider(String id, IEntityHookFactory<T> factory)
	{
		super(id, factory);
	}

	@Override
	public boolean canAttachToEntity(Entity entity)
	{
		return entity instanceof EntityPlayer;
	}
}
