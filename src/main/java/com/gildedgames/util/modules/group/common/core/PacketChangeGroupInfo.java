package com.gildedgames.util.modules.group.common.core;

import com.gildedgames.util.core.io.ByteBufBridge;
import com.gildedgames.util.core.io.MessageHandlerClient;
import com.gildedgames.util.core.io.MessageHandlerServer;
import com.gildedgames.util.modules.group.common.player.GroupMember;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.UUID;

public class PacketChangeGroupInfo extends PacketMemberAction<PacketChangeGroupInfo>
{
	private GroupInfo groupInfo;

	public PacketChangeGroupInfo()
	{

	}

	public PacketChangeGroupInfo(UUID uuid, Group group, GroupInfo groupInfo)
	{
		super(group.getParentPool(), group, GroupMember.get(uuid));
		this.groupInfo = groupInfo;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);
		this.groupInfo = (new ByteBufBridge(buf)).getIO("");
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);
		(new ByteBufBridge(buf)).setIO("", this.groupInfo);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketChangeGroupInfo, IMessage>
	{
		@Override
		public IMessage onMessage(PacketChangeGroupInfo message, EntityPlayer player)
		{
			message.pool.changeGroupInfoDirectly(message.group, message.groupInfo);

			return null;
		}
	}

	public static class HandlerServer extends MessageHandlerServer<PacketChangeGroupInfo, IMessage>
	{
		@Override
		public IMessage onMessage(PacketChangeGroupInfo message, EntityPlayer player)
		{
			message.pool.changeGroupInfo(message.member.getUniqueId(), message.group, message.groupInfo);

			return null;
		}
	}
}
