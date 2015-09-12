package com.gildedgames.util.ai.searching;

public abstract class Node implements Comparable<Node>
{
	private double g, h;

	public void setG(double g)
	{
		this.g = g;
	}

	public void setH(double h)
	{
		this.h = h;
	}

	public double getF()
	{
		return this.g + this.h;
	}

	public double getG()
	{
		return this.g;
	}

	public double getH()
	{
		return this.h;
	}

	@Override
	public int compareTo(Node o)
	{
		return Double.compare(this.getF(), o.getF());
	}
}
