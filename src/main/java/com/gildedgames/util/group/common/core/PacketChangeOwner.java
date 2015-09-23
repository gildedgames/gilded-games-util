package com.gildedgames.util.group.common.core;

import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.group.common.player.GroupMember;

public class PacketChangeOwner extends PacketMemberAction<PacketChangeOwner>
{
	public PacketChangeOwner()
	{

	}

	public PacketChangeOwner(GroupPool pool, Group group, GroupMember newOwner)
	{
		super(pool, group, newOwner);
	}

	@Override
	public void handleClientSide(PacketChangeOwner message, EntityPlayer player)
	{
		throw new IllegalStateException();
	}

	@Override
	public void handleServerSide(PacketChangeOwner message, EntityPlayer player)
	{
		Group group = message.group;
		GroupMember thePlayer = GroupMember.get(player);
		if (!group.hasMemberData() || !group.getMemberData().contains(message.member) || !group.getPermissions().canChangeOwner(message.member, thePlayer))
		{
			UtilCore.print("Player " + player.getCommandSenderName() + " tried to change " + message.member.getProfile().getUsername() + " to the owner but did not have the permissions.");
			return;
		}
		//message.pool.changeOwner(message.member.getProfile().getEntity(), message.group);
	}
}
