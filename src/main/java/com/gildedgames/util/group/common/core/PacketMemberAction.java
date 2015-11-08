package com.gildedgames.util.group.common.core;

import com.gildedgames.util.core.CustomPacket;
import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.io_manager.util.IOUtil;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public abstract class PacketMemberAction<T extends PacketMemberAction<T>> extends CustomPacket<T>
{
	protected GroupPool pool;

	protected Group group;

	protected GroupMember member;

	public PacketMemberAction()
	{

	}

	public PacketMemberAction(GroupPool pool, Group group, GroupMember member)
	{
		this.pool = pool;
		this.group = group;
		this.member = member;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.pool = GroupCore.locate().getPoolFromID(ByteBufUtils.readUTF8String(buf));
		this.group = this.pool.get(IOUtil.readUUID(buf));
		this.member = GroupMember.get(IOUtil.readUUID(buf));
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.pool.getID());
		IOUtil.writeUUID(this.group.getUUID(), buf);
		IOUtil.writeUUID(this.member.getProfile().getUUID(), buf);
	}
}
