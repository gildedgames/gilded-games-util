package com.gildedgames.util.group.common.core;

import com.gildedgames.util.group.common.player.GroupMember;

import net.minecraft.entity.player.EntityPlayer;

public class PacketAddMember extends PacketMemberAction<PacketAddMember>
{
	public PacketAddMember()
	{

	}

	public PacketAddMember(GroupPool pool, Group group, GroupMember member)
	{
		super(pool, group, member);
	}

	@Override
	public void handleClientSide(PacketAddMember message, EntityPlayer player)
	{
		message.pool.addMemberDirectly(message.group, message.member);
	}

	@Override
	public void handleServerSide(PacketAddMember message, EntityPlayer player)
	{
		message.pool.addMember(message.member.getProfile().getUUID(), message.group);
	}

}
