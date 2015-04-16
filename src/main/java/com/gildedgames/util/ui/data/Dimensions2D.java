package com.gildedgames.util.ui.data;

import java.util.List;

public class Dimensions2D
{
	
	/**
	 * The top-left origin point to orient these dimensions around.
	 */
	protected DimensionsHolder origin;

	protected Position2D position;

	protected int width, height;

	protected boolean centeredX, centeredY;

	protected float scale;

	public Dimensions2D()
	{
		this(new Position2D(0, 0), 0, 0, 1.0F, false, false);
	}

	public Dimensions2D(Dimensions2D dim)
	{
		this(dim.getPos(), dim.getWidth(), dim.getHeight(), dim.getScale(), dim.isCenteredX(), dim.isCenteredY());
	}

	private Dimensions2D(Position2D position, int width, int height, float scale, boolean centeredVertically, boolean centeredHorizontally)
	{
		this.position = position;

		this.width = width;
		this.height = height;

		this.scale = scale;

		this.centeredX = centeredVertically;
		this.centeredY = centeredHorizontally;
	}

	/**
	 * The top-left origin point to orient these dimensions around.
	 */
	public DimensionsHolder getOrigin()
	{
		return this.origin;
	}
	
	/**
	 * The top-left origin point to orient these dimensions around.
	 */
	public Dimensions2D setOrigin(DimensionsHolder origin)
	{
		this.origin = origin;
	
		return this;
	}
	
	/**
	 * @return Returns a clone() of this Dimensions2D object, unaltered by any origin.
	 */
	public Dimensions2D withoutOrigin()
	{
		return this.clone().setOrigin(null);
	}
	
	public float getScale()
	{
		if (this.getOrigin() == null || this.getOrigin().getDimensions() == null)
		{
			return this.scale;
		}
		
		return this.scale * this.getOrigin().getDimensions().getScale();
	}

	/**
	 * Altered by factors such as scale and origin.
	 */
	public Position2D getPos()
	{
		if (this.getOrigin() == null || this.getOrigin().getDimensions() == null)
		{
			return this.getScaledPos();
		}
		
		return this.getScaledPos().withAdded(this.getOrigin().getDimensions().getPos());
	}

	/**
	 * Unaltered by the origin.
	 */
	private Position2D getScaledPos()
	{
		int offsetX = (int) (this.isCenteredY() ? this.getWidth() * this.getScale() / 2 : 0);
		int offsetY = (int) (this.isCenteredX() ? this.getHeight() * this.getScale() / 2 : 0);

		return new Position2D(this.position.getX() - offsetX, this.position.getY() - offsetY);
	}
	
	/**
	 * Altered by factors such as scale and origin.
	 */
	public int getX()
	{
		return this.getPos().getX();
	}

	/**
	 * Altered by factors such as scale and origin.
	 */
	public int getY()
	{
		return this.getPos().getY();
	}

	/**
	 * Altered by factors such as scale and origin.
	 */
	public int getWidth()
	{
		return (int) (this.width * this.getScale());
	}

	/**
	 * Altered by factors such as scale and origin.
	 */
	public int getHeight()
	{
		return (int) (this.height * this.getScale());
	}

	public boolean isCenteredX()
	{
		return this.centeredX;
	}

	public boolean isCenteredY()
	{
		return this.centeredY;
	}
	
	public Dimensions2D set(Dimensions2D dim)
	{
		this.position = dim.position;
		
		this.width = dim.width;
		this.height = dim.height;
		
		this.scale = dim.scale;
		
		this.centeredX = dim.centeredX;
		this.centeredY = dim.centeredY;
		
		this.origin = dim.origin;
		
		return this;
	}

	@Override
	public Dimensions2D clone()
	{
		Dimensions2D clone = new Dimensions2D(this.position, this.width, this.height, this.scale, this.centeredX, this.centeredY);
		
		clone.origin = this.origin;
		
		return clone;
	}

	public Dimensions2D setScale(float scale)
	{
		this.scale = scale;

		return this;
	}

	public Dimensions2D setHeight(int height)
	{
		this.height = height;

		return this;
	}

	public Dimensions2D setWidth(int width)
	{
		this.width = width;

		return this;
	}

	public Dimensions2D setPos(Position2D position)
	{
		this.position = position;

		return this;
	}

	public Dimensions2D setCentering(boolean centeredX, boolean centeredY)
	{
		this.centeredX = centeredX;
		this.centeredY = centeredY;

		return this;
	}
	
	public Dimensions2D setCentering(Dimensions2D copyFrom)
	{
		return this.setCentering(copyFrom.isCenteredX(), copyFrom.isCenteredY());
	}

	public Dimensions2D addWidth(int width)
	{
		return this.setWidth(this.width + width);
	}

	public Dimensions2D addHeight(int height)
	{
		return this.setArea(this.width, this.height + height);
	}

	public Dimensions2D addArea(int width, int height)
	{
		return this.addWidth(width).addHeight(height);
	}

	public Dimensions2D setArea(int width, int height)
	{
		return this.setWidth(width).setHeight(height);
	}

	public Dimensions2D addX(int x)
	{
		return this.setPos(this.position.withAdded(x, 0));
	}

	public Dimensions2D addY(int y)
	{
		return this.setPos(this.position.withAdded(0, y));
	}

	public Dimensions2D addPos(Position2D pos)
	{
		return this.addX(pos.getX()).addY(pos.getY());
	}

	public Dimensions2D setY(int y)
	{
		return this.setPos(this.position.withY(y));
	}

	public Dimensions2D setX(int x)
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
				int minX = Math.min(result.getX(), dimension.getX());
				int minY = Math.min(result.getY(), dimension.getY());

				int maxX = Math.max(result.getX() + result.getWidth(), dimension.getX() + dimension.getWidth());
				int maxY = Math.max(result.getY() + result.getHeight(), dimension.getY() + dimension.getHeight());

				result.setPos(new Position2D(minX, minY)).setArea(maxX - minY, maxY - minY);
			}
		}

		return result;
	}
	
	@Override
	public String toString()
	{
		String link = ", ";
		
		return this.getPos().toString() + link + "Area() Width: '" + this.width + "', Height: '" + this.height + "'" + link + "Centered() X: '" + this.centeredX + "', Y: '" + this.centeredY + "'" + link + "Scale() Value: '" + this.scale + "'";
	}

}
