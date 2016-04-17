package com.gildedgames.util.modules.instances;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.util.core.Module;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.core.util.GGHelper;

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
		for (InstanceHandler<?> handler : this.locate().getHandlers())
		{
			for (Instance inst : handler.getInstances())
			{
				int dim = handler.getDimensionForInstance(inst);
				
				if (DimensionManager.isDimensionRegistered(dim))
				{
					DimensionManager.unregisterDimension(dim);
				}
			}
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
		
		for (InstanceHandler<?> handler : this.locate().getHandlers())
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
		
		tag.setInteger("size", this.locate().getHandlers().size());
		
		for (InstanceHandler<?> handler : this.locate().getHandlers())
		{
			NBTTagCompound subTag = new NBTTagCompound();
			handler.write(subTag);
			
			tag.setTag(String.valueOf(i++), subTag);
		}
		
		GGHelper.writeNBTToFile(tag, "//data//instances.dat");
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
