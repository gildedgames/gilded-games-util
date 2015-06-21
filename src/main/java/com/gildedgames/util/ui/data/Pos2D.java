package com.gildedgames.util.ui.data;

public class Pos2D
{
	
	protected final int x, y;
	
	public Pos2D()
	{
		this(0, 0);
	}
	
	public Pos2D(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Pos2D withAddedX(int x)
	{
		return this.withAdded(x, 0);
	}
	
	public Pos2D withAddedY(int y)
	{
		return this.withAdded(0, y);
	}
	
	public Pos2D withAdded(int x, int y)
	{
		int newX = this.x + x;
		int newY = this.y + y;
		
		return new Pos2D(newX, newY);
	}
	
	public Pos2D withAdded(Pos2D pos)
	{
		if (pos == null)
		{
			return this;
		}
		
		return this.withAdded(pos.getX(), pos.getY());
	}
	
	public Pos2D withX(int x)
	{
		return new Pos2D(x, this.y);
	}
	
	public Pos2D withY(int y)
	{
		return new Pos2D(this.x, y);
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
