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
	
	public Pos2D addX(int x)
	{
		return this.add(x, 0);
	}
	
	public Pos2D addY(int y)
	{
		return this.add(0, y);
	}
	
	public Pos2D subtractX(int x)
	{
		return this.subtract(x, 0);
	}
	
	public Pos2D subtractY(int y)
	{
		return this.subtract(0, y);
	}
	
	public Pos2D subtract(int x, int y)
	{
		return this.add(-x, -y);
	}
	
	public Pos2D subtract(Pos2D pos)
	{
		if (pos == null)
		{
			return this;
		}
		
		return this.add(-pos.x, -pos.y);
	}

	public Pos2D add(int x, int y)
	{
		int newX = this.x + x;
		int newY = this.y + y;
		
		return new Pos2D(newX, newY);
	}
	
	public Pos2D add(Pos2D pos)
	{
		if (pos == null)
		{
			return this;
		}
		
		return this.add(pos.x, pos.y);
	}
	
	public Pos2D x(int x)
	{
		return new Pos2D(x, this.y);
	}
	
	public Pos2D y(int y)
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
