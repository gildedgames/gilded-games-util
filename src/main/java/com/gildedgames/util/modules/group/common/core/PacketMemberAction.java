package com.gildedgames.util.modules.group.common.core;

import com.gildedgames.util.io_manager.util.IOUtil;
import com.gildedgames.util.modules.group.GroupModule;
import com.gildedgames.util.modules.group.common.player.GroupMember;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class PacketMemberAction<T extends PacketMemberAction<T>> implements IMessage
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
		this.pool = GroupModule.locate().getPoolFromID(ByteBufUtils.readUTF8String(buf));
		this.group = this.pool.get(IOUtil.readUUID(buf));
		this.member = GroupMember.get(IOUtil.readUUID(buf));
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.pool.getID());
		IOUtil.writeUUID(this.group.getUUID(), buf);
		IOUtil.writeUUID(this.member.getPlayer().getUniqueID(), buf);
	}
}
