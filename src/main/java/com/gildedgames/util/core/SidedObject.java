package com.gildedgames.util.core;

public class SidedObject<T>
{

	private final T client;

	private final T server;

	public SidedObject(T client, T server)
	{
		this.client = client;
		this.server = server;
	}

	public T instance()
	{
		if (UtilCore.isClient())
		{
			return this.client;
		}
		else
		{
			return this.server;
		}
	}

	public T client()
	{
		return this.client;
	}

	public T server()
	{
		return this.server;
	}

}
