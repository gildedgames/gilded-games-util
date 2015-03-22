package com.gildedgames.util.core.io;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Collections;
import java.util.List;

import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.factory.IOObserver;

public class ByteBufFactory implements IOFactory<ByteBuf, ByteBuf>
{

	@Override
	public ByteBuf createInput(byte[] reading)
	{
		return Unpooled.copiedBuffer(reading);
	}

	@Override
	public ByteBuf createOutput()
	{
		return Unpooled.buffer();
	}

	@Override
	public IOBridge createInputBridge(ByteBuf input)
	{
		return new ByteBufBridge(input);
	}

	@Override
	public IOBridge createOutputBridge(ByteBuf output)
	{
		return new ByteBufBridge(output);
	}

	@Override
	public List<IOObserver<ByteBuf, ByteBuf>> getObservers()
	{
		return Collections.EMPTY_LIST;
	}

}
