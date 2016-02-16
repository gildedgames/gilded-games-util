package com.gildedgames.util.group.common.core;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.group.common.player.GroupMember;

import net.minecraft.entity.player.EntityPlayer;

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
		if (!group.hasMemberData() || !group.getMemberData().contains(message.member.getProfile().getUUID()) || !group.getPermissions().canChangeOwner(group, message.member, thePlayer))
		{
			UtilModule.print("Player " + player.getName() + " tried to change " + message.member.getProfile().getUsername() + " to the owner but did not have the permissions.");
			return;
		}
		//message.pool.changeOwner(message.member.getProfile().getEntity(), message.group);
	}
}
