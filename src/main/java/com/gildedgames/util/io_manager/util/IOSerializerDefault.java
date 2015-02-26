package com.gildedgames.util.io_manager.util;

import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.constructor.DefaultConstructor;
import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IOData;
import com.gildedgames.util.io_manager.overhead.ByteChunkPool;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IOSerializer;
import com.google.common.base.Optional;

import java.io.IOException;

public class IOSerializerDefault implements IOSerializer
{

	private final static int BUFFER_SIZE = 8192;

	private final static DefaultConstructor defaultConstructor = new DefaultConstructor();

	private final IOManager parentManager;

	private final static String METADATA_KEY = "metaClassq";

	public IOSerializerDefault(IOManager parentManager)
	{
		this.parentManager = parentManager;
	}

	@Override
	public IOManager getManager()
	{
		return this.parentManager;
	}
	
	@Override
	public <I, O, DATA extends IOData<I, O>> DATA readData(ByteChunkPool chunkPool, DATA data, IOFactory<I, O> factory, IConstructor... constructors) throws IOException
	{
		data.setSubData(this.readSubData(chunkPool, factory));

		this.readMainData(chunkPool, data, factory, constructors);

		return data;
	}

	@Override
	public <I, O, DATA extends IOData<I, O>> void writeData(ByteChunkPool chunkPool, DATA data, IOFactory<I, O> factory) throws IOException
	{
		this.writeSubData(chunkPool, data, factory);

		this.writeMainData(chunkPool, data, factory);
	}
	
	@Override
	public <I, O, DATA extends IOData<I, O>> DATA readSubData(ByteChunkPool chunkPool, IOFactory<I, O> factory) throws IOException
	{
		DATA readMetadata = null;
		
		IOBridge io = factory.createInputBridge(factory.createInput(chunkPool.getChunk("subDataMetadata")));

		int metadataCount = io.getInteger("subDataCount");
		
		for (int count = 0; count < metadataCount; count++)
		{
			I input = factory.createInput(chunkPool.getChunk("subData" + count));
			
			IOBridge inputBridge = factory.createInputBridge(input);

			Class<?> clazz = inputBridge.getSerializedClass(METADATA_KEY + count);
			
			@SuppressWarnings("unchecked")
			DATA subData = (DATA) IOCore.io().create(clazz, defaultConstructor);

			if (readMetadata != null)
			{
				readMetadata.setSubData(subData);
			}

			subData.read(input);

			readMetadata = subData;
		}

		return readMetadata;
	}
	
	@Override
	public <I, O, DATA extends IOData<I, O>> void writeSubData(ByteChunkPool chunkPool, DATA data, IOFactory<I, O> factory) throws IOException
	{
		Optional<IOData<I, O>> metadata = data.getSubData();

		int metadataCount = 0;
		
		while (metadata != null && metadata.isPresent())
		{
			metadata = metadata.get().getSubData();
			
			metadataCount++;
		}
		
		IOBridge io = factory.createOutputBridge(factory.createOutput());
		
		io.setInteger("subDataCount", metadataCount);
		
		metadata = data.getSubData();
		
		chunkPool.setChunk("subDataMetadata", io.getBytes());
		
		for (int count = 0; count < metadataCount; count++)
		{
			IOData<I, O> metadataFile = metadata.get();

			final O output = factory.createOutput();
			
			IOBridge outputBridge = factory.createOutputBridge(output);
			
			outputBridge.setSerializedClass(METADATA_KEY + count, metadataFile.getClass());
			
			metadataFile.write(output);
			
			chunkPool.setChunk("subData" + count, outputBridge.getBytes());

			metadata = metadataFile.getSubData();
		}
	}
	
	private <I, O, DATA extends IOData<I, O>> DATA readMainData(ByteChunkPool chunkPool, DATA data, IOFactory<I, O> factory, IConstructor... constructors) throws IOException
	{
		I input = factory.createInput(chunkPool.getChunk("mainData"));

		data.read(input);

		return data;
	}

	private <I, O, DATA extends IOData<I, O>> void writeMainData(ByteChunkPool chunkPool, DATA data, IOFactory<I, O> factory) throws IOException
	{
		final O output = factory.createOutput();

		IOBridge outputBridge = factory.createOutputBridge(output);
		
		data.write(output);

		chunkPool.setChunk("mainData", outputBridge.getBytes());
	}


}
