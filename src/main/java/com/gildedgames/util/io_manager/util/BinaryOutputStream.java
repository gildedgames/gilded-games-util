package com.gildedgames.util.io_manager.util;

import java.io.IOException;
import java.io.OutputStream;

public class BinaryOutputStream
{
	
	private static final int BITS_IN_BYTE = 8;
	
	private final OutputStream output;
	
	private int index;
	
	private byte buffer;
	
	public BinaryOutputStream(OutputStream output)
	{
		this.output = output;
		
		this.clearBuffer();
	}
	
	private void clearBuffer()
	{
		if (this.index == 0)
		{
			return;
		}
		
        if (this.index > 0)
        {
        	this.buffer <<= (BITS_IN_BYTE - this.index);
        }
        
        try
        {
        	this.output.write(this.buffer);
        }
        catch (IOException e)
        {
        	e.printStackTrace();
        }
        
        this.index = 0;
		this.buffer = 0;
	}

	public void writeBit(boolean bit)
	{	
		this.buffer <<= 1;
		
        if (bit)
        {
        	this.buffer |= 1;
        }

		this.index++;
		
		if (this.index == BITS_IN_BYTE)
		{
			this.clearBuffer();
		}
	}
	
	public void flush()
	{
		this.clearBuffer();
		
		try
		{
			this.output.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void close()
	{
		this.flush();
		
		try
		{
			this.output.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
