package com.gildedgames.util.modules.group.common.core;

import com.gildedgames.util.core.io.MessageHandlerClient;
import com.gildedgames.util.core.io.MessageHandlerServer;
import com.gildedgames.util.modules.group.common.player.GroupMember;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

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
	
	public static class HandlerClient extends MessageHandlerClient<PacketRemoveInvite, IMessage>
	{

		@Override
		public IMessage onMessage(PacketRemoveInvite message, EntityPlayer player)
		{
			message.pool.removeInvitationDirectly(message.group, message.member);

			return null;
		}
	}
	
	public static class HandlerServer extends MessageHandlerServer<PacketRemoveInvite, IMessage>
	{

		@Override
		public IMessage onMessage(PacketRemoveInvite message, EntityPlayer player)
		{
			message.pool.removeInvitation(message.member.getUniqueId(), message.group);

			return null;
		}
	}
}
