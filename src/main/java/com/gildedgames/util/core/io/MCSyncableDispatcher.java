package com.gildedgames.util.core.io;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.io_manager.io.IOSyncable;
import com.gildedgames.util.io_manager.io.IOSyncable.SyncSide;
import com.gildedgames.util.io_manager.io.IOSyncableDispatcher;

public class MCSyncableDispatcher implements IOSyncableDispatcher<ByteBuf, ByteBuf>
{
	
	private List<Pair<String, IOSyncable<ByteBuf, ByteBuf>>> serverSyncables = new ArrayList<Pair<String, IOSyncable<ByteBuf, ByteBuf>>>();

	private List<Pair<String, IOSyncable<ByteBuf, ByteBuf>>> clientSyncables = new ArrayList<Pair<String, IOSyncable<ByteBuf, ByteBuf>>>();
	
	public MCSyncableDispatcher()
	{
		
	}
	
	private List<Pair<String, IOSyncable<ByteBuf, ByteBuf>>> getSyncables(SyncSide side)
	{
		return side.isServer() ? this.serverSyncables : this.clientSyncables;
	}
	
	@Override
	public void dispatchDirtySyncables(SyncSide from)
	{
		for (Pair<String, IOSyncable<ByteBuf, ByteBuf>> pair : this.getSyncables(from))
		{
			IOSyncable<ByteBuf, ByteBuf> syncable = pair.getRight();
			
			if (syncable != null && syncable.isDirty())
			{
				if (from.isClient())
				{
					UtilCore.NETWORK.sendToServer(new MessageSyncToServer(this, syncable));
				}
				else
				{
					UtilCore.NETWORK.sendToServer(new MessageSyncToClient(this, syncable));
				}
			}
		}
	}

	@Override
	public void track(String key, IOSyncable<ByteBuf, ByteBuf> syncable, SyncSide... sides)
	{
		Pair<String, IOSyncable<ByteBuf, ByteBuf>> pair = new ImmutablePair<String, IOSyncable<ByteBuf, ByteBuf>>(key, syncable);
	
		for (SyncSide side : sides)
		{
			this.getSyncables(side).add(pair);
		}
	}
	
	@Override
	public void untrack(String key, SyncSide side)
	{
		Pair<String, IOSyncable<ByteBuf, ByteBuf>> toUntrack = null;
		
		for (Pair<String, IOSyncable<ByteBuf, ByteBuf>> pair : this.getSyncables(side))
		{
			String syncKey = pair.getLeft();
			
			if (syncKey != null && syncKey.equals(key))
			{
				toUntrack = pair;
				break;
			}
		}
		
		if (toUntrack != null)
		{
			this.getSyncables(side).remove(toUntrack);
		}
	}

	@Override
	public void untrack(IOSyncable<ByteBuf, ByteBuf> syncable, SyncSide side)
	{
		Pair<String, IOSyncable<ByteBuf, ByteBuf>> toUntrack = null;
		
		for (Pair<String, IOSyncable<ByteBuf, ByteBuf>> pair : this.getSyncables(side))
		{
			IOSyncable<ByteBuf, ByteBuf> sync = pair.getRight();
			
			if (sync != null && sync.equals(syncable))
			{
				toUntrack = pair;
				break;
			}
		}
		
		if (toUntrack != null)
		{
			this.getSyncables(side).remove(toUntrack);
		}
	}

	@Override
	public IOSyncable<ByteBuf, ByteBuf> getSyncable(String key, SyncSide side)
	{
		for (Pair<String, IOSyncable<ByteBuf, ByteBuf>> pair : this.getSyncables(side))
		{
			String syncKey = pair.getLeft();
			IOSyncable<ByteBuf, ByteBuf> sync = pair.getRight();
			
			if (syncKey != null && syncKey.equals(key))
			{
				return sync;
			}
		}
		
		return null;
	}

	@Override
	public String getKey(IOSyncable<ByteBuf, ByteBuf> syncable, SyncSide side)
	{
		for (Pair<String, IOSyncable<ByteBuf, ByteBuf>> pair : this.getSyncables(side))
		{
			String syncKey = pair.getLeft();
			IOSyncable<ByteBuf, ByteBuf> sync = pair.getRight();
			
			if (sync != null && sync.equals(syncable))
			{
				return syncKey;
			}
		}
		
		return null;
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
