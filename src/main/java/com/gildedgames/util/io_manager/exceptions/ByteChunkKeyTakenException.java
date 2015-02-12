package com.gildedgames.util.io_manager.exceptions;


public class ByteChunkKeyTakenException extends RuntimeException
{

	private static final long serialVersionUID = 2107293727864570772L;

	public ByteChunkKeyTakenException(String key)
	{
		super("The key " + key + " has already been taken within a ByteChunkPool! Contact code monkeys!");
	}

}
