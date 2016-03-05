package com.gildedgames.util.modules.group.common.core;

import com.gildedgames.util.core.io.MessageHandlerClient;
import com.gildedgames.util.core.io.MessageHandlerServer;
import com.gildedgames.util.modules.group.common.player.GroupMember;
import com.gildedgames.util.io_manager.util.IOUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketAddInvite extends PacketMemberAction<PacketAddInvite>
{
	private GroupMember inviter;

	public PacketAddInvite()
	{

	}

	public PacketAddInvite(GroupPool pool, Group group, GroupMember invited, GroupMember inviter)
	{
		super(pool, group, invited);
		this.inviter = inviter;
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
		IOUtil.writeUUID(this.inviter.getUniqueId(), buf);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketAddInvite, IMessage>
	{
		@Override
		public IMessage onMessage(PacketAddInvite message, EntityPlayer player)
		{
			message.pool.inviteDirectly(message.group, message.member, message.inviter);

			return null;
		}
	}

	public static class HandlerServer extends MessageHandlerServer<PacketAddInvite, IMessage>
	{
		@Override
		public IMessage onMessage(PacketAddInvite message, EntityPlayer player)
		{
			message.pool.invite(message.member.getUniqueId(), player.getGameProfile().getId(), message.group);

			return null;
		}
	}
}
