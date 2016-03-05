package com.gildedgames.util.modules.group.common.core;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.core.io.MessageHandlerClient;
import com.gildedgames.util.core.io.MessageHandlerServer;
import com.gildedgames.util.modules.group.common.player.GroupMember;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketChangeOwner extends PacketMemberAction<PacketChangeOwner>
{
	public PacketChangeOwner()
	{

	}

	public PacketChangeOwner(GroupPool pool, Group group, GroupMember newOwner)
	{
		super(pool, group, newOwner);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketChangeOwner, IMessage>
	{

		@Override
		public IMessage onMessage(PacketChangeOwner message, EntityPlayer player)
		{
			Group group = message.group;
			GroupMember thePlayer = GroupMember.get(player);

			if (!group.hasMemberData() || !group.getMemberData().contains(message.member.getUniqueId()) || !group.getPermissions().canChangeOwner(group, message.member, thePlayer))
			{
				UtilModule.logger().warn("Player " + player.getName() + " tried to change " + message.member.getEntity().getName() + " to the owner but did not have the permissions.");
				return null;
			}

			//message.pool.changeOwner(message.member.getProfile().getEntity(), message.group);

			return null;
		}
	}
}
