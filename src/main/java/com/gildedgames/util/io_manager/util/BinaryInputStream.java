package com.gildedgames.util.io_manager.util;

import java.io.IOException;
import java.io.InputStream;

public class BinaryInputStream
{
	
	private static final int END_OF_FILE = -1;
	
	private static final int BITS_IN_BYTE = 8;

	private InputStream input;
	
	private byte buffer;
	
	private int index;
	
	public BinaryInputStream(InputStream input)
	{
		this.input = input;
		
		this.fillBuffer();
	}
	
	private void fillBuffer()
	{
		try
		{
			this.buffer = (byte) this.input.read();
			this.index = BITS_IN_BYTE;
		}
        catch (IOException e)
		{
        	this.buffer = END_OF_FILE;
        	this.index = END_OF_FILE;
        }
	}
	
	public boolean isEmpty()
	{
		return this.buffer == END_OF_FILE;
	}
	
    public boolean readBit()
    {
        if (isEmpty())
        {
        	throw new RuntimeException("Reading from empty input stream");
        }
        
        this.index--;
        
        boolean bit = ((this.buffer >> this.index) & 1) == 1;
        
        if (this.index == 0)
        {
        	fillBuffer();
        }
        
        return bit;
    }
	
}
