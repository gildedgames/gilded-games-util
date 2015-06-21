package com.gildedgames.util.ui.data;

import java.util.ArrayList;
import java.util.List;

import scala.actors.threadpool.Arrays;

import com.google.common.collect.ImmutableList;

public class Dim2D
{

	private final ImmutableList<Dim2DHolder> modifiers;

	private final Pos2D position;

	private final int width, height;

	private final boolean centeredX, centeredY;

	private final float scale;
	
	public Dim2D()
	{
		this(new Dim2DBuilder());
	}

	public Dim2D(Dim2DBuilder builder)
	{
		this.position = builder.position;

		this.width = builder.width;
		this.height = builder.height;

		this.scale = builder.scale;

		this.centeredX = builder.centeredX;
		this.centeredY = builder.centeredY;

		this.modifiers = ImmutableList.copyOf(builder.modifiers);
	}
	
	public ImmutableList<Dim2DHolder> getModifiers()
	{
		return this.modifiers;
	}
	
	public boolean containsModifier(Dim2DHolder modifier)
	{
		return this.modifiers.contains(modifier);
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

	/**
	 * Altered by factors such as scale and modifiers.
	 */
	public Pos2D getPos()
	{
		return this.getModifiedPos(this.position);
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

	@Override
	public Dim2D clone()
	{
		return new Dim2D(new Dim2DBuilder(this));
	}
	
	public static Dim2DBuilder build()
	{
		return new Dim2DBuilder();
	}
	
	public static Dim2DBuilder build(Dim2DHolder holder)
	{
		return new Dim2DBuilder(holder);
	}
	
	public static Dim2DBuilder build(Dim2D dim)
	{
		return new Dim2DBuilder(dim);
	}
	
	public static Dim2D combine(Dim2D... dimensions)
	{
		return Dim2D.combine(Arrays.asList(dimensions));
	}

	public static Dim2D combine(List<Dim2D> dimensions)
	{
		Dim2DBuilder result = new Dim2DBuilder();
		
		float overallScale = 0.0F;
		
		int validDimensions = 0;
		
		int centeredXCount = 0;
		int nonCenteredXCount = 0;
		
		int centeredYCount = 0;
		int nonCenteredYCount = 0;

		for (Dim2D dim : dimensions)
		{
			if (dim != null)
			{
				Dim2D preview = result.commit();
				
				int minX = Math.min(preview.getX(), dim.getX());
				int minY = Math.min(preview.getY(), dim.getY());

				int maxX = Math.max(preview.getX() + preview.getWidth(), dim.getX() + dim.getWidth());
				int maxY = Math.max(preview.getY() + preview.getHeight(), dim.getY() + dim.getHeight());

				result.pos(new Pos2D(minX, minY)).area(maxX - minY, maxY - minY);
				
				overallScale += dim.getScale();
				
				validDimensions++;
				
				if (dim.isCenteredX())
				{
					centeredXCount++;
				}
				else
				{
					nonCenteredXCount++;
				}
				
				if (dim.isCenteredY())
				{
					centeredYCount++;
				}
				else
				{
					nonCenteredYCount++;
				}
			}
		}
		
		result.scale(overallScale / validDimensions);
		
		if (centeredXCount >= nonCenteredXCount)
		{
			result.centerX(true);
		}
		else
		{
			result.centerX(false);
		}
		
		if (centeredYCount >= nonCenteredYCount)
		{
			result.centerY(true);
		}
		else
		{
			result.centerY(false);
		}

		return result.commit();
	}

	@Override
	public String toString()
	{
		String link = ", ";
		
		return this.getPos().toString() + link + "Area() Width: '" + this.width + "', Height: '" + this.height + "'" + link + "Centered() X: '" + this.centeredX + "', Y: '" + this.centeredY + "'" + link + "Scale() Value: '" + this.scale + "'";
	}
	
	public static class Dim2DBuilder
	{
		
		protected List<Dim2DHolder> modifiers = new ArrayList<Dim2DHolder>();

		protected Pos2D position = new Pos2D();

		protected int width, height;

		protected boolean centeredX, centeredY;

		protected float scale;
		
		public Dim2DBuilder()
		{
			
		}
		
		public Dim2DBuilder(Dim2DHolder holder)
		{
			this(holder.getDim());
		}
		
		public Dim2DBuilder(Dim2D dim)
		{
			this.position = dim.position;

			this.width = dim.width;
			this.height = dim.height;

			this.scale = dim.scale;

			this.centeredX = dim.centeredX;
			this.centeredY = dim.centeredY;

			this.modifiers = new ArrayList<Dim2DHolder>(dim.modifiers);
		}
		
		public Dim2DBuilder resetPos()
		{
			this.position = new Pos2D();
			
			return this;
		}

		public Dim2DBuilder scale(float scale)
		{
			this.scale = scale;

			return this;
		}

		public Dim2DBuilder height(int height)
		{
			this.height = height;

			return this;
		}
		
		public Dim2DBuilder width(int width)
		{
			this.width = width;

			return this;
		}

		public Dim2DBuilder pos(Pos2D position)
		{
			this.position = position;

			return this;
		}

		public Dim2DBuilder center(boolean centeredX, boolean centeredY)
		{
			return this.centerX(centeredX).centerY(centeredY);
		}

		public Dim2DBuilder centerX(boolean centeredX)
		{
			this.centeredX = centeredX;

			return this;
		}

		public Dim2DBuilder centerY(boolean centeredY)
		{
			this.centeredY = centeredY;

			return this;
		}

		public Dim2DBuilder center(Dim2D copyFrom)
		{
			return this.center(copyFrom.isCenteredX(), copyFrom.isCenteredY());
		}

		public Dim2DBuilder area(int width, int height)
		{
			return this.width(width).height(height);
		}

		public Dim2DBuilder y(int y)
		{
			return this.pos(this.position.withY(y));
		}

		public Dim2DBuilder x(int x)
		{
			return this.pos(this.position.withX(x));
		}

		public Dim2DBuilder center(boolean centered)
		{
			return this.center(centered, centered);
		}
		
		public Dim2DBuilder addScale(float scale)
		{
			this.scale += scale;
			
			return this;
		}
		
		public Dim2DBuilder addWidth(int width)
		{
			return this.width(this.width + width);
		}

		public Dim2DBuilder addHeight(int height)
		{
			return this.area(this.width, this.height + height);
		}

		public Dim2DBuilder addArea(int width, int height)
		{
			return this.addWidth(width).addHeight(height);
		}

		public Dim2DBuilder addX(int x)
		{
			return this.pos(this.position.withAdded(x, 0));
		}

		public Dim2DBuilder addY(int y)
		{
			return this.pos(this.position.withAdded(0, y));
		}

		public Dim2DBuilder addPos(Pos2D pos)
		{
			return this.addX(pos.getX()).addY(pos.getY());
		}
		
		/**
		 * @return Returns a clone() of this Dim2D object, unaltered by any modifiers.
		 */
		public Dim2DBuilder clearModifiers()
		{
			this.modifiers = new ArrayList<Dim2DHolder>();
			
			return this;
		}

		public Dim2DBuilder addModifier(Dim2DHolder modifier)
		{
			if (!this.modifiers.contains(modifier))
			{
				this.modifiers.add(modifier);
			}

			return this;
		}

		public Dim2DBuilder removeModifier(Dim2DHolder modifier)
		{
			this.modifiers.remove(modifier);
			
			return this;
		}
		
		public Dim2D commit()
		{
			return new Dim2D(this);
		}
		
	}

}
