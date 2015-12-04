package com.gildedgames.util.group.common.core;

import com.gildedgames.util.core.io.CustomPacket;
import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.io_manager.util.IOUtil;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;

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
		this.pool = GroupCore.locate().getPoolFromID(ByteBufUtils.readUTF8String(buf));
		this.group = this.pool.get(IOUtil.readUUID(buf));
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.pool.getID());
		IOUtil.writeUUID(this.group.getUUID(), buf);
	}
}
