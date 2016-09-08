package com.gildedgames.util.modules.instances;

import com.gildedgames.util.core.Module;
import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.core.util.GGHelper;
import com.gildedgames.util.modules.instances.networking.packet.PacketRegisterDimension;
import com.gildedgames.util.modules.instances.networking.packet.PacketRegisterInstance;
import com.gildedgames.util.modules.instances.networking.packet.PacketUnregisterDimension;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class InstanceModule extends Module
{

	public static InstanceModule INSTANCE = new InstanceModule();

	private List<InstanceHandler<?>> instances;

	@CapabilityInject(IPlayerInstances.class)
	public static final Capability<IPlayerInstances> PLAYER_INSTANCES = null;

	@Override
	public void init(FMLInitializationEvent event)
	{
		InstanceCapabilityManager.init();
	}

	@SubscribeEvent
	public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event)
	{
		for (InstanceHandler<?> handler : this.getHandlers())
		{
			handler.sendUnregisterInstancesPacket((EntityPlayerMP) event.player);
		}
	}

	public IPlayerInstances getPlayer(EntityPlayer player)
	{
		return player.getCapability(PLAYER_INSTANCES, null);
	}

	public IPlayerInstances getPlayer(UUID uuid)
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
	public void preInit(FMLPreInitializationEvent event)
	{
		UtilModule.NETWORK.registerMessage(PacketRegisterDimension.Handler.class, PacketRegisterDimension.class, Side.CLIENT);
		UtilModule.NETWORK.registerMessage(PacketUnregisterDimension.Handler.class, PacketUnregisterDimension.class, Side.CLIENT);
		UtilModule.NETWORK.registerMessage(PacketRegisterInstance.Handler.class, PacketRegisterInstance.class, Side.CLIENT);
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
