package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.UIPosition;

public class UIDimensions
{

	protected final UIPosition position;

	protected final double width, height;

	protected final double scale;

	public UIDimensions(double width, double height)
	{
		this(new UIPosition(0, 0), width, height);
	}

	public UIDimensions(UIPosition position, double width, double height)
	{
		this(position, width, height, 1.0D);
	}

	public UIDimensions(UIPosition position, double width, double height, double scale)
	{
		this.width = width;
		this.height = height;

		this.position = position;

		this.scale = scale;
	}

	public UIPosition getPosition()
	{
		return this.position;
	}

	public double getX()
	{
		return this.position.getX();
	}

	public double getY()
	{
		return this.position.getY();
	}

	public double getWidth()
	{
		return this.width;
	}

	public double getHeight()
	{
		return this.height;
	}

	public double getScale()
	{
		return this.scale;
	}

	public UIDimensions copyAndSetDimensions(double width, double height)
	{
		return new UIDimensions(this.position, width, height, this.scale);
	}

	public UIDimensions copyAndSetScale(double scale)
	{
		return new UIDimensions(this.position, this.width, this.height, scale);
	}

	public UIDimensions copyAndSetPosition(UIPosition position)
	{
		return new UIDimensions(position, this.width, this.height, this.scale);
	}

}
