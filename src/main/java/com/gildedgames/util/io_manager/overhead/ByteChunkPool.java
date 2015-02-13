package com.gildedgames.util.io_manager.overhead;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.io_manager.exceptions.ByteChunkKeyTakenException;

public class ByteChunkPool
{

	private List<ByteChunk> chunks = new ArrayList<ByteChunk>();
	
	private int totalSize = 0;
	
	private DataInput input;
	
	private DataOutput output;
	
	public ByteChunkPool(DataOutput output)
	{
		this.output = output;
	}
	
	public ByteChunkPool(DataInput input)
	{
		this.input = input;
	}
	
	public void setChunk(String key, byte[] chunk)
	{
		for (ByteChunk byteChunk : this.chunks)
		{
			if (byteChunk != null && byteChunk.equals(key))
			{
				throw new ByteChunkKeyTakenException(key);
			}
		}
		
		this.chunks.add(new ByteChunk(key, chunk));
	}
	
	public void writeChunks() throws IOException
	{
		this.output.writeInt(this.chunks.size());
		
		for (ByteChunk chunk : this.chunks)
		{
			if (chunk != null)
			{
				this.output.writeUTF(chunk.getKey());
				this.output.writeInt(chunk.getLength());
			}
		}
		
		for (ByteChunk chunk : this.chunks)
		{
			if (chunk != null)
			{
				this.output.write(chunk.getBytes());
			}
		}
	}
	
	public void readChunks() throws IOException
	{	
		int chunkAmount = this.input.readInt();
	
		this.chunks = new ArrayList<ByteChunk>(chunkAmount);
		
		for (int count = 0; count < chunkAmount; count++)
		{
			String key = this.input.readUTF();
			int length = this.input.readInt();
			
			ByteChunk chunk = new ByteChunk(key, length);
			
			this.chunks.add(chunk);
		}
	}
	
	public byte[] getChunk(String key) throws IOException
	{	
		for (ByteChunk chunk : this.chunks)
		{
			if (!chunk.hasBytes())
			{
				byte[] bytes = new byte[chunk.getLength()];
				
				this.input.readFully(bytes);
				
				chunk.setBytes(bytes);	
			}
			
			if (chunk.getKey().equals(key))
			{
				return chunk.getBytes();
			}
		}
		
		return new byte[0];
	}
	
}
