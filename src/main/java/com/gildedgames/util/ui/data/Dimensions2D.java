package com.gildedgames.util.ui.data;

import java.util.List;

public class Dimensions2D
{
	
	/**
	 * The top-left origin point to orient these dimensions around.
	 */
	protected Position2D origin = new Position2D();

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
	public Position2D getOrigin()
	{
		return this.origin;
	}
	
	/**
	 * The top-left origin point to orient these dimensions around.
	 */
	public void setOrigin(Position2D origin)
	{
		this.origin = origin;
	}
	
	public float getScale()
	{
		return this.scale;
	}

	/**
	 * Altered by factors such as scale and origin.
	 */
	public Position2D getPos()
	{
		return this.origin.withAdded(this.getScaledPos());
	}

	/**
	 * Unaltered by factors such as scale and origin.
	 */
	public Position2D getBasePos()
	{
		return this.position;
	}
	
	/**
	 * Unaltered by the origin.
	 */
	public Position2D getScaledPos()
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
	 * Unaltered by factors such as scale and origin.
	 */
	public int getBaseX()
	{
		return this.position.getX();
	}

	/**
	 * Unaltered by factors such as scale and origin.
	 */
	public int getBaseY()
	{
		return this.position.getY();
	}

	/**
	 * Altered by factors such as scale and origin.
	 */
	public int getWidth()
	{
		return (int) (this.width * this.scale);
	}

	/**
	 * Altered by factors such as scale and origin.
	 */
	public int getHeight()
	{
		return (int) (this.height * this.scale);
	}
	
	/**
	 * Unaltered by factors such as scale and origin.
	 */
	public int getBaseWidth()
	{
		return this.width;
	}

	/**
	 * Unaltered by factors such as scale and origin.
	 */
	public int getBaseHeight()
	{
		return this.height;
	}

	public boolean isCenteredX()
	{
		return this.centeredX;
	}

	public boolean isCenteredY()
	{
		return this.centeredY;
	}

	@Override
	public Dimensions2D clone()
	{
		return new Dimensions2D(this.position, this.width, this.height, this.scale, this.centeredX, this.centeredY);
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
				boolean changed = false;

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
