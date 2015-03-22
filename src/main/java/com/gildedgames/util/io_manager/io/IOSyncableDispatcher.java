package com.gildedgames.util.io_manager.io;

import com.gildedgames.util.io_manager.io.IOSyncable.SyncSide;


public interface IOSyncableDispatcher<I, O> extends IO<I, O>
{
	
	String getID();

	void dispatchDirtySyncables(SyncSide from);
	
	void track(String key, IOSyncable<I, O> syncable, SyncSide... sides);
	
	void untrack(String key, SyncSide side);
	
	void untrack(IOSyncable<I, O> syncable, SyncSide side);
	
	IOSyncable<I, O> getSyncable(String key, SyncSide side);
	
	String getKey(IOSyncable<I, O> syncable, SyncSide side);
	
}
