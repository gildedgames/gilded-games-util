package com.gildedgames.util.group.common.core;

import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.group.GroupCore;

public class PacketRemoveGroup extends PacketGroupAction<PacketRemoveGroup>
{
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
		if (!message.group.getPermissions().canRemoveGroup(this.group, GroupCore.getGroupMember(player)))
		{
			UtilCore.print("Player " + player.getCommandSenderName() + " tried to remove " + message.group.getName() + " but did not have the permissions.");
			return;
		}
		message.pool.remove(message.group);
	}

}
