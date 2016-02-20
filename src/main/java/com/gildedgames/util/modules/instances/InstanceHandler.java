package com.gildedgames.util.modules.instances;

import java.util.Collection;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.world.Teleporter;
import net.minecraftforge.common.DimensionManager;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.core.nbt.NBTHelper;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class InstanceHandler<T extends Instance> implements NBT
{

	private final BiMap<Integer, T> instances = HashBiMap.create();

	private InstanceFactory<T> factory;

	public InstanceHandler(InstanceFactory<T> factory)
	{
		this.factory = factory;
		DimensionManager.registerProviderType(factory.providerId(), factory.getProviderType(), false);
	}

	public T getInstance(int id)
	{
		return this.instances.get(id);
	}

	public T createNew()
	{
		int dimensionId = DimensionManager.getNextFreeDimId();
		DimensionManager.registerDimension(dimensionId, this.factory.providerId());
		T instance = this.factory.createInstance(dimensionId, this);
		this.instances.put(dimensionId, instance);
		return instance;

	}

	public int createDimensionFor(T instance)
	{
		int dimensionId = DimensionManager.getNextFreeDimId();
		DimensionManager.registerDimension(dimensionId, this.factory.providerId());
		this.instances.put(dimensionId, instance);
		return dimensionId;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		NBTTagList tagList = new NBTTagList();
		for (Entry<Integer, T> entry : this.instances.entrySet())
		{
			T instance = entry.getValue();
			NBTTagCompound newTag = new NBTTagCompound();
			newTag.setInteger("dimension", entry.getKey());
			instance.write(newTag);
			tagList.appendTag(newTag);
		}
		output.setTag("instances", tagList);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		for (NBTTagCompound tag : NBTHelper.getIterator(input, "instances"))
		{
			int id = tag.getInteger("dimension");
			if (DimensionManager.isDimensionRegistered(id))
			{
				id = DimensionManager.getNextFreeDimId();
				//TODO: Relocate contents in old folder "DIM" + id to "DIM" + newId
			}
			T instance = this.factory.createInstance(id, this);
			instance.read(tag);
			DimensionManager.registerDimension(id, this.factory.providerId());

			this.instances.put(id, instance);
		}
	}

	public T getInstanceForDimension(int id)
	{
		return this.instances.get(id);
	}

	public int getInstancesSize()
	{
		return this.instances.size();
	}

	public Collection<T> getInstances()
	{
		return this.instances.values();
	}

	public void teleportPlayerToDimension(T instance, EntityPlayerMP player)
	{
		if (this.instances.containsValue(instance))
		{
			PlayerInstances hook = InstanceModule.INSTANCE.getPlayer(player);
			if (hook.getInstance() == null)
			{
				hook.setOutside(new BlockPosDimension((int) player.posX, (int) player.posY, (int) player.posZ, player.dimension));
			}
			int dimId = this.instances.inverse().get(instance);

			Teleporter teleporter = this.factory.getTeleporter(MinecraftServer.getServer().worldServerForDimension(dimId));

			ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();
			scm.transferPlayerToDimension(player, this.instances.inverse().get(instance), teleporter);

			player.timeUntilPortal = player.getPortalCooldown();

			hook.setInstance(instance);
		}
	}

	public void teleportPlayerOutsideInstance(EntityPlayerMP player)
	{
		PlayerInstances hook = InstanceModule.INSTANCE.getPlayer(player);
		if (hook.getInstance() != null && hook.outside() != null)
		{
			BlockPosDimension pos = hook.outside();
			Teleporter teleporter = new InstanceTeleporter(MinecraftServer.getServer().worldServerForDimension(player.dimension));
			ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();
			scm.transferPlayerToDimension(player, pos.dimId(), teleporter);
			player.timeUntilPortal = player.getPortalCooldown();
			hook.setOutside(null);
			hook.setInstance(null);

			player.playerNetServerHandler.setPlayerLocation(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
		}
	}

}
