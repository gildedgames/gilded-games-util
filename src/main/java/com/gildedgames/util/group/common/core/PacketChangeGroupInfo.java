package com.gildedgames.util.group.common.core;

import java.util.UUID;

import com.gildedgames.util.core.io.ByteBufBridge;
import com.gildedgames.util.group.GroupCore;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketChangeGroupInfo extends PacketMemberAction<PacketChangeGroupInfo>
{
	private GroupInfo groupInfo;

	public PacketChangeGroupInfo()
	{

	}

	public PacketChangeGroupInfo(UUID uuid, Group group, GroupInfo groupInfo)
	{
		super(group.getParentPool(), group, GroupCore.locate().getPlayers().get(uuid));
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

	@Override
	public void handleClientSide(PacketChangeGroupInfo message, EntityPlayer player)
	{
		message.pool.changeGroupInfoDirectly(message.group, message.groupInfo);
	}

	@Override
	public void handleServerSide(PacketChangeGroupInfo message, EntityPlayer player)
	{
		message.pool.changeGroupInfo(message.member.getProfile().getUUID(), message.group, message.groupInfo);
	}

}
