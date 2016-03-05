package com.gildedgames.util.modules.group.common.core;

import com.gildedgames.util.core.io.MessageHandlerClient;
import com.gildedgames.util.core.io.MessageHandlerServer;
import com.gildedgames.util.modules.group.common.player.GroupMember;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketAddMember extends PacketMemberAction<PacketAddMember>
{
	public PacketAddMember()
	{

	}

	public PacketAddMember(GroupPool pool, Group group, GroupMember member)
	{
		super(pool, group, member);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketAddMember, IMessage>
	{
		@Override
		public IMessage onMessage(PacketAddMember message, EntityPlayer player)
		{
			message.pool.addMemberDirectly(message.group, message.member);

			return null;
		}
	}

	public static class HandlerServer extends MessageHandlerServer<PacketAddMember, IMessage>
	{

		@Override
		public IMessage onMessage(PacketAddMember message, EntityPlayer player)
		{
			message.pool.addMember(message.member.getUniqueId(), message.group);

			return null;
		}
	}
}
