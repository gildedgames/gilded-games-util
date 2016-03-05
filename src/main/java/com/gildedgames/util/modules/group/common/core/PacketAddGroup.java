package com.gildedgames.util.modules.group.common.core;

import com.gildedgames.util.core.io.ByteBufBridge;
import com.gildedgames.util.core.io.MessageHandlerClient;
import com.gildedgames.util.core.io.MessageHandlerServer;
import com.gildedgames.util.modules.group.GroupModule;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketAddGroup implements IMessage
{
	private GroupPool pool;

	private GroupInfo groupInfo;

	public PacketAddGroup()
	{

	}

	public PacketAddGroup(GroupPool pool, GroupInfo groupInfo)
	{
		this.pool = pool;
		this.groupInfo = groupInfo;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.pool = GroupModule.locate().getPoolFromID(ByteBufUtils.readUTF8String(buf));
		this.groupInfo = (new ByteBufBridge(buf)).getIO("");
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.pool.getID());
		(new ByteBufBridge(buf)).setIO("", this.groupInfo);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketAddGroup, IMessage>
	{
		@Override
		public IMessage onMessage(PacketAddGroup message, EntityPlayer player)
		{
			message.pool.create(message.groupInfo.getName(), player, message.groupInfo.getPermissions());

			return null;
		}
	}

	public static class HandlerClient extends MessageHandlerClient<PacketAddGroup, IMessage>
	{
		@Override
		public IMessage onMessage(PacketAddGroup message, EntityPlayer player)
		{
			Group group = new Group(message.pool);
			group.setGroupInfo(message.groupInfo);

			message.pool.addGroupDirectly(group);

			return null;
		}
	}

}
