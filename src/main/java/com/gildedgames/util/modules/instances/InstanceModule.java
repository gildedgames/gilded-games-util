package com.gildedgames.util.modules.instances;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;

import com.gildedgames.util.core.Module;
import com.gildedgames.util.core.util.GGHelper;
import com.gildedgames.util.modules.entityhook.api.IEntityHookPool;

public class InstanceModule extends Module
{
	
	public static InstanceModule INSTANCE = new InstanceModule();
	
	private List<InstanceHandler<?>> instances;

	public PlayerInstances getPlayer(EntityPlayer player)
	{
		return PlayerInstances.PROVIDER.getHook(player);
	}

	public PlayerInstances getPlayer(UUID uuid)
	{
		return PlayerInstances.PROVIDER.getHook(uuid);
	}
	
	public int getFreeDimID()
	{
		int next = -1;
		
        while (true)
        {
            next--;
            
            if (!DimensionManager.isDimensionRegistered(next))
            {
                return next;
            }
        }
	}
	
	@Override
	public void serverStopped(FMLServerStoppedEvent event)
	{
		for (InstanceHandler<?> handler : this.getHandlers())
		{
			handler.unregisterInstances();
		}
	}
	
	@Override
	public void serverAboutToStart(FMLServerAboutToStartEvent event)
	{
		NBTTagCompound tag = GGHelper.readNBTFromFile("//data//instances.dat");

		if (tag == null)
		{
			return;
		}
		
		int i = 0;
		
		for (InstanceHandler<?> handler : this.getHandlers())
		{
			NBTTagCompound subTag = tag.getCompoundTag(String.valueOf(i++));
			
			handler.read(subTag);
		}
	}
	
	@Override
	public void flushData()
	{
		NBTTagCompound tag = new NBTTagCompound();
		
		int i = 0;
		
		tag.setInteger("size", this.getHandlers().size());
		
		for (InstanceHandler<?> handler : this.getHandlers())
		{
			NBTTagCompound subTag = new NBTTagCompound();
			handler.write(subTag);
			
			tag.setTag(String.valueOf(i++), subTag);
		}
		
		GGHelper.writeNBTToFile(tag, "//data//instances.dat");
	}

	public <T extends Instance> InstanceHandler<T> createInstanceHandler(InstanceFactory<T> factory)
	{
		InstanceHandler<T> handler = new InstanceHandler<>(factory);
		this.addHandler(handler);
		
		return handler;
	}
	
	public IEntityHookPool<PlayerInstances> getPlayerHooks()
	{
		return PlayerInstances.PROVIDER.getPool();
	}

	public Collection<InstanceHandler<?>> getHandlers()
	{
		if (this.instances == null)
		{
			this.instances = new ArrayList<>();
		}

		return this.instances;
	}

	protected void addHandler(InstanceHandler<?> handler)
	{
		this.getHandlers().add(handler);
	}

}
