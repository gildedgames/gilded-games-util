package com.gildedgames.util.modules.group.common.core;

import com.gildedgames.util.core.io.MessageHandlerClient;
import com.gildedgames.util.modules.group.common.player.GroupMember;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Send to a client when one of their invitations is removed.
 * @author Emile
 *
 */
public class PacketRemoveInvitation extends PacketMemberAction<PacketRemoveInvitation>
{
	public PacketRemoveInvitation()
	{

	}

	public PacketRemoveInvitation(GroupPool pool, Group group, GroupMember invited)
	{
		super(pool, group, invited);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketRemoveInvitation, IMessage>
	{

		@Override
		public IMessage onMessage(PacketRemoveInvitation message, EntityPlayer player)
		{
			((GroupPoolClient) message.pool).invitationRemoved(message.group);

			return null;
		}
	}
}
