package com.gildedgames.util.modules.player.common.networking.messages;

import com.gildedgames.util.modules.player.PlayerModule;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.gildedgames.util.io_manager.io.IOSyncable.SyncSide;
import com.gildedgames.util.modules.player.common.player.IPlayerHook;

public class MessagePlayerHookClient implements IMessage
{

	private IPlayerHook playerHook;

	private ByteBuf buf;

	public MessagePlayerHookClient()
	{

	}

	public MessagePlayerHookClient(IPlayerHook playerHook)
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
		PlayerModule.locate().writeHookReference(this.playerHook, buf);
		
		this.playerHook.getProfile().syncTo(buf, SyncSide.SERVER);
		this.playerHook.syncTo(buf, SyncSide.SERVER);
	}
	
	public static class Handler implements IMessageHandler<MessagePlayerHookClient, IMessage>
	{
	        
        @Override
        public IMessage onMessage(MessagePlayerHookClient message, MessageContext ctx)
        {
        	if (ctx.side.isServer())
        	{
        		EntityPlayer player = ctx.getServerHandler().playerEntity;
        		
        		IPlayerHook playerHook = PlayerModule.locate().readHookReference(player, message.buf);
        		
        		playerHook.getProfile().syncFrom(message.buf, SyncSide.CLIENT);
        		playerHook.syncFrom(message.buf, SyncSide.CLIENT);
        	}

        	return null;
        }
        
	}

}
