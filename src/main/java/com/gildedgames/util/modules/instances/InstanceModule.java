package com.gildedgames.util.modules.instances;

import java.util.UUID;

import com.gildedgames.util.core.Module;
import com.gildedgames.util.core.SidedObject;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public class InstanceModule extends Module
{
	private SidedObject<InstanceServices> services = new SidedObject<>(new InstanceServices(Side.CLIENT), new InstanceServices(Side.SERVER));

	public static InstanceModule INSTANCE = new InstanceModule();

	public InstanceServices locate()
	{
		return this.services.instance();
	}

	public PlayerInstances getPlayer(EntityPlayer player)
	{
		return this.locate().getPlayers().get(player);
	}

	public PlayerInstances getPlayer(UUID uuid)
	{
		return this.locate().getPlayers().get(uuid);
	}

	public <T extends Instance> InstanceHandler<T> createServerInstanceHandler(InstanceFactory<T> factory)
	{
		InstanceHandler<T> handler = new InstanceHandler<>(factory);
		this.services.server().addHandler(handler);
		return handler;
	}

	public <T extends Instance> InstanceHandler<T> createClientInstanceHandler(InstanceFactory<T> factory)
	{
		InstanceHandler<T> handler = new InstanceHandler<>(factory);
		this.services.client().addHandler(handler);
		return handler;
	}
}
