package com.gildedgames.util.ui.data;

import java.util.List;

public class Dimensions2D
{

	protected Position2D position;

	protected float width, height;
	
	protected boolean centeredVertically, centeredHorizontally;
	
	protected float scale;
	
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
	
	public Position2D getBasePosition()
	{
		return this.position;
	}
	
	public float getX()
	{
		return this.getScaledX();
	}

	public float getY()
	{
		return this.getScaledY();
	}
	
	public float getBaseX()
	{
		return this.position.getX();
	}
	
	public float getBaseY()
	{
		return this.position.getY();
	}

	public float getWidth()
	{
		return this.width * this.scale;
	}

	public float getHeight()
	{
		return this.height * this.scale;
	}
	
	public boolean isCenteredVertically()
	{
		return this.centeredVertically;
	}
	
	public boolean isCenteredHorizontally()
	{
		return this.centeredHorizontally;
	}
	
	public Dimensions2D clone()
	{
		return new Dimensions2D(this.position, this.width, this.height, this.scale, this.centeredVertically, this.centeredHorizontally);
	}
	
	public Dimensions2D withScale(float scale)
	{
		return new Dimensions2D(this.position, this.width, this.height, scale, this.centeredVertically, this.centeredHorizontally);
	}

	public Dimensions2D withArea(float width, float height)
	{
		return new Dimensions2D(this.position, width, height, this.scale, this.centeredVertically, this.centeredHorizontally);
	}
	
	public Dimensions2D withAddedArea(float width, float height)
	{
		return this.withArea(this.width + width, this.height + height);
	}
	
	public Dimensions2D withWidth(float width)
	{
		return this.withArea(width, this.height);
	}

	public Dimensions2D withHeight(float height)
	{
		return this.withArea(this.width, height);
	}
	
	public Dimensions2D withAddedWidth(float width)
	{
		return this.withArea(this.width + width, this.height);
	}

	public Dimensions2D withAddedHeight(float height)
	{
		return this.withArea(this.width, this.height + height);
	}
	
	public Dimensions2D withPos(Position2D position)
	{
		return new Dimensions2D(position, this.width, this.height, this.scale, this.centeredVertically, this.centeredHorizontally);
	}
	
	public Dimensions2D withAddedPos(Position2D pos)
	{
		return this.withPos(new Position2D(this.position.getX() + pos.getX(), this.position.getY() + pos.getY()));
	}
	
	public Dimensions2D withY(float y)
	{
		return this.withPos(new Position2D(this.position.getX(), y));
	}
	
	public Dimensions2D withX(float x)
	{
		return this.withPos(new Position2D(x, this.position.getY()));
	}
	
	public Dimensions2D withAddedX(float x)
	{
		return this.withPos(new Position2D(this.position.getX() + x, this.position.getY()));
	}
	
	public Dimensions2D withAddedY(float y)
	{
		return this.withPos(new Position2D(this.position.getX(), this.position.getY() + y));
	}
	
	public Dimensions2D withCentering(boolean centered)
	{
		return this.withCentering(centered, centered);
	}
	
	public Dimensions2D withCentering(boolean centeredVertically, boolean centeredHorizontally)
	{
		return new Dimensions2D(this.position, this.width, this.height, this.scale, centeredVertically, centeredHorizontally);
	}
	
	public Dimensions2D setScale(float scale)
	{
		this.scale = scale;
		
		return this;
	}
	
	public Dimensions2D setHeight(float height)
	{
		this.height = height;
		
		return this;
	}
	
	public Dimensions2D setWidth(float width)
	{
		this.width = width;
		
		return this;
	}
	
	public Dimensions2D setPos(Position2D position)
	{
		this.position = position;
		
		return this;
	}
	
	public Dimensions2D setCentering(boolean centeredVertically, boolean centeredHorizontally)
	{
		this.centeredVertically = centeredVertically;
		this.centeredHorizontally = centeredHorizontally;
		
		return this;
	}
	
	public Dimensions2D addWidth(float width)
	{
		return this.setWidth(this.width + width);
	}
	
	public Dimensions2D addHeight(float height)
	{
		return this.setArea(this.width, this.height + height);
	}
	
	public Dimensions2D addArea(float width, float height)
	{
		return this.addWidth(width).addHeight(height);
	}

	public Dimensions2D setArea(float width, float height)
	{
		return this.setWidth(width).setHeight(height);
	}

	public Dimensions2D addX(float x)
	{
		return this.setPos(this.position.withAdded(x, 0));
	}
	
	public Dimensions2D addY(float y)
	{
		return this.setPos(this.position.withAdded(0, y));
	}
	
	public Dimensions2D addPos(Position2D pos)
	{
		return this.addX(pos.getX()).addY(pos.getY());
	}
	
	public Dimensions2D setY(float y)
	{
		return this.setPos(this.position.withY(y));
	}
	
	public Dimensions2D setX(float x)
	{
		return this.setPos(this.position.withX(x));
	}

	public Dimensions2D setCentering(boolean centered)
	{
		return this.setCentering(centered, centered);
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
				
				result.setPos(new Position2D(minX, minY)).setArea(maxX - minY, maxY - minY);
			}
		}
		
		return result;
	}

}
