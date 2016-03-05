package com.gildedgames.util.modules.group.common.core;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.core.io.MessageHandlerClient;
import com.gildedgames.util.core.io.MessageHandlerServer;
import com.gildedgames.util.modules.group.common.player.GroupMember;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketRemoveGroup extends PacketGroupAction<PacketRemoveGroup>
{
	public PacketRemoveGroup()
	{

	}

	public PacketRemoveGroup(GroupPool pool, Group group)
	{
		super(pool, group);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketRemoveGroup, IMessage>
	{
		@Override
		public IMessage onMessage(PacketRemoveGroup message, EntityPlayer player)
		{
			message.pool.removeGroupDirectly(message.group);

			return null;
		}
	}

	public static class HandlerServer extends MessageHandlerServer<PacketRemoveGroup, IMessage>
	{
		@Override
		public IMessage onMessage(PacketRemoveGroup message, EntityPlayer player)
		{
			if (!message.group.getPermissions().canRemoveGroup(message.group, GroupMember.get(player)))
			{
				UtilModule.logger().warn("Player " + player.getName() + " tried to remove " + message.group.getName() + " but did not have the permissions.");

				return null;
			}
			message.pool.remove(message.group);

			return null;
		}
	}
}
