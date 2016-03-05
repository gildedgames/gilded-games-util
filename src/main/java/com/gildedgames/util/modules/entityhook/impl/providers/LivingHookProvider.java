package com.gildedgames.util.modules.entityhook.impl.providers;

import com.gildedgames.util.modules.entityhook.api.IEntityHookFactory;
import com.gildedgames.util.modules.entityhook.impl.hooks.EntityHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class LivingHookProvider<T extends EntityHook> extends GenericHookProvider<T>
{
	public LivingHookProvider(String id, IEntityHookFactory<T> factory)
	{
		super(id, factory);
	}

	@Override
	public boolean canAttachToEntity(Entity entity)
	{
		return entity instanceof EntityLivingBase;
	}
}
