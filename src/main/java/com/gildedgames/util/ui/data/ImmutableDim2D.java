package com.gildedgames.util.ui.data;

public class ImmutableDim2D extends Dim2D
{
	
	public ImmutableDim2D(Dim2D dim)
	{
		super();
		
		super.set(dim);
	}

	@Override
	public void addModifier(Dim2DHolder modifier)
	{
		
	}
	
	@Override
	public void removeModifier(Dim2DHolder modifier)
	{
		
	}

	@Override
	public Dim2D set(Dim2D dim)
	{
		return this;
	}

	@Override
	public Dim2D setScale(float scale)
	{
		return this;
	}

	@Override
	public Dim2D addScale(float scale)
	{
		return this;
	}
	
	@Override
	public Dim2D setHeight(int height)
	{
		return this;
	}

	@Override
	public Dim2D setWidth(int width)
	{
		return this;
	}

	@Override
	public Dim2D setPos(Pos2D position)
	{
		return this;
	}

	@Override
	public Dim2D setCentering(boolean centeredX, boolean centeredY)
	{
		return this;
	}
	
	@Override
	public Dim2D setCenteringX(boolean centeredX)
	{
		return this;
	}
	
	@Override
	public Dim2D setCenteringY(boolean centeredY)
	{
		return this;
	}
	
	@Override
	public Dim2D setCentering(Dim2D copyFrom)
	{
		return this;
	}

	@Override
	public Dim2D addWidth(int width)
	{
		return this;
	}

	@Override
	public Dim2D addHeight(int height)
	{
		return this;
	}

	@Override
	public Dim2D addArea(int width, int height)
	{
		return this;
	}

	@Override
	public Dim2D setArea(int width, int height)
	{
		return this;
	}

	@Override
	public Dim2D addX(int x)
	{
		return this;
	}

	@Override
	public Dim2D addY(int y)
	{
		return this;
	}

	@Override
	public Dim2D addPos(Pos2D pos)
	{
		return this;
	}

	@Override
	public Dim2D setY(int y)
	{
		return this;
	}

	@Override
	public Dim2D setX(int x)
	{
		return this;
	}

	@Override
	public Dim2D setCentering(boolean centered)
	{
		return this;
	}
	
}
