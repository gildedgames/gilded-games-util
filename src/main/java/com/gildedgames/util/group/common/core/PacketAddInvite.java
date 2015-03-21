package com.gildedgames.util.group.common.core;

import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.group.common.player.GroupMember;

public class PacketAddInvite extends PacketMemberAction<PacketAddInvite>
{
	public PacketAddInvite()
	{

	}

	public PacketAddInvite(GroupPool pool, Group group, GroupMember invited)
	{
		super(pool, group, invited);
	}

	@Override
	public void handleClientSide(PacketAddInvite message, EntityPlayer player)
	{
		message.pool.inviteDirectly(message.group, message.member);
	}

	@Override
	public void handleServerSide(PacketAddInvite message, EntityPlayer player)
	{
		if (!message.group.getPermissions().canInvite(message.member, GroupCore.getGroupMember(player)))
		{
			UtilCore.print("Player " + player.getCommandSenderName() + " tried to invite " + message.member.getProfile().getUsername() + " but did not have the permissions.");
			return;
		}
		message.pool.invite(message.member.getProfile().getEntity(), message.group);
	}

}
