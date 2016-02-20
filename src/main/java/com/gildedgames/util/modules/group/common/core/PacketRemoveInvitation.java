package com.gildedgames.util.modules.group.common.core;

import com.gildedgames.util.modules.group.common.player.GroupMember;

import net.minecraft.entity.player.EntityPlayer;

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

	@Override
	public void handleClientSide(PacketRemoveInvitation message, EntityPlayer player)
	{
		((GroupPoolClient) message.pool).invitationRemoved(message.group);
	}

	@Override
	public void handleServerSide(PacketRemoveInvitation message, EntityPlayer player)
	{
		throw new IllegalStateException();
	}
}
