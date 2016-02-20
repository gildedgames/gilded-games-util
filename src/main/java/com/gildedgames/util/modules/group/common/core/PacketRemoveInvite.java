package com.gildedgames.util.modules.group.common.core;

import com.gildedgames.util.modules.group.common.player.GroupMember;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketRemoveInvite extends PacketMemberAction<PacketRemoveInvite>
{
	public PacketRemoveInvite()
	{

	}

	public PacketRemoveInvite(GroupPool pool, Group group, GroupMember invited)
	{
		super(pool, group, invited);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);
	}

	@Override
	public void handleClientSide(PacketRemoveInvite message, EntityPlayer player)
	{
		message.pool.removeInvitationDirectly(message.group, message.member);
	}

	@Override
	public void handleServerSide(PacketRemoveInvite message, EntityPlayer player)
	{
		message.pool.removeInvitation(message.member.getProfile().getUUID(), message.group);
	}
}
