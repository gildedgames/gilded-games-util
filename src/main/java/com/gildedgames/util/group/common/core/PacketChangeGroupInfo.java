package com.gildedgames.util.group.common.core;

import com.gildedgames.util.core.io.ByteBufBridge;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketChangeGroupInfo extends PacketGroupAction<PacketChangeGroupInfo>
{
	private GroupInfo groupInfo;

	public PacketChangeGroupInfo()
	{

	}

	public PacketChangeGroupInfo(GroupPool pool, Group group, GroupInfo groupInfo)
	{
		super(pool, group);
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
		throw new IllegalStateException();
	}

}
