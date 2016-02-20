package com.gildedgames.util.modules.group.common.core;

import com.gildedgames.util.modules.group.common.player.GroupMember;
import com.gildedgames.util.io_manager.util.IOUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketAddInvite extends PacketMemberAction<PacketAddInvite>
{
	private GroupMember inviter;

	public PacketAddInvite()
	{

	}

	public PacketAddInvite(GroupPool pool, Group group, GroupMember invited, GroupMember inviter)
	{
		super(pool, group, invited);
		this.inviter = inviter;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);
		this.inviter = GroupMember.get(IOUtil.readUUID(buf));
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);
		IOUtil.writeUUID(this.inviter.getProfile().getUUID(), buf);
	}

	@Override
	public void handleClientSide(PacketAddInvite message, EntityPlayer player)
	{
		message.pool.inviteDirectly(message.group, message.member, message.inviter);
	}

	@Override
	public void handleServerSide(PacketAddInvite message, EntityPlayer player)
	{
		message.pool.invite(message.member.getProfile().getUUID(), player.getGameProfile().getId(), message.group);
	}

}
