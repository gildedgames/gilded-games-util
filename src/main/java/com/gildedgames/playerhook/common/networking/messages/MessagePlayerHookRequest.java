package com.gildedgames.playerhook.common.networking.messages;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.playerhook.PlayerHookCore;
import com.gildedgames.playerhook.common.PlayerHookManager;

public class MessagePlayerHookRequest implements IMessage
{

	private UUID uuid;
	
	private PlayerHookManager manager;

	public MessagePlayerHookRequest()
	{

	}

	public MessagePlayerHookRequest(PlayerHookManager manager, UUID uuid)
	{
		this.manager = manager;
		this.uuid = uuid;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.manager = PlayerHookManager.getManagers().get(buf.readInt());
		this.uuid = new UUID(buf.readLong(), buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.manager.getID());
		
		buf.writeLong(this.uuid.getMostSignificantBits());
		buf.writeLong(this.uuid.getLeastSignificantBits());
	}

	public static class Handler implements IMessageHandler<MessagePlayerHookRequest, IMessage>
	{
	        
        @Override
        public IMessage onMessage(MessagePlayerHookRequest message, MessageContext ctx)
        {
        	if (ctx.side.isServer())
        	{
        		EntityPlayer player = ctx.getServerHandler().playerEntity;
        		
        		PlayerHookCore.NETWORK.sendTo(new MessagePlayerHook(message.manager.instance(Side.SERVER).get(message.uuid)), (EntityPlayerMP) player);
        	}

        	return null;
        }
        
	}

}
