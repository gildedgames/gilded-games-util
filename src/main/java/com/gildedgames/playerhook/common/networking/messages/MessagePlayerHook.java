package com.gildedgames.playerhook.common.networking.messages;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.playerhook.PlayerHookCore;
import com.gildedgames.playerhook.common.PlayerHookManager;
import com.gildedgames.playerhook.common.player.IPlayerHook;
import com.gildedgames.playerhook.common.player.PlayerProfile;

public class MessagePlayerHook implements IMessage
{

	public IPlayerHook playerHook;
	
	public int managerID;
	
	public ByteBuf buf;

	public MessagePlayerHook()
	{
	}

	public MessagePlayerHook(IPlayerHook playerHook)
	{
		this.playerHook = playerHook;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.buf = buf;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.playerHook.getManager().getID());

		this.playerHook.getProfile().writeToClient(buf);
		this.playerHook.writeToClient(buf);
	}
	
	public static class Handler implements IMessageHandler<MessagePlayerHook, IMessage>
	{
	        
        @Override
        public IMessage onMessage(MessagePlayerHook message, MessageContext ctx)
        {
        	if (ctx.side.isClient())
        	{
        		PlayerHookManager manager = PlayerHookCore.proxy.getManagers().get(message.buf.readInt());
            	
            	IPlayerHook playerHook = manager.get(PlayerHookCore.proxy.getPlayer());
        		
            	playerHook.getProfile().readFromServer(message.buf);
            	playerHook.readFromServer(message.buf);
        	}

        	return null;
        }
        
	}

}
