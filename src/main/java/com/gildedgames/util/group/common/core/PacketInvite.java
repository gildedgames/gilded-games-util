package com.gildedgames.util.group.common.core;

import net.minecraft.entity.player.EntityPlayer;

public class PacketInvite extends PacketGroupAction<PacketInvite>
{

	public PacketInvite()
	{

	}

	public PacketInvite(GroupPool pool, Group group)
	{
		super(pool, group);
	}

	@Override
	public void handleClientSide(PacketInvite message, EntityPlayer player)
	{
		((GroupPoolClient) message.pool).inviteReceived(message.group);
	}

	@Override
	public void handleServerSide(PacketInvite message, EntityPlayer player)
	{
		throw new IllegalStateException();
	}

}
