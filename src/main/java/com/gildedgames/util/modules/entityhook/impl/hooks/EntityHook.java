package com.gildedgames.util.modules.entityhook.impl.hooks;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import java.util.UUID;

/**
 * @param <T> The entity type this hook will attach to
 */
public abstract class EntityHook<T extends Entity> implements IExtendedEntityProperties
{
	protected T player;

	@Override
	@SuppressWarnings("unchecked")
	public void init(Entity entity, World world)
	{
		this.player = (T) entity;
	}

	public abstract void onLoaded();

	public abstract void onUnloaded();

	public abstract void onUpdate();

	public T getEntity()
	{
		return this.player;
	}

	public UUID getUniqueId()
	{
		return this.getEntity().getUniqueID();
	}
}
