package com.gildedgames.playerhook.common.util;

import io.netty.buffer.ByteBuf;

public interface ISyncable
{

	void writeServerData(ByteBuf buf);

	void readServerData(ByteBuf buf);
	
	void writeClientData(ByteBuf buf);
	
	void readClientData(ByteBuf buf);

	void markDirty();

	void markClean();

	boolean isDirty();

}
