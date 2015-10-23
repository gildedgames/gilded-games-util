package com.gildedgames.util.group.common.core;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.group.common.player.GroupMember;

import net.minecraft.entity.player.EntityPlayer;

public class PacketRemoveGroup extends PacketGroupAction<PacketRemoveGroup>
{
	public PacketRemoveGroup()
	{

	}

	public PacketRemoveGroup(GroupPool pool, Group group)
	{
		super(pool, group);
	}

	@Override
	public void handleClientSide(PacketRemoveGroup message, EntityPlayer player)
	{
		message.pool.removeGroupDirectly(message.group);
	}

	@Override
	public void handleServerSide(PacketRemoveGroup message, EntityPlayer player)
	{
		if (!message.group.getPermissions().canRemoveGroup(message.group, GroupMember.get(player)))
		{
			UtilCore.print("Player " + player.getCommandSenderName() + " tried to remove " + message.group.getName() + " but did not have the permissions.");
			return;
		}
		message.pool.remove(message.group);
	}

}
