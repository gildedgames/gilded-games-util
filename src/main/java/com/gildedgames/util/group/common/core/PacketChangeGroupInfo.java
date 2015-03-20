package com.gildedgames.util.group.common.core;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.core.nbt.NBTHelper;

public class PacketChangeGroupInfo extends PacketGroupAction<PacketChangeGroupInfo>
{
	private GroupInfo groupInfo;

	public PacketChangeGroupInfo()
	{

	}

	public PacketChangeGroupInfo(GroupPool pool, Group group, GroupInfo groupInfo)
	{
		this.groupInfo = groupInfo;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);
		this.groupInfo = NBTHelper.readInputObject(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);
		NBTHelper.writeOutputObject(this.groupInfo, buf);
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
