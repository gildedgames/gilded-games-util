package com.gildedgames.util.group.common.core;

import com.gildedgames.util.core.io.ByteBufBridge;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketJoin extends PacketGroupAction<PacketJoin>
{
	MemberData data;

	public PacketJoin()
	{

	}

	public PacketJoin(GroupPool pool, Group group)
	{
		super(pool, group);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);
		this.data = (new ByteBufBridge(buf)).getIO("");
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);
		(new ByteBufBridge(buf)).setIO("", this.data);
	}

	@Override
	public void handleClientSide(PacketJoin message, EntityPlayer player)
	{
		((GroupPoolClient) message.pool).join(message.group, message.data);
	}

	@Override
	public void handleServerSide(PacketJoin message, EntityPlayer player)
	{
		throw new IllegalStateException();
	}
}
