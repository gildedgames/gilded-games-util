package com.gildedgames.util.io_manager.io;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

public interface IRawData
{

	public void writeRawData(ByteBuf output) throws IOException;

	public void readRawData(ByteBuf input) throws IOException;

}
