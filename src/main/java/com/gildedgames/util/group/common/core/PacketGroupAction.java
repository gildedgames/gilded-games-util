package com.gildedgames.util.group.common.core;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import com.gildedgames.util.core.CustomPacket;
import com.gildedgames.util.group.GroupCore;

public abstract class PacketGroupAction<T extends PacketGroupAction<T>> extends CustomPacket<T>
{
	GroupPool pool;

	Group group;

	public PacketGroupAction()
	{

	}

	public PacketGroupAction(GroupPool pool, Group group)
	{
		this.pool = pool;
		this.group = group;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.pool = GroupCore.locate().getFromID(ByteBufUtils.readUTF8String(buf));
		this.group = this.pool.get(ByteBufUtils.readUTF8String(buf));
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.pool.getID());
		ByteBufUtils.writeUTF8String(buf, this.group.getName());
	}
}
