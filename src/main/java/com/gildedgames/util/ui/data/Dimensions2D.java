package com.gildedgames.util.ui.data;



public class Dimensions2D
{

	protected final Position2D position;

	protected final float width, height;
	
	protected final boolean centeredVertically, centeredHorizontally;

	public Dimensions2D(float width, float height)
	{
		this(new Position2D(0, 0), width, height);
	}
	
	public Dimensions2D(Position2D position, float width, float height)
	{
		this(position, width, height, false);
	}
	
	public Dimensions2D(Position2D position, float width, float height, boolean centered)
	{
		this(position, width, height, centered, centered);
	}

	public Dimensions2D(Position2D position, float width, float height, boolean centeredVertically, boolean centeredHorizontally)
	{
		this.width = width;
		this.height = height;

		this.position = position;
		
		this.centeredVertically = centeredVertically;
		this.centeredHorizontally = centeredHorizontally;
	}

	public Position2D getPosition()
	{
		return this.position;
	}

	public float getX()
	{
		return this.position.getX();
	}

	public float getY()
	{
		return this.position.getY();
	}

	public float getWidth()
	{
		return this.width;
	}

	public float getHeight()
	{
		return this.height;
	}
	
	public boolean isCenteredVertically()
	{
		return this.centeredVertically;
	}
	
	public boolean isCenteredHorizontally()
	{
		return this.centeredHorizontally;
	}

	public Dimensions2D copyWith(float width, float height)
	{
		return new Dimensions2D(this.position, width, height);
	}

	public Dimensions2D copyWith(Position2D position)
	{
		return new Dimensions2D(position, this.width, this.height);
	}
	
	public Dimensions2D copyWith(boolean centered)
	{
		return this.copyWith(centered, centered);
	}
	
	public Dimensions2D copyWith(boolean centeredVertically, boolean centeredHorizontally)
	{
		return new Dimensions2D(this.position, this.width, this.height, centeredVertically, centeredHorizontally);
	}

}
