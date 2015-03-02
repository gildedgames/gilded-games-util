package com.gildedgames.util.ui.data;

import java.util.List;

public class Dimensions2D
{

	protected final Position2D position;

	protected final float width, height;
	
	protected final boolean centeredVertically, centeredHorizontally;
	
	protected final float scale;
	
	public Dimensions2D()
	{
		this(new Position2D(0, 0), 0, 0, 1.0F, false, false);
	}
	
	public Dimensions2D(Dimensions2D dim)
	{
		this(dim.getPosition(), dim.getWidth(), dim.getHeight(), dim.getScale(), dim.isCenteredVertically(), dim.isCenteredHorizontally());
	}

	private Dimensions2D(Position2D position, float width, float height, float scale, boolean centeredVertically, boolean centeredHorizontally)
	{
		this.position = position;
		
		this.width = width;
		this.height = height;
		
		this.scale = scale;
		
		this.centeredVertically = centeredVertically;
		this.centeredHorizontally = centeredHorizontally;
	}
	
	public float getScale()
	{
		return this.scale;
	}
	
	private float getScaledX()
	{
		float offset = this.isCenteredHorizontally() ? (this.getWidth() * this.getScale()) / 2 : 0;
		
		return this.position.getX() - offset;
	}
	
	private float getScaledY()
	{
		float offset = this.isCenteredVertically() ? (this.getHeight() * this.getScale()) / 2 : 0;
		
		return this.position.getY() - offset;
	}

	public Position2D getPosition()
	{
		return new Position2D(this.getScaledX(), this.getScaledY());
	}
	
	public float getX()
	{
		return this.getScaledX();
	}

	public float getY()
	{
		return this.getScaledY();
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
	
	public Dimensions2D setScale(float scale)
	{
		return new Dimensions2D(this.position, this.width, this.height, scale, this.centeredVertically, this.centeredHorizontally);
	}

	public Dimensions2D setArea(float width, float height)
	{
		return new Dimensions2D(this.position, width, height, this.scale, this.centeredVertically, this.centeredHorizontally);
	}

	public Dimensions2D setPos(Position2D position)
	{
		return new Dimensions2D(position, this.width, this.height, this.scale, this.centeredVertically, this.centeredHorizontally);
	}
	
	public Dimensions2D setCentered(boolean centered)
	{
		return this.setCentered(centered, centered);
	}
	
	public Dimensions2D setCentered(boolean centeredVertically, boolean centeredHorizontally)
	{
		return new Dimensions2D(this.position, this.width, this.height, this.scale, centeredVertically, centeredHorizontally);
	}
	
	public static Dimensions2D combine(List<Dimensions2D> dimensions)
	{
		Dimensions2D result = new Dimensions2D();
		
		for (Dimensions2D dimension : dimensions)
		{
			if (dimension != null)
			{
				boolean changed = false;
				
				float minX = Math.min(result.getX(), dimension.getX());
				float minY = Math.min(result.getY(), dimension.getY());
				
				float maxX = Math.max(result.getX() + result.getWidth(), dimension.getX() + dimension.getWidth());
				float maxY = Math.max(result.getY() + result.getHeight(), dimension.getY() + dimension.getHeight());
				
				result = result.setPos(new Position2D(minX, minY)).setArea(maxX - minY, maxY - minY);
			}
		}
		
		return result;
	}

}
