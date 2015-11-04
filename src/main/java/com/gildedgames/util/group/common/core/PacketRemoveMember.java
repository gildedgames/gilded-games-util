package com.gildedgames.util.group.common.core;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.group.common.player.GroupMember;

import net.minecraft.entity.player.EntityPlayer;

public class PacketRemoveMember extends PacketMemberAction<PacketRemoveMember>
{

	public PacketRemoveMember()
	{

	}

	public PacketRemoveMember(GroupPool pool, Group group, GroupMember member)
	{
		super(pool, group, member);
	}

	@Override
	public void handleClientSide(PacketRemoveMember message, EntityPlayer player)
	{
		message.pool.removeMemberDirectly(message.group, message.member);
	}

	@Override
	public void handleServerSide(PacketRemoveMember message, EntityPlayer player)
	{
		if (!message.group.getPermissions().canRemoveMember(message.group, message.member, GroupMember.get(player)))
		{
			UtilCore.print("Player " + player.getCommandSenderName() + " tried to remove " + message.member.getProfile().getUsername() + " but did not have the permissions.");
			return;
		}
		message.pool.removeMember(message.member.getProfile().getUUID(), message.group);
	}

}
