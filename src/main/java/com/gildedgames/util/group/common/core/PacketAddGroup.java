package com.gildedgames.util.group.common.core;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import com.gildedgames.util.core.CustomPacket;
import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.group.common.permissions.GroupPermsOpen;
import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.io_manager.util.IOUtil;

public class PacketAddGroup extends CustomPacket<PacketAddGroup>
{
	GroupPool pool;

	String name;

	GroupMember creating;

	public PacketAddGroup(GroupPool pool, String name, EntityPlayer creating)
	{
		this.pool = pool;
		this.name = name;
		this.creating = GroupCore.getGroupMember(creating);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.pool = GroupCore.locate().getFromID(ByteBufUtils.readUTF8String(buf));
		this.name = ByteBufUtils.readUTF8String(buf);
		this.creating = GroupCore.getGroupMember(IOUtil.readUUID(buf));
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.pool.getID());
		ByteBufUtils.writeUTF8String(buf, this.name);
		IOUtil.writeUUID(this.creating.getProfile().getUUID(), buf);
	}

	@Override
	public void handleClientSide(PacketAddGroup message, EntityPlayer player)
	{
		Group group = new Group(message.pool, new GroupPermsOpen(), message.name, message.creating);
		message.pool.addGroupDirectly(group);
	}

	@Override
	public void handleServerSide(PacketAddGroup message, EntityPlayer player)
	{
		message.pool.create(message.name, message.creating.getProfile().getEntity());
	}

}
