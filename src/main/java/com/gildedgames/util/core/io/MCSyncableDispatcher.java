package com.gildedgames.util.core.io;

import com.gildedgames.util.core.UtilModule;
import io.netty.buffer.ByteBuf;

import java.util.Map;

import com.gildedgames.util.io_manager.io.IOSyncable;
import com.gildedgames.util.io_manager.io.IOSyncable.SyncSide;
import com.gildedgames.util.io_manager.io.IOSyncableDispatcher;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class MCSyncableDispatcher implements IOSyncableDispatcher<ByteBuf, ByteBuf>
{
	
	private BiMap<String, IOSyncable<ByteBuf, ByteBuf>> serverSyncables = HashBiMap.create();

	private BiMap<String, IOSyncable<ByteBuf, ByteBuf>> clientSyncables = HashBiMap.create();
	
	private String id;
	
	public MCSyncableDispatcher(String id)
	{
		this.id = id;
	}
	
	private BiMap<String, IOSyncable<ByteBuf, ByteBuf>> getSyncables(SyncSide side)
	{
		return side.isServer() ? this.serverSyncables : this.clientSyncables;
	}
	
	@Override
	public String getID()
	{
		return this.id;
	}
	
	@Override
	public void dispatchDirtySyncables(SyncSide from)
	{
		for (Map.Entry<String, IOSyncable<ByteBuf, ByteBuf>> pair : this.getSyncables(from).entrySet())
		{
			IOSyncable<ByteBuf, ByteBuf> syncable = pair.getValue();
			
			if (syncable != null && syncable.isDirty())
			{
				if (from.isClient())
				{
					UtilModule.NETWORK.sendToServer(new MessageSyncToServer(this, syncable));
				}
				else
				{
					UtilModule.NETWORK.sendToServer(new MessageSyncToClient(this, syncable));
				}
			}
		}
	}

	@Override
	public void track(String key, IOSyncable<ByteBuf, ByteBuf> syncable, SyncSide... sides)
	{
		for (SyncSide side : sides)
		{
			this.getSyncables(side).put(key, syncable);
		}
	}
	
	@Override
	public void untrack(String key, SyncSide side)
	{
		this.getSyncables(side).remove(key);
	}

	@Override
	public void untrack(IOSyncable<ByteBuf, ByteBuf> syncable, SyncSide side)
	{
		this.getSyncables(side).inverse().remove(syncable);
	}

	@Override
	public IOSyncable<ByteBuf, ByteBuf> getSyncable(String key, SyncSide side)
	{
		return this.getSyncables(side).get(key);
	}

	@Override
	public String getKey(IOSyncable<ByteBuf, ByteBuf> syncable, SyncSide side)
	{
		return this.getSyncables(side).inverse().get(syncable);
	}

	@Override
	public void write(ByteBuf output)
	{
		
	}

	@Override
	public void read(ByteBuf input)
	{
		
	}

}
