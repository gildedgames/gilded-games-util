package com.gildedgames.util.group.common.core;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import com.gildedgames.util.core.CustomPacket;
import com.gildedgames.util.core.nbt.NBTHelper;
import com.gildedgames.util.group.GroupCore;

public class PacketAddGroup extends CustomPacket<PacketAddGroup>
{
	GroupPool pool;

	GroupInfo groupinfo;

	public PacketAddGroup()
	{

	}

	public PacketAddGroup(GroupPool pool, GroupInfo groupInfo)
	{
		this.pool = pool;
		this.groupinfo = groupInfo;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.pool = GroupCore.locate().getFromID(ByteBufUtils.readUTF8String(buf));
		this.groupinfo = NBTHelper.readInputObject(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.pool.getID());
		NBTHelper.writeOutputObject(this.groupinfo, buf);
	}

	@Override
	public void handleClientSide(PacketAddGroup message, EntityPlayer player)
	{
		Group group = new Group(message.pool);
		group.setGroupInfo(message.groupinfo);
		message.pool.addGroupDirectly(group);
	}

	@Override
	public void handleServerSide(PacketAddGroup message, EntityPlayer player)
	{
		message.pool.create(message.groupinfo.getName(), player);
	}

}
