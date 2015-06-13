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

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Tuple)
		{
			Tuple<?, ?> tuple = (Tuple<?, ?>) obj;
			return tuple.first.equals(this.first) && tuple.second.equals(this.second);
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode()
	{
		int hash = 17;
		hash += this.first.hashCode();
		hash = hash * 31 + this.second.hashCode();
		return hash;
	}
}
