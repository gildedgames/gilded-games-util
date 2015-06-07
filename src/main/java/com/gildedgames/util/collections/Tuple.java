package com.gildedgames.util.collections;

public class Tuple<F, S>
{
	private F first;

	private S second;

	public Tuple(F first, S second)
	{
		this.first = first;
		this.second = second;
	}

	public F getFirst()
	{
		return this.first;
	}

	public S getSecond()
	{
		return this.second;
	}
}
