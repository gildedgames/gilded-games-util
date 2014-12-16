package com.gildedgames.playerhook.common.util;

import io.netty.buffer.ByteBuf;

public interface ISyncable
{

	void writeToClient(ByteBuf buf);

	void readFromServer(ByteBuf buf);
	
	void writeToServer(ByteBuf buf);
	
	void readFromClient(ByteBuf buf);

	void markDirty();

	void markClean();

	boolean isDirty();

}
