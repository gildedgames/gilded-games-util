package com.gildedgames.util.ui.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.core.ObjectFilter.FilterCondition;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class Dim2D
{

	protected final ImmutableList<Modifier> modifiers;

	protected final Pos2D pos;

	protected final int width, height;

	protected final boolean centeredX, centeredY;

	protected final float scale;

	private Dim2D()
	{
		this(new Dim2DBuilder());
	}

	public Dim2D(Dim2DBuilder builder)
	{
		this.pos = builder.pos;

		this.width = builder.width;
		this.height = builder.height;

		this.scale = builder.scale;

		this.centeredX = builder.centeredX;
		this.centeredY = builder.centeredY;

		this.modifiers = ImmutableList.copyOf(builder.modifiers);
	}

	public ImmutableList<Modifier> getModifiers()
	{
		return this.modifiers;
	}

	public boolean containsModifier(Dim2DHolder modifier)
	{
		return this.modifiers.contains(modifier);
	}

	public float scale()
	{
		float modifiedScale = this.scale;

		for (Modifier modifier : this.modifiers)
		{
			if (modifier != null && modifier.getTypes().contains(ModifierType.SCALE) || modifier.getTypes().contains(ModifierType.ALL))
			{
				Dim2DHolder holder = modifier.getHolder();

				if (holder.getDim() != null && holder.getDim() != this)
				{
					modifiedScale *= holder.getDim().scale();
				}
			}
		}

		return modifiedScale;
	}

	/**
	 * Altered by factors such as scale and modifiers.
	 */
	public Pos2D pos()
	{
		return this.getModifiedPos(this.pos);
	}

	public Pos2D maxPos()
	{
		return this.pos().add(this.width(), this.height());
	}

	public int maxX()
	{
		return this.x() + this.width();
	}

	public int maxY()
	{
		return this.y() + this.height();
	}

	/**
	 * Unaltered by modifiers.
	 */
	private Pos2D getScaledPos(Pos2D pos)
	{
		int offsetX = (int) (this.isCenteredX() ? this.width() * this.scale() / 2 : 0);
		int offsetY = (int) (this.isCenteredY() ? this.height() * this.scale() / 2 : 0);

		return new Pos2D(pos.getX() - offsetX, pos.getY() - offsetY);
	}

	private Pos2D getModifiedPos(Pos2D pos)
	{
		Pos2D modifiedPos = this.getScaledPos(pos);

		for (Modifier modifier : this.modifiers)
		{
			if (modifier != null)
			{
				Dim2DHolder holder = modifier.getHolder();

				if (holder.getDim() != null && holder.getDim() != this)
				{
					if (modifier.getTypes().contains(ModifierType.X) || modifier.getTypes().contains(ModifierType.POS) || modifier.getTypes().contains(ModifierType.ALL))
					{
						modifiedPos = modifiedPos.addX(holder.getDim().pos().getX());
					}

					if (modifier.getTypes().contains(ModifierType.Y) || modifier.getTypes().contains(ModifierType.POS) || modifier.getTypes().contains(ModifierType.ALL))
					{
						modifiedPos = modifiedPos.addY(holder.getDim().pos().getY());
					}
				}
			}
		}

		return modifiedPos;
	}

	/**
	 * Altered by factors such as scale and modifiers.
	 */
	public int x()
	{
		return this.pos().getX();
	}

	/**
	 * Altered by factors such as scale and modifiers.
	 */
	public int y()
	{
		return this.pos().getY();
	}

	/**
	 * Altered by factors such as scale and modifiers.
	 */
	public int width()
	{
		int modifiedWidth = this.width;

		for (Modifier modifier : this.modifiers)
		{
			if (modifier != null && modifier.getTypes().contains(ModifierType.WIDTH) || modifier.getTypes().contains(ModifierType.AREA) || modifier.getTypes().contains(ModifierType.ALL))
			{
				Dim2DHolder holder = modifier.getHolder();

				if (holder.getDim() != null && holder.getDim() != this)
				{
					modifiedWidth += holder.getDim().width();
				}
			}
		}

		return (int) (modifiedWidth * this.scale());
	}

	/**
	 * Altered by factors such as scale and modifiers.
	 */
	public int height()
	{
		int modifiedHeight = this.height;

		for (Modifier modifier : this.modifiers)
		{
			if (modifier != null && modifier.getTypes().contains(ModifierType.HEIGHT) || modifier.getTypes().contains(ModifierType.AREA) || modifier.getTypes().contains(ModifierType.ALL))
			{
				Dim2DHolder holder = modifier.getHolder();

				if (holder.getDim() != null && holder.getDim() != this)
				{
					modifiedHeight += holder.getDim().height();
				}
			}
		}

		return (int) (modifiedHeight * this.scale());
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
	public Dim2DBuilder clone()
	{
		return new Dim2DBuilder(this);
	}
	
	public Dim2DHolder toHolder()
	{
		return new Dim2DSingle(this);
	}

	public Dim2D withoutModifiers(ModifierType first, ModifierType... rest)
	{
		List<ModifierType> types = Lists.asList(first, rest);

		return Dim2D.build(this).clearModifiers(types.toArray(new ModifierType[types.size()])).flush();
	}

	public Dim2D without(InternalModifierType first, InternalModifierType... rest)
	{
		List<InternalModifierType> types = Lists.asList(first, rest);

		Dim2DBuilder builder = Dim2D.build(this);

		if (types.contains(InternalModifierType.SCALE))
		{
			builder.scale(1.0F);
		}

		if (types.contains(InternalModifierType.X_CENTERING))
		{
			builder.centerX(false);
		}

		if (types.contains(InternalModifierType.Y_CENTERING))
		{
			builder.centerY(false);
		}

		return builder.flush();
	}

	/**
	 * Creates an empty Dim2D object with default values
	 * @return
	 */
	public static Dim2D flush()
	{
		return new Dim2D();
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
				Dim2D preview = result.flush();

				int minX = Math.min(preview.x(), dim.x());
				int minY = Math.min(preview.y(), dim.y());

				int maxX = Math.max(preview.x() + preview.width(), dim.x() + dim.width());
				int maxY = Math.max(preview.y() + preview.height(), dim.y() + dim.height());

				result.pos(new Pos2D(minX, minY)).area(maxX - minY, maxY - minY);

				overallScale += dim.scale();

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

		return result.flush();
	}

	@Override
	public String toString()
	{
		String link = ", ";

		return this.pos().toString() + link + "Area() Width: '" + this.width() + "', Height: '" + this.height() + "'" + link + "Centered() X: '" + this.centeredX + "', Y: '" + this.centeredY + "'" + link + "Scale() Value: '" + this.scale() + "'";
	}

	public static class Dim2DBuilder
	{

		protected List<Modifier> modifiers = new ArrayList<Modifier>();

		protected Pos2D pos = new Pos2D();

		protected int width, height;

		protected boolean centeredX, centeredY;

		protected float scale = 1.0F;

		public Dim2DBuilder()
		{

		}

		public Dim2DBuilder(Dim2DHolder holder)
		{
			this(holder.getDim());
		}

		public Dim2DBuilder(Dim2D dim)
		{
			this.pos = dim.pos;

			this.width = dim.width;
			this.height = dim.height;

			this.scale = dim.scale;

			this.centeredX = dim.centeredX;
			this.centeredY = dim.centeredY;

			this.modifiers = new ArrayList<Modifier>(dim.modifiers);
		}

		private Dim2DBuilder(Dim2DBuilder builder)
		{
			this.modifiers = builder.modifiers;

			this.pos = builder.pos;

			this.width = builder.width;
			this.height = builder.height;

			this.centeredX = builder.centeredX;
			this.centeredY = builder.centeredY;

			this.scale = builder.scale;
		}

		public Dim2DBuildWith buildWith(Dim2DHolder holder)
		{
			return new Dim2DBuildWith(this, holder);
		}

		public Dim2DBuildWith buildWith(Dim2D dim)
		{
			return this.buildWith(new Dim2DSingle(dim));
		}

		public Dim2DBuilder resetPos()
		{
			this.pos = new Pos2D();

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
			this.pos = position;

			return this;
		}

		public Dim2DBuilder pos(int x, int y)
		{
			this.pos = new Pos2D(x, y);
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

		public Dim2DBuilder area(int width, int height)
		{
			return this.width(width).height(height);
		}

		public Dim2DBuilder y(int y)
		{
			return this.pos(this.pos.y(y));
		}

		public Dim2DBuilder x(int x)
		{
			return this.pos(this.pos.x(x));
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
			return this.pos(this.pos.add(x, 0));
		}

		public Dim2DBuilder addY(int y)
		{
			return this.pos(this.pos.add(0, y));
		}

		public Dim2DBuilder addPos(Pos2D pos)
		{
			return this.addX(pos.getX()).addY(pos.getY());
		}

		/**
		 * @return Returns a clone() of this Dim2D object, unaltered by any modifiers.
		 */
		public Dim2DBuilder clearModifiers(ModifierType... types)
		{
			this.modifiers = ObjectFilter.getTypesFrom(types, new FilterCondition(Arrays.<Object>asList(types))
			{

				@Override
				public boolean isType(Object object)
				{
					if (object instanceof Modifier)
					{
						Modifier modifier = (Modifier) object;

						for (ModifierType type : ObjectFilter.getTypesFrom(this.data(), ModifierType.class))
						{
							if (modifier.getTypes().contains(type))
							{
								return true;
							}
						}
					}

					return false;
				}

			});

			return this;
		}

		public Dim2DBuilder addModifier(Dim2DHolder holder, ModifierType mandatoryType, ModifierType... otherTypes)
		{
			Modifier modifier = new Modifier(holder, Lists.asList(mandatoryType, otherTypes));

			if (!this.modifiers.contains(modifier))
			{
				this.modifiers.add(modifier);
			}

			return this;
		}

		public Dim2DBuilder removeModifier(Dim2DHolder holder, ModifierType mandatoryType, ModifierType... otherTypes)
		{
			Modifier modifier = new Modifier(holder, Lists.asList(mandatoryType, otherTypes));

			this.modifiers.remove(modifier);

			return this;
		}

		public Dim2D flush()
		{
			return new Dim2D(this);
		}

	}

	public static class Dim2DBuildWith
	{

		protected Dim2DBuilder builder;

		protected Dim2DHolder buildWith;

		private Dim2DBuildWith(Dim2DBuilder builder, Dim2DHolder buildWith)
		{
			this.builder = builder;
			this.buildWith = buildWith;
		}

		public Dim2DBuildWith scale()
		{
			this.builder.scale = this.buildWith.getDim().scale;

			return this;
		}

		public Dim2DBuildWith height()
		{
			this.builder.height = this.buildWith.getDim().height;

			return this;
		}

		public Dim2DBuildWith width()
		{
			this.builder.width = this.buildWith.getDim().width;

			return this;
		}

		public Dim2DBuildWith pos()
		{
			this.builder.pos = this.buildWith.getDim().pos;

			return this;
		}

		public Dim2DBuildWith center()
		{
			this.builder.centerX(this.buildWith.getDim().centeredX).centerY(this.buildWith.getDim().centeredY);

			return this;
		}

		public Dim2DBuildWith centerX()
		{
			this.builder.centeredX = this.buildWith.getDim().centeredX;

			return this;
		}

		public Dim2DBuildWith centerY()
		{
			this.builder.centeredY = this.buildWith.getDim().centeredY;

			return this;
		}

		public Dim2DBuildWith area()
		{
			this.builder.width(this.buildWith.getDim().width).height(this.buildWith.getDim().height);

			return this;
		}

		public Dim2DBuildWith y()
		{
			this.builder.pos(this.builder.pos.y(this.buildWith.getDim().pos.y));

			return this;
		}

		public Dim2DBuildWith x()
		{
			this.builder.pos(this.builder.pos.x(this.buildWith.getDim().pos.x));

			return this;
		}

		public Dim2DBuildWith addScale()
		{
			this.builder.scale += this.buildWith.getDim().scale;

			return this;
		}

		public Dim2DBuildWith addWidth()
		{
			this.builder.width(this.builder.width + this.buildWith.getDim().width);

			return this;
		}

		public Dim2DBuildWith addHeight()
		{
			this.builder.area(this.builder.width, this.builder.height + this.buildWith.getDim().height);

			return this;
		}

		public Dim2DBuildWith addArea()
		{
			this.builder.addWidth(this.buildWith.getDim().width).addHeight(this.buildWith.getDim().height);

			return this;
		}

		public Dim2DBuildWith addX()
		{
			this.builder.pos(this.builder.pos.add(this.buildWith.getDim().pos.x, 0));

			return this;
		}

		public Dim2DBuildWith addY()
		{
			this.builder.pos(this.builder.pos.add(0, this.buildWith.getDim().pos.y));

			return this;
		}

		public Dim2DBuildWith addPos()
		{
			this.builder.addX(this.buildWith.getDim().pos.getX()).addY(this.buildWith.getDim().pos.getY());

			return this;
		}

		public Dim2DBuilder build()
		{
			return this.builder;
		}
		
		public Dim2D flush()
		{
			return this.builder.flush();
		}

	}

	public static class Dim2DModifier extends Dim2DBuilder
	{

		protected Dim2DHolder holder;

		public Dim2DModifier(Dim2DHolder holder)
		{
			super(holder);

			this.holder = holder;
		}

		@Override
		public Dim2D flush()
		{
			Dim2D commit = super.flush();

			this.holder.setDim(commit);

			return commit;
		}

	}

	public static class Modifier
	{

		private Dim2DHolder holder;

		private ModifierType[] types;

		public Modifier(Dim2DHolder holder, List<ModifierType> types)
		{
			this(holder, types.toArray(new ModifierType[types.size()]));
		}

		public Modifier(Dim2DHolder holder, ModifierType... types)
		{
			this.holder = holder;
			this.types = types;
		}

		public Dim2DHolder getHolder()
		{
			return this.holder;
		}

		public List<ModifierType> getTypes()
		{
			return Arrays.asList(this.types);
		}

	}

	public static enum ModifierType
	{

		X, Y, POS, HEIGHT, WIDTH, AREA, SCALE, ALL;

	}

	public static enum InternalModifierType
	{
		X_CENTERING, Y_CENTERING, SCALE;
	}

}
