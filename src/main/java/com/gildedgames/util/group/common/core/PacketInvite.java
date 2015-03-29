package com.gildedgames.util.group.common.core;

import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.group.common.player.GroupMember;

public class PacketInvite extends PacketMemberAction<PacketInvite>
{

	public PacketInvite()
	{

	}

	public PacketInvite(GroupPool pool, Group group, GroupMember inviter)
	{
		super(pool, group, inviter);
	}

	@Override
	public void handleClientSide(PacketInvite message, EntityPlayer player)
	{
		((GroupPoolClient) message.pool).inviteReceived(message.group, message.member);
	}

	@Override
	public void handleServerSide(PacketInvite message, EntityPlayer player)
	{
		throw new IllegalStateException();
	}

}
