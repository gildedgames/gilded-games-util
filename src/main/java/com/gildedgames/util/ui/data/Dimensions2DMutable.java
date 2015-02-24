package com.gildedgames.util.ui.data;

public class Dimensions2DMutable extends Dimensions2D
{
	
	protected Position2D position;

	protected float width, height;
	
	protected boolean centeredVertically, centeredHorizontally;
	
	public Dimensions2DMutable(Dimensions2D dim)
	{
		this(dim.getPosition(), dim.getWidth(), dim.getHeight(), dim.isCenteredVertically(), dim.isCenteredHorizontally());
	}

	public Dimensions2DMutable(float width, float height)
	{
		this(new Position2D(0, 0), width, height);
	}
	
	public Dimensions2DMutable(Position2D position, float width, float height)
	{
		this(position, width, height, false);
	}
	
	public Dimensions2DMutable(Position2D position, float width, float height, boolean centered)
	{
		this(position, width, height, centered, centered);
	}

	public Dimensions2DMutable(Position2D position, float width, float height, boolean centeredVertically, boolean centeredHorizontally)
	{
		super(position, width, height, centeredVertically, centeredHorizontally);
		
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
		this.width = width;
		this.height = height;
		
		return this;
	}

	public Dimensions2D copyWith(Position2D position)
	{
		this.position = position;
		
		return this;
	}
	
	public Dimensions2D copyWith(boolean centered)
	{
		return this.copyWith(centered, centered);
	}
	
	public Dimensions2D copyWith(boolean centeredVertically, boolean centeredHorizontally)
	{
		this.centeredVertically = centeredVertically;
		this.centeredHorizontally = centeredHorizontally;
		
		return this;
	}

}
