package com.gildedgames.util.ui.data;

import java.util.List;

public class Dimensions2D
{

	protected Position2D position;

	protected float width, height;

	protected boolean centeredX, centeredY;

	protected float scale;

	public Dimensions2D()
	{
		this(new Position2D(0, 0), 0, 0, 1.0F, false, false);
	}

	public Dimensions2D(Dimensions2D dim)
	{
		this(dim.getPosition(), dim.getWidth(), dim.getHeight(), dim.getScale(), dim.isCenteredX(), dim.isCenteredY());
	}

	private Dimensions2D(Position2D position, float width, float height, float scale, boolean centeredVertically, boolean centeredHorizontally)
	{
		this.position = position;

		this.width = width;
		this.height = height;

		this.scale = scale;

		this.centeredX = centeredVertically;
		this.centeredY = centeredHorizontally;
	}

	public float getScale()
	{
		return this.scale;
	}

	private float getScaledX()
	{
		float offset = this.isCenteredY() ? this.getWidth() * this.getScale() / 2 : 0;

		return this.position.getX() - offset;
	}

	private float getScaledY()
	{
		float offset = this.isCenteredX() ? this.getHeight() * this.getScale() / 2 : 0;

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

	public Dimensions2D copyWithScale(float scale)
	{
		return new Dimensions2D(this.position, this.width, this.height, scale, this.centeredX, this.centeredY);
	}

	public Dimensions2D copyWithArea(float width, float height)
	{
		return new Dimensions2D(this.position, width, height, this.scale, this.centeredX, this.centeredY);
	}

	public Dimensions2D copyWithAddedArea(float width, float height)
	{
		return this.copyWithArea(this.width + width, this.height + height);
	}

	public Dimensions2D copyWithWidth(float width)
	{
		return this.copyWithArea(width, this.height);
	}

	public Dimensions2D copyWithHeight(float height)
	{
		return this.copyWithArea(this.width, height);
	}

	public Dimensions2D copyWithAddedWidth(float width)
	{
		return this.copyWithArea(this.width + width, this.height);
	}

	public Dimensions2D copyWithAddedHeight(float height)
	{
		return this.copyWithArea(this.width, this.height + height);
	}

	public Dimensions2D copyWithPos(Position2D position)
	{
		return new Dimensions2D(position, this.width, this.height, this.scale, this.centeredX, this.centeredY);
	}

	public Dimensions2D copyWithAddedPos(Position2D pos)
	{
		return this.copyWithPos(new Position2D(this.position.getX() + pos.getX(), this.position.getY() + pos.getY()));
	}

	public Dimensions2D copyWithY(float y)
	{
		return this.copyWithPos(new Position2D(this.position.getX(), y));
	}

	public Dimensions2D copyWithX(float x)
	{
		return this.copyWithPos(new Position2D(x, this.position.getY()));
	}

	public Dimensions2D copyWithAddedX(float x)
	{
		return this.copyWithPos(new Position2D(this.position.getX() + x, this.position.getY()));
	}

	public Dimensions2D copyWithAddedY(float y)
	{
		return this.copyWithPos(new Position2D(this.position.getX(), this.position.getY() + y));
	}

	public Dimensions2D copyWithCentering(boolean centered)
	{
		return this.copyWithCentering(centered, centered);
	}

	public Dimensions2D copyWithCentering(boolean centeredX, boolean centeredY)
	{
		return new Dimensions2D(this.position, this.width, this.height, this.scale, centeredX, centeredY);
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

	public Dimensions2D setCentering(boolean centeredX, boolean centeredY)
	{
		this.centeredX = centeredX;
		this.centeredY = centeredY;

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
