package com.gildedgames.util.modules.group.common.core;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.core.io.MessageHandlerClient;
import com.gildedgames.util.core.io.MessageHandlerServer;
import com.gildedgames.util.modules.group.common.player.GroupMember;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketRemoveMember extends PacketMemberAction<PacketRemoveMember>
{

	public PacketRemoveMember()
	{

	}

	public PacketRemoveMember(GroupPool pool, Group group, GroupMember member)
	{
		super(pool, group, member);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketRemoveMember, IMessage>
	{
		@Override
		public IMessage onMessage(PacketRemoveMember message, EntityPlayer player)
		{
			message.pool.removeMemberDirectly(message.group, message.member);

			return null;
		}
	}

	public static class HandlerServer extends MessageHandlerServer<PacketRemoveMember, IMessage>
	{
		@Override
		public IMessage onMessage(PacketRemoveMember message, EntityPlayer player)
		{
			if (!message.group.getPermissions().canRemoveMember(message.group, message.member, GroupMember.get(player)))
			{
				UtilModule.logger().warn("Player " + player.getName() + " tried to remove " + message.member.getUniqueId() + " but did not have the permissions.");
				return null;
			}
			message.pool.removeMember(message.member.getUniqueId(), message.group);

			return null;
		}
	}
}
