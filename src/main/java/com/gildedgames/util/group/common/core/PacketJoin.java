package com.gildedgames.util.group.common.core;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.core.nbt.NBTHelper;

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
		MemberData data = NBTHelper.readInputObject(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);
		NBTHelper.writeOutputObject(this.group.getMemberData(), buf);
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
