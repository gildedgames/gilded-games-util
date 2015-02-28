package com.gildedgames.util.ui.data;

public class Dimensions2DMutable extends Dimensions2D
{
	
	protected Position2D position;

	protected float width, height, scale;
	
	protected boolean centeredVertically, centeredHorizontally;
	
	public Dimensions2DMutable(Dimensions2D dim)
	{
		this(dim.getPosition(), dim.getWidth(), dim.getHeight(), dim.getScale(), dim.isCenteredVertically(), dim.isCenteredHorizontally());
	}

	public Dimensions2DMutable(Position2D position, float width, float height, float scale, boolean centeredVertically, boolean centeredHorizontally)
	{
		super();
		
		this.width = width;
		this.height = height;

		this.position = position;
		
		this.centeredVertically = centeredVertically;
		this.centeredHorizontally = centeredHorizontally;
	}
	
	@Override
	public float getScale()
	{
		return this.scale;
	}
	
	@Override
	public Position2D getPosition()
	{
		return this.position;
	}

	@Override
	public float getX()
	{
		return this.position.getX();
	}

	@Override
	public float getY()
	{
		return this.position.getY();
	}

	@Override
	public float getWidth()
	{
		return this.width;
	}

	@Override
	public float getHeight()
	{
		return this.height;
	}
	
	@Override
	public boolean isCenteredVertically()
	{
		return this.centeredVertically;
	}
	
	@Override
	public boolean isCenteredHorizontally()
	{
		return this.centeredHorizontally;
	}

	@Override
	public Dimensions2D set(float scale)
	{
		this.scale = scale;
		
		return this;
	}
	
	@Override
	public Dimensions2D set(float width, float height)
	{
		this.width = width;
		this.height = height;
		
		return this;
	}

	@Override
	public Dimensions2D set(Position2D position)
	{
		this.position = position;
		
		return this;
	}
	
	@Override
	public Dimensions2D set(boolean centered)
	{
		return this.set(centered, centered);
	}
	
	@Override
	public Dimensions2D set(boolean centeredVertically, boolean centeredHorizontally)
	{
		this.centeredVertically = centeredVertically;
		this.centeredHorizontally = centeredHorizontally;
		
		return this;
	}

}
