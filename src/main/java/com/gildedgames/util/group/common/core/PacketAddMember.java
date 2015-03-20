package com.gildedgames.util.group.common.core;

import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.group.common.player.GroupMember;

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
		message.pool.addMember(message.member.getProfile().getEntity(), message.group);
	}

}
