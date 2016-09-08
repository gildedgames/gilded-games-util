package com.gildedgames.util.modules.instances.networking.packet;

import com.gildedgames.util.core.io.MessageHandlerClient;
import com.gildedgames.util.core.nbt.NBTHelper;
import com.gildedgames.util.modules.instances.IPlayerInstances;
import com.gildedgames.util.modules.instances.Instance;
import com.gildedgames.util.modules.instances.InstanceModule;
import com.gildedgames.util.modules.instances.PlayerInstances;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketRegisterInstance implements IMessage
{

	private Instance instance;

	public PacketRegisterInstance()
	{

	}

	public PacketRegisterInstance(Instance instance)
	{
		this.instance = instance;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		NBTTagCompound tag = ByteBufUtils.readTag(buf);

		this.instance = NBTHelper.fullyDeserialize("instance", tag);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		NBTTagCompound tag = new NBTTagCompound();

		NBTHelper.fullySerialize("instance", this.instance, tag);

		ByteBufUtils.writeTag(buf, tag);
	}

	public static class Handler extends MessageHandlerClient<PacketRegisterInstance, PacketRegisterInstance>
	{
		@Override
		public PacketRegisterInstance onMessage(PacketRegisterInstance message, EntityPlayer player)
		{
			IPlayerInstances instances = InstanceModule.INSTANCE.getPlayer(player);

			instances.setInstance(message.instance);

			return null;
		}
	}

}
