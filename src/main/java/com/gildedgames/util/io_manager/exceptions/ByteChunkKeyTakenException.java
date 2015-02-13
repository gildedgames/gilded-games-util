package com.gildedgames.util.io_manager.exceptions;


public class ByteChunkKeyTakenException extends RuntimeException
{

	public ByteChunkKeyTakenException(String key)
	{
		super("The key '" + key + "' has already been taken within a ByteChunkPool! Contact code monkeys!");
	}

}
