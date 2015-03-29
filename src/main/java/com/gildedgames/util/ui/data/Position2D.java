package com.gildedgames.util.ui.data;

public class Position2D
{
	
	protected final float x, y;
	
	public Position2D()
	{
		this(0, 0);
	}
	
	public Position2D(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Position2D clone()
	{
		return new Position2D(this.x, this.y);
	}
	
	public Position2D withAddedX(float x)
	{
		return this.withAdded(x, 0);
	}
	
	public Position2D withAddedY(float y)
	{
		return this.withAdded(0, y);
	}
	
	public Position2D withAdded(float x, float y)
	{
		float newX = this.x + x;
		float newY = this.y + y;
		
		return new Position2D(newX, newY);
	}
	
	public Position2D withAdded(Position2D pos)
	{
		return this.withAdded(pos.getX(), pos.getY());
	}
	
	public Position2D withX(float x)
	{
		return new Position2D(x, this.y);
	}
	
	public Position2D withY(float y)
	{
		return new Position2D(this.x, y);
	}
	
	public float getX()
	{
		return this.x;
	}
	
	public float getY()
	{
		return this.y;
	}
	
	@Override
	public String toString()
	{
		return "Position() X: '" + this.x + "', Y: '" + this.y + "'";
	}
	
}
