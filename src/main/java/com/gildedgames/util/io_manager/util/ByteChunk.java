package com.gildedgames.util.io_manager.util;


public class ByteChunk
{
	
	private byte[] data;
	
	private int length;
	
	private String key;
	
	public ByteChunk(String key, byte[] bytes)
	{
		this.key = key;
		this.data = bytes;
	}
	
	public ByteChunk(String key, int length)
	{
		this.key = key;
		this.length = length;
	}
	
	public boolean hasBytes()
	{
		return this.getBytes() != null;
	}
	
	public String getKey()
	{
		return this.key;
	}
	
	public byte[] getBytes()
	{
		return this.data;
	}
	
	public void setBytes(byte[] bytes)
	{
		this.data = bytes;
	}
	
	public int getLength()
	{
		if (this.getBytes() == null)
		{
			return this.length;
		}
		
		return this.getBytes().length;
	}

}
