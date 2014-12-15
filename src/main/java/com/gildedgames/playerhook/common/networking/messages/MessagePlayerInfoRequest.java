package com.gildedgames.playerhook.common.networking.messages;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.playerhook.PlayerHookCore;
import com.gildedgames.playerhook.common.player.PlayerHook;

public class MessagePlayerInfoRequest implements IMessage
{

	private String username;

	public MessagePlayerInfoRequest()
	{

	}

	public MessagePlayerInfoRequest(String username)
	{
		this.username = username;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.username = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.username);
	}

	public static class Handler implements IMessageHandler<MessagePlayerInfoRequest, IMessage>
	{
	        
        @Override
        public IMessage onMessage(MessagePlayerInfoRequest message, MessageContext ctx)
        {
        	if (ctx.side == Side.SERVER)
        	{
        		EntityPlayer player = ctx.getServerHandler().playerEntity;
        		
        		PlayerHookCore.NETWORK.sendTo(new MessagePlayerInfo(PlayerHook.get(Side.SERVER, message.username)), (EntityPlayerMP) player);
        	}

        	return null;
        }
        
	}

}
