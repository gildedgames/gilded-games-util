package com.gildedgames.util.modules.instances.networking.packet;

import com.gildedgames.util.core.io.MessageHandlerClient;
import com.gildedgames.util.core.io.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketRegisterInstance implements IMessage
{

	private int dimID;

	private DimensionType type;

	public PacketRegisterInstance()
	{

	}

	public PacketRegisterInstance(DimensionType type, int dimID)
	{
		this.dimID = dimID;
		this.type = type;
	}

	private DimensionType fromOrdinal(int ordinal)
	{
		DimensionType[] type = DimensionType.values();

		return type[ordinal > type.length || ordinal < 0 ? 0 : ordinal];
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.dimID = buf.readInt();
		this.type = this.fromOrdinal(buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.dimID);
		buf.writeInt(this.type.ordinal());
	}

	public static class Handler extends MessageHandlerClient<PacketRegisterInstance, PacketRegisterInstance>
	{
		@Override
		public PacketRegisterInstance onMessage(PacketRegisterInstance message, EntityPlayer player)
		{
			DimensionManager.registerDimension(message.dimID, message.type);

			return null;
		}
	}

}
