package com.gildedgames.util.ui.data;

import java.util.ArrayList;
import java.util.List;

import scala.actors.threadpool.Arrays;

public class Dim2D
{

	protected List<Dim2DHolder> modifiers = new ArrayList<Dim2DHolder>();

	protected Pos2D position, prevPosition;

	protected int width, height;

	protected boolean centeredX, centeredY;

	protected float scale;

	public Dim2D()
	{
		this(new Pos2D(0, 0), 0, 0, 1.0F, false, false);
	}

	public Dim2D(Dim2D dim)
	{
		this();
		
		this.set(dim);
	}

	private Dim2D(Pos2D position, int width, int height, float scale, boolean centeredVertically, boolean centeredHorizontally)
	{
		this.position = position;
		this.prevPosition = this.position.clone();

		this.width = width;
		this.height = height;

		this.scale = scale;

		this.centeredX = centeredVertically;
		this.centeredY = centeredHorizontally;
	}
	
	public List<Dim2DHolder> getModifiers()
	{
		return new ArrayList<Dim2DHolder>(this.modifiers);
	}

	public void clearModifiers()
	{
		this.modifiers.clear();
	}
	
	public void addModifier(Dim2DHolder modifier)
	{
		if (this.modifiers.contains(modifier))
		{
			return;
		}
		
		this.modifiers.add(modifier);
	}

	public void removeModifier(Dim2DHolder modifier)
	{
		this.modifiers.remove(modifier);
	}
	
	public boolean containsModifier(Dim2DHolder modifier)
	{
		return this.modifiers.contains(modifier);
	}
	
	/**
	 * @return Returns a clone() of this Dim2D object, unaltered by any modifiers.
	 */
	public Dim2D withoutModifiers()
	{
		Dim2D clone = this.clone();
		
		clone.modifiers = new ArrayList<Dim2DHolder>();
		
		return clone;
	}

	public float getScale()
	{
		float modifiedScale = this.scale;
		
		for (Dim2DHolder modifier : this.modifiers)
		{
			if (modifier != null && modifier.getDim() != null && modifier.getDim() != this)
			{
				modifiedScale *= modifier.getDim().getScale();
			}
		}
		
		return modifiedScale;
	}
	
	public Dim2D resetPos()
	{
		this.position = new Pos2D();
		
		return this;
	}

	/**
	 * Altered by factors such as scale and modifiers.
	 */
	public Pos2D getPos()
	{
		return this.getModifiedPos(this.position);
	}
	
	/**
	 * Altered by factors such as scale and modifiers.
	 */
	public Pos2D getPrevPos()
	{
		return this.getModifiedPos(this.prevPosition);
	}

	/**
	 * Unaltered by modifiers.
	 */
	private Pos2D getScaledPos(Pos2D pos)
	{
		int offsetX = (int) (this.isCenteredY() ? this.getWidth() * this.getScale() / 2 : 0);
		int offsetY = (int) (this.isCenteredX() ? this.getHeight() * this.getScale() / 2 : 0);

		return new Pos2D(pos.getX() - offsetX, pos.getY() - offsetY);
	}
	
	private Pos2D getModifiedPos(Pos2D pos)
	{
		Pos2D modifiedPos = this.getScaledPos(pos);
		
		for (Dim2DHolder modifier : this.modifiers)
		{
			if (modifier != null && modifier.getDim() != null && modifier.getDim() != this)
			{
				modifiedPos = modifiedPos.withAdded(modifier.getDim().getPos());
			}
		}
		
		return modifiedPos;
	}
	
	/**
	 * Altered by factors such as scale and modifiers.
	 */
	public int getX()
	{
		return this.getPos().getX();
	}

	/**
	 * Altered by factors such as scale and modifiers.
	 */
	public int getY()
	{
		return this.getPos().getY();
	}
	
	/**
	 * Altered by factors such as scale and modifiers.
	 */
	public int getPrevX()
	{
		return this.getPrevPos().getX();
	}

	/**
	 * Altered by factors such as scale and modifiers.
	 */
	public int getPrevY()
	{
		return this.getPrevPos().getY();
	}

	/**
	 * Altered by factors such as scale and modifiers.
	 */
	public int getWidth()
	{
		return (int) (this.width * this.getScale());
	}

