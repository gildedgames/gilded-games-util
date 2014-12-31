package com.gildedgames.util.io_manager.networking;

import io.netty.buffer.ByteBuf;

public interface ISyncable
{
	
	public boolean isDirty();

	public void markDirty();

	public void markClean();

	public void writeToClient(ByteBuf buf);

	public void writeToServer(ByteBuf buf);

	public void readFromClient(ByteBuf buf);

	public void readFromServer(ByteBuf buf);

}
