package com.gildedgames.util.modules.instances;

import com.gildedgames.util.core.Module;
import com.gildedgames.util.core.util.GGHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class InstanceModule extends Module
{

	public static InstanceModule INSTANCE = new InstanceModule();

	private List<InstanceHandler<?>> instances;

	@CapabilityInject(PlayerInstances.class)
	public static final Capability<PlayerInstances> PLAYER_INSTANCES = null;

	private InstanceCapabilityManager capabilityManager;

	@Override
	public void init(FMLInitializationEvent event)
	{
		this.capabilityManager = new InstanceCapabilityManager();
		this.capabilityManager.init();
	}

	public PlayerInstances getPlayer(EntityPlayer player)
	{
		return player.getCapability(PLAYER_INSTANCES, null);
	}

	public PlayerInstances getPlayer(UUID uuid)
	{
		EntityPlayer player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(uuid);

		if (player == null)
		{
			return null;
		}

		return InstanceModule.INSTANCE.getPlayer(player);
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
	public void serverStarted(FMLServerStartedEvent event)
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