	/**
	 * Altered by factors such as scale and modifiers.
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

	public Dim2D set(Dim2D dim)
	{
		if (dim == null)
		{
			return this;
		}
		
		this.position = dim.position;
		this.prevPosition = dim.prevPosition;
		
		this.width = dim.width;
		this.height = dim.height;
		
		this.scale = dim.scale;
		
		this.centeredX = dim.centeredX;
		this.centeredY = dim.centeredY;
		
		this.modifiers = dim.modifiers;
		
		return this;
	}

	@Override
	public Dim2D clone()
	{
		Dim2D clone = new Dim2D(this.position, this.width, this.height, this.scale, this.centeredX, this.centeredY);
		
		clone.prevPosition = this.prevPosition;
		clone.modifiers = this.modifiers;
		
		return clone;
	}

	public Dim2D setScale(float scale)
	{
		this.scale = scale;

		return this;
	}

	public Dim2D addScale(float scale)
	{
		this.scale += scale;
		
		return this;
	}

	public Dim2D setHeight(int height)
	{
		this.height = height;

		return this;
	}
	
	public Dim2D setWidth(int width)
	{
		this.width = width;

		return this;
	}

	public Dim2D setPos(Pos2D position)
	{
		this.prevPosition = this.position.clone();
		this.position = position;

		return this;
	}

	public Dim2D setCentering(boolean centeredX, boolean centeredY)
	{
		return this.setCenteringX(centeredX).setCenteringY(centeredY);
	}

	public Dim2D setCenteringX(boolean centeredX)
	{
		this.centeredX = centeredX;

		return this;
	}

	public Dim2D setCenteringY(boolean centeredY)
	{
		this.centeredY = centeredY;

		return this;
	}

	public Dim2D setCentering(Dim2D copyFrom)
	{
		return this.setCentering(copyFrom.isCenteredX(), copyFrom.isCenteredY());
	}

	public Dim2D addWidth(int width)
	{
		return this.setWidth(this.width + width);
	}

	public Dim2D addHeight(int height)
	{
		return this.setArea(this.width, this.height + height);
	}

	public Dim2D addArea(int width, int height)
	{
		return this.addWidth(width).addHeight(height);
	}

	public Dim2D setArea(int width, int height)
	{
		return this.setWidth(width).setHeight(height);
	}

	public Dim2D addX(int x)
	{
		return this.setPos(this.position.withAdded(x, 0));
	}

	public Dim2D addY(int y)
	{
		return this.setPos(this.position.withAdded(0, y));
	}

	public Dim2D addPos(Pos2D pos)
	{
		return this.addX(pos.getX()).addY(pos.getY());
	}

	public Dim2D setY(int y)
	{
		return this.setPos(this.position.withY(y));
	}

	public Dim2D setX(int x)
	{
		return this.setPos(this.position.withX(x));
	}

	public Dim2D setCentering(boolean centered)
	{
		return this.setCentering(centered, centered);
	}
	
	public static Dim2D combine(Dim2D... dimensions)
	{
		return Dim2D.combine(Arrays.asList(dimensions));
	}

	public static Dim2D combine(List<Dim2D> dimensions)
	{
		Dim2D result = new Dim2D();
		
		float overallScale = 0.0F;
		
		int validDimensions = 0;
		
		int centeredXCount = 0;
		int nonCenteredXCount = 0;
		
		int centeredYCount = 0;
		int nonCenteredYCount = 0;

		for (Dim2D dimension : dimensions)
		{
			if (dimension != null)
			{
				int minX = Math.min(result.getX(), dimension.getX());
				int minY = Math.min(result.getY(), dimension.getY());

				int maxX = Math.max(result.getX() + result.getWidth(), dimension.getX() + dimension.getWidth());
				int maxY = Math.max(result.getY() + result.getHeight(), dimension.getY() + dimension.getHeight());

				result.setPos(new Pos2D(minX, minY)).setArea(maxX - minY, maxY - minY);
				
				overallScale += dimension.getScale();
				
				validDimensions++;
				
				if (dimension.isCenteredX())
				{
					centeredXCount++;
				}
				else
				{
					nonCenteredXCount++;
				}
				
				if (dimension.isCenteredY())
				{
					centeredYCount++;
				}
				else
				{
					nonCenteredYCount++;
				}
			}
		}
		
		result.setScale(overallScale / validDimensions);
		
		if (centeredXCount >= nonCenteredXCount)
		{
			result.setCenteringX(true);
		}
		else
		{
			result.setCenteringX(false);
		}
		
		if (centeredYCount >= nonCenteredYCount)
		{
			result.setCenteringY(true);
		}
		else
		{
			result.setCenteringY(false);
		}

		return result;
	}
	
	public ImmutableDim2D immutable()
	{
		return new ImmutableDim2D(this);
	}
	
	@Override
	public String toString()
	{
		String link = ", ";
		
		return this.getPos().toString() + link + "Area() Width: '" + this.width + "', Height: '" + this.height + "'" + link + "Centered() X: '" + this.centeredX + "', Y: '" + this.centeredY + "'" + link + "Scale() Value: '" + this.scale + "'";
	}

}
