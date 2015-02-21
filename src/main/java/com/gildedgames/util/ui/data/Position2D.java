package com.gildedgames.util.ui.data;

public class Position2D
{
	
	protected final float x, y;
	
	public Position2D(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Position2D add(float x, float y)
	{
		float newX = this.x + x;
		float newY = this.y + y;
		
		return new Position2D(newX, newY);
	}
	
	public float getX()
	{
		return this.x;
	}
	
	public float getY()
	{
		return this.y;
	}
	
}
