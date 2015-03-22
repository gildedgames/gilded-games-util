package com.gildedgames.util.core.io;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.io.IOSyncable;
import com.gildedgames.util.io_manager.io.IOSyncable.SyncSide;
import com.gildedgames.util.io_manager.io.IOSyncableDispatcher;

public class MessageSyncToServer implements IMessage
{

	public IOSyncableDispatcher<ByteBuf, ByteBuf> dispatcher;

	public IOSyncable<ByteBuf, ByteBuf> syncable;

	public ByteBuf buf;

	public MessageSyncToServer()
	{
	}

	public MessageSyncToServer(IOSyncableDispatcher<ByteBuf, ByteBuf> dispatcher, IOSyncable<ByteBuf, ByteBuf> syncable)
	{
		this.dispatcher = dispatcher;
		this.syncable = syncable;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.buf = buf;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.dispatcher.getID());
		ByteBufUtils.writeUTF8String(buf, this.dispatcher.getKey(this.syncable, SyncSide.SERVER));
		
		this.syncable.syncTo(buf, SyncSide.SERVER);
	}
	
	public static class Handler implements IMessageHandler<MessageSyncToServer, IMessage>
	{
	        
        @Override
        public IMessage onMessage(MessageSyncToServer message, MessageContext ctx)
        {
        	if (ctx.side.isServer())
        	{
        		String dispatcherID = ByteBufUtils.readUTF8String(message.buf);
				String syncableID = ByteBufUtils.readUTF8String(message.buf);
				
				IOSyncableDispatcher<ByteBuf, ByteBuf> dispatcher = IOCore.io().getDispatcherFromID(dispatcherID);
				IOSyncable<ByteBuf, ByteBuf> syncable = dispatcher.getSyncable(syncableID, SyncSide.CLIENT);
				
				syncable.syncFrom(message.buf, SyncSide.CLIENT);
        	}

        	return null;
        }
        
	}

}
