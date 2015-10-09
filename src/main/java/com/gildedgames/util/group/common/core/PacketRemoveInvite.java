package com.gildedgames.util.group.common.core;

import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.io_manager.util.IOUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketRemoveInvite extends PacketMemberAction<PacketRemoveInvite>
{
	private GroupMember inviter;

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
		this.inviter = GroupMember.get(IOUtil.readUUID(buf));
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);
		IOUtil.writeUUID(this.inviter.getProfile().getUUID(), buf);
	}

	@Override
	public void handleClientSide(PacketRemoveInvite message, EntityPlayer player)
	{
		message.pool.removeInvitationDirectly(message.group, message.member);
	}

	@Override
	public void handleServerSide(PacketRemoveInvite message, EntityPlayer player)
	{
		message.pool.removeInvitation(message.member.getProfile().getEntity(), message.group);
	}
}
