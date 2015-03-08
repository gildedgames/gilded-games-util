package com.gildedgames.util.ui.data;

public class Position2D
{
	
	protected final float x, y;
	
	public Position2D(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Position2D withAdded(float x, float y)
	{
		float newX = this.x + x;
		float newY = this.y + y;
		
		return new Position2D(newX, newY);
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
		return "Position X: '" + this.x + "', Position Y: '" + this.y + "'";
	}
	
}