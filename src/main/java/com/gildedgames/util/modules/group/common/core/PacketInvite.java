package com.gildedgames.util.modules.group.common.core;

import com.gildedgames.util.core.io.MessageHandlerClient;
import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.modules.group.common.player.GroupMember;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketInvite extends PacketMemberAction<PacketInvite>
{

	public PacketInvite()
	{

	}

	public PacketInvite(GroupPool pool, Group group, GroupMember inviter)
	{
		super(pool, group, inviter);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketInvite, IMessage>
	{
		@Override
		public IMessage onMessage(PacketInvite message, EntityPlayer player)
		{
			((GroupPoolClient) message.pool).inviteReceived(message.group, message.member);

			return null;
		}
	}
}
