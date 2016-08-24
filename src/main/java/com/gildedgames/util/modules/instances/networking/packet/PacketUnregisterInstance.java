package com.gildedgames.util.modules.instances.networking.packet;

import com.gildedgames.util.core.io.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketUnregisterInstance implements IMessage
{

	private int dimID;

	public PacketUnregisterInstance()
	{

	}

	public PacketUnregisterInstance(int dimID)
	{
		this.dimID = dimID;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.dimID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.dimID);
	}

	public static class Handler extends MessageHandlerClient<PacketUnregisterInstance, PacketUnregisterInstance>
	{
		@Override
		public PacketUnregisterInstance onMessage(PacketUnregisterInstance message, EntityPlayer player)
		{
			if (DimensionManager.isDimensionRegistered(message.dimID))
			{
				DimensionManager.unregisterDimension(message.dimID);
			}

			return null;
		}
	}

}
