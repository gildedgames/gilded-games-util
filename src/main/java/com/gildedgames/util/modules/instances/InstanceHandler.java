package com.gildedgames.util.modules.instances;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;

import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.commons.io.FileUtils;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.core.nbt.NBTHelper;
import com.gildedgames.util.modules.world.common.BlockPosDimension;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class InstanceHandler<T extends Instance> implements NBT
{

	private final BiMap<Integer, T> instances = HashBiMap.create();

	private InstanceFactory<T> factory;

	public InstanceHandler(InstanceFactory<T> factory)
	{
		this.factory = factory;

//		DimensionManager.registerProviderType(factory.providerId(), factory.getProviderType(), false);
	}
	
	public T getInstance(int id)
	{
		return this.instances.get(id);
	}

	public T createNew()
	{
		int dimensionId = InstanceModule.INSTANCE.getFreeDimID();
		
//		DimensionManager.registerDimension(dimensionId, this.factory.providerId());
		T instance = this.factory.createInstance(dimensionId, this);
		this.instances.put(dimensionId, instance);
		
		return instance;
	}
	
	public void unregisterInstances()
	{
		for (Entry<Integer, T> entry : this.instances.entrySet())
		{
			int dimId = entry.getKey();

			DimensionManager.unregisterDimension(dimId);
		}
		
		this.instances.clear();
	}

	@Override
	public void write(NBTTagCompound output)
	{
		output.setBoolean("hasWrittenInstances", this.instances.size() > 0);
		
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
		boolean hasWrittenInstances = input.getBoolean("hasWrittenInstances");
		
		if (!hasWrittenInstances)
		{
			return;
		}
		
		for (NBTTagCompound tag : NBTHelper.getIterator(input, "instances"))
		{
			int id = tag.getInteger("dimension");

			if (DimensionManager.isDimensionRegistered(id))
			{
				final int oldId = id;
				
				File oldDimFolder = new File(UtilModule.getWorldDirectory(), "//DIM" + oldId);
				
				id = InstanceModule.INSTANCE.getFreeDimID();
				
				File newDimFolder = new File(UtilModule.getWorldDirectory(), "//DIM" + id);
				
				if(oldDimFolder.isDirectory())
				{
				    File[] content = oldDimFolder.listFiles();
				    
				    for(int i = 0; i < content.length; i++)
				    {
				    	try
				    	{
							FileUtils.moveFileToDirectory(content[i], newDimFolder, true);
						}
				    	catch (IOException e)
				    	{
							e.printStackTrace();
						}
				    }
				}
			}
			
			T instance = this.factory.createInstance(id, this);
			instance.read(tag);
//			DimensionManager.registerDimension(id, this.factory.providerId());

			this.instances.put(id, instance);
		}
	}

	public T getInstanceForDimension(int id)
	{
		return this.instances.get(id);
	}
	
	public int getDimensionForInstance(Instance instance)
	{
		return this.instances.inverse().get(instance);
	}

	public int getInstancesSize()
	{
		return this.instances.size();
	}

	public Collection<T> getInstances()
	{
		return this.instances.values();
	}

	public World teleportPlayerToDimension(T instance, EntityPlayerMP player)
	{
		if (this.instances.containsValue(instance))
		{
			PlayerInstances hook = InstanceModule.INSTANCE.getPlayer(player);
			if (hook.getInstance() == null)
			{
				hook.setOutside(new BlockPosDimension((int) player.posX, (int) player.posY, (int) player.posZ, player.dimension));
			}

			int dimId = this.instances.inverse().get(instance);

			MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

			WorldServer world = server.worldServerForDimension(dimId);
			
			Teleporter teleporter = this.factory.getTeleporter(world);

			PlayerList playerList = server.getPlayerList();
			playerList.transferPlayerToDimension(player, this.instances.inverse().get(instance), teleporter);

			player.timeUntilPortal = player.getPortalCooldown();

			hook.setInstance(instance);
			
			return world;
		}
		
		return player.worldObj;
	}

	public void teleportPlayerOutsideInstance(EntityPlayerMP player)
	{
		PlayerInstances hook = InstanceModule.INSTANCE.getPlayer(player);
		
		if (hook.getInstance() != null && hook.outside() != null)
		{
			MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

			BlockPosDimension pos = hook.outside();
			Teleporter teleporter = new InstanceTeleporter(server.worldServerForDimension(player.dimension));
			PlayerList playerList = server.getPlayerList();
			playerList.transferPlayerToDimension(player, pos.dimId(), teleporter);
			player.timeUntilPortal = player.getPortalCooldown();
			hook.setOutside(null);
			hook.setInstance(null);

			player.connection.setPlayerLocation(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
		}
	}

}
