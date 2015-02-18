package com.gildedgames.util.ui;

public class UIPosition
{
	
	protected final double x, y;
	
	public UIPosition(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public UIPosition add(double x, double y)
	{
		double newX = this.x + x;
		double newY = this.y + y;
		
		return new UIPosition(newX, newY);
	}
	
	public double getX()
	{
		return this.x;
	}
	
	public double getY()
	{
		return this.y;
	}
	
}
