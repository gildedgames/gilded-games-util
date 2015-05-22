package com.gildedgames.util.ui.data;

public class Position2D
{
	
	protected final int x, y;
	
	public Position2D()
	{
		this(0, 0);
	}
	
	public Position2D(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Position2D clone()
	{
		return new Position2D(this.x, this.y);
	}
	
	public Position2D withAddedX(int x)
	{
		return this.withAdded(x, 0);
	}
	
	public Position2D withAddedY(int y)
	{
		return this.withAdded(0, y);
	}
	
	public Position2D withAdded(int x, int y)
	{
		int newX = this.x + x;
		int newY = this.y + y;
		
		return new Position2D(newX, newY);
	}
	
	public Position2D withAdded(Position2D pos)
	{
		if (pos == null)
		{
			return this;
		}
		
		return this.withAdded(pos.getX(), pos.getY());
	}
	
	public Position2D withX(int x)
	{
		return new Position2D(x, this.y);
	}
	
	public Position2D withY(int y)
	{
		return new Position2D(this.x, y);
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	@Override
	public String toString()
	{
		return "Position() X: '" + this.x + "', Y: '" + this.y + "'";
	}
	
}
