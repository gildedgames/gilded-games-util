package com.gildedgames.util.modules.group.common.core;

import com.gildedgames.util.core.io.ByteBufBridge;
import com.gildedgames.util.core.io.MessageHandlerClient;
import com.gildedgames.util.modules.group.GroupModule;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketJoin implements IMessage
{
	//This class doesn't use PacketGroupAction because of syncing issues. It'd send two packets, PacketAddGroup and this at the same time, meaning
	//sometimes this packet doesn't find the newly created group. Waiting until handleClientSide to read the group ensures it's there.

	private MemberData data;

	private GroupPool pool;

	private Group group;

	private ByteBuf buf;

	public PacketJoin() { }

	public PacketJoin(GroupPool pool, Group group)
	{
		this.pool = pool;
		this.group = group;
		this.data = group.getMemberData();
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.pool = GroupModule.locate().getPoolFromID(ByteBufUtils.readUTF8String(buf));
		this.buf = buf;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.pool.getID());
		ByteBufUtils.writeUTF8String(buf, this.group.getName());
		this.data.write((new ByteBufBridge(buf)));
	}

	public static class HandlerClient extends MessageHandlerClient<PacketJoin, IMessage>
	{
		@Override
		public IMessage onMessage(PacketJoin message, EntityPlayer player)
		{
			MemberData data = new MemberData();
			Group group = message.pool.get(ByteBufUtils.readUTF8String(message.buf));
			data.read(new ByteBufBridge(message.buf));
			((GroupPoolClient) message.pool).join(group, data);

			return null;
		}
	}
}
