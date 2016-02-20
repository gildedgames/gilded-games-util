package com.gildedgames.util.testutil.instances;

import net.minecraft.entity.player.EntityPlayerMP;

import com.gildedgames.util.modules.instances.InstanceHandler;

public class DefaultHandler
{
	private InstanceHandler<DefaultInstance> handler;

	public DefaultHandler(InstanceHandler<DefaultInstance> handler)
	{
		this.handler = handler;
	}

	public DefaultInstance get(String key)
	{
		for (DefaultInstance inst : this.handler.getInstances())
		{
			if (inst.getKey().equals(key))
			{
				return inst;
			}
		}
		DefaultInstance inst = this.handler.createNew();
		inst.setKey(key);
		return inst;
	}

	public void teleportToInst(EntityPlayerMP player, DefaultInstance inst)
	{
		this.handler.teleportPlayerToDimension(inst, player);
	}

	public void teleportBack(EntityPlayerMP player)
	{
		this.handler.teleportPlayerOutsideInstance(player);
	}
}
