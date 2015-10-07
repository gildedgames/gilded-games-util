package com.gildedgames.util.ui.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.core.ObjectFilter.FilterCondition;
import com.gildedgames.util.ui.input.InputProvider;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class Dim2D
{

	protected final ImmutableList<Modifier> modifiers;

	protected final Pos2D pos;

	protected final Rotation2D rotation;

	protected final float width, height;

	protected final boolean centeredX, centeredY;

	protected final float scale;
	
	private Pos2D modPos;
	
	private Rotation2D modRotation;
	
	private float modWidth, modHeight;
	
	private float modScale;
	
	private List<Dim2DListener> listeners = new ArrayList<Dim2DListener>();

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

		this.rotation = builder.rotation;

		this.modifiers = ImmutableList.copyOf(builder.modifiers);
		
		this.modPos = this.pos;
		
		this.modWidth = this.width;
		this.modHeight = this.height;
		
		this.modRotation = this.rotation;
		
		this.modScale = this.scale;
		
		this.listeners = builder.listeners;
		
		for (Modifier modifier : this.modifiers)
		{
			if (modifier != null && modifier.getHolder() != null)
			{
				modifier.refreshListener(this);
				modifier.listener.notifyChange();
			}
		}
	}

	public ImmutableList<Modifier> getModifiers()
	{
		return this.modifiers;
	}
	
	public ImmutableList<Modifier> getModifiersOfType(ModifierType type)
	{
		return ImmutableList.<Modifier>copyOf(ObjectFilter.<Modifier>getTypesFrom(this.modifiers, new FilterCondition(this.modifiers)
		{

			@Override
			public boolean isType(Object object)
			{
				if (object instanceof Modifier)
				{
					Modifier modifier = (Modifier)object;
					
					if (modifier.getTypes().contains(ModifierType.POS))
					{
						return true;
					}
				}
				
				return false;
			}
			
		}));
	}

	public boolean containsModifier(Dim2DHolder modifier)
	{
		return this.modifiers.contains(modifier);
	}
	
	private void refreshModifiedState()
	{
		this.refreshModifiedState(true);
	}
	
	private void refreshModifiedState(boolean notifyListeners)
	{
		this.modRotation = this.rotation;
		this.modScale = this.scale;
		
		final float offsetX = this.isCenteredX() ? this.width() * this.scale() / 2 : 0;
		final float offsetY = this.isCenteredY() ? this.height() * this.scale() / 2 : 0;
		
		this.modPos = Pos2D.flush(this.pos.x() - offsetX, this.pos.y() - offsetY);
		
		this.modWidth = this.width;
		this.modHeight = this.height;
		
		for (Modifier modifier : this.modifiers)
		{
			if (modifier == null)
			{
				continue;
			}
			
			Dim2DHolder holder = modifier.getHolder();
			
			if (modifier.getTypes().contains(ModifierType.ROTATION) || modifier.getTypes().contains(ModifierType.ALL))
			{
				if (holder.getDim() != null && holder.getDim() != this)
				{
					this.modRotation = this.modRotation.buildWith(holder.getDim().rotation()).addDegrees().flush();
				}
			}
			
			if (modifier.getTypes().contains(ModifierType.SCALE) || modifier.getTypes().contains(ModifierType.ALL))
			{
				if (holder.getDim() != null && holder.getDim() != this)
				{
					this.modScale *= holder.getDim().scale();
				}
			}

			if (holder.getDim() != null && holder.getDim() != this)
			{
				if (modifier.getTypes().contains(ModifierType.X) || modifier.getTypes().contains(ModifierType.POS) || modifier.getTypes().contains(ModifierType.ALL))
				{
					this.modPos = this.modPos.clone().addX(holder.getDim().pos().x()).flush();
				}

				if (modifier.getTypes().contains(ModifierType.Y) || modifier.getTypes().contains(ModifierType.POS) || modifier.getTypes().contains(ModifierType.ALL))
				{
					this.modPos = this.modPos.clone().addY(holder.getDim().pos().y()).flush();
				}
			}
			
			if (modifier.getTypes().contains(ModifierType.WIDTH) || modifier.getTypes().contains(ModifierType.AREA) || modifier.getTypes().contains(ModifierType.ALL))
			{
				if (holder.getDim() != null && holder.getDim() != this)
				{
					this.modWidth += holder.getDim().width();
				}
			}
			
			if (modifier.getTypes().contains(ModifierType.HEIGHT) || modifier.getTypes().contains(ModifierType.AREA) || modifier.getTypes().contains(ModifierType.ALL))
			{
				if (holder.getDim() != null && holder.getDim() != this)
				{
					this.modHeight += holder.getDim().height();
				}
			}
		}
		
		this.modWidth *= this.scale();
		this.modHeight *= this.scale();
		
		if (notifyListeners)
		{
			for (Dim2DListener listener : this.listeners)
			{
				if (listener != null)
				{
					listener.notifyChange();
				}
			}
		}
	}

	public Rotation2D rotation()
	{
		return this.modRotation;
	}

	public float degrees()
	{
		return this.rotation().degrees();
	}

	public Pos2D origin()
	{
		return this.rotation.origin();
	}

	public float scale()
	{
		return this.modScale;
	}

	/**
	 * Altered by factors such as scale and modifiers.
	 */
	public Pos2D pos()
	{
		return this.modPos;
	}

	public Pos2D maxPos()
	{
		return this.pos().clone().add(this.width(), this.height()).flush();
	}

	public float maxX()
	{
		return this.x() + this.width();
	}

	public float maxY()
	{
		return this.y() + this.height();
	}

	/**
	 * Altered by factors such as scale and modifiers.
	 */
	public float x()
	{
		return this.pos().x();
	}

	/**
	 * Altered by factors such as scale and modifiers.
	 */
	public float y()
	{
		return this.pos().y();
	}

	/**
	 * Altered by factors such as scale and modifiers.
	 */
	public float width()
	{
		return this.modWidth;
	}

	/**
	 * Altered by factors such as scale and modifiers.
	 */
	public float height()
	{
		return this.modHeight;
	}

	public boolean isCenteredX()
	{
		return this.centeredX;
	}

	public boolean isCenteredY()
	{
		return this.centeredY;
	}

	public boolean intersects(Pos2D pos)
	{
		return this.intersects(Dim2D.build().pos(pos).flush());
	}

	public boolean intersects(Dim2D dim)
	{
		return dim.maxX() >= this.x() && dim.maxY() >= this.y() && dim.x() < this.maxX() && dim.y() < this.maxY();
	}
	
	public boolean isHovered(InputProvider input)
	{
		Pos2D mousePos = Pos2D.flush(input.getMouseX(), input.getMouseY());
		
		return this.intersects(mousePos);
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

		if (types.contains(InternalModifierType.X_CENTERING) || types.contains(InternalModifierType.CENTERING))
		{
			builder.centerX(false);
		}

		if (types.contains(InternalModifierType.Y_CENTERING) || types.contains(InternalModifierType.CENTERING))
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

				float minX = Math.min(preview.x(), dim.x());
				float minY = Math.min(preview.y(), dim.y());

				float maxX = Math.max(preview.x() + preview.width(), dim.x() + dim.width());
				float maxY = Math.max(preview.y() + preview.height(), dim.y() + dim.height());

				result.pos(Pos2D.flush(minX, minY)).area(maxX - minY, maxY - minY);

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
		
		protected List<Dim2DListener> listeners = new ArrayList<Dim2DListener>();

		protected Pos2D pos = Pos2D.flush();

		protected float width, height;

		protected boolean centeredX, centeredY;

		protected float scale = 1.0F;

		protected Rotation2D rotation = Rotation2D.flush();

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

			this.rotation = dim.rotation;

			this.modifiers = new ArrayList<Modifier>(dim.modifiers);
			this.listeners = new ArrayList<Dim2DListener>(dim.listeners);
		}

		private Dim2DBuilder(Dim2DBuilder builder)
		{
			this.modifiers = builder.modifiers;
			this.listeners = builder.listeners;
			
			this.pos = builder.pos;

			this.width = builder.width;
			this.height = builder.height;

			this.centeredX = builder.centeredX;
			this.centeredY = builder.centeredY;

			this.rotation = builder.rotation;

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
		
		public Dim2DBuilder rotation(Rotation2D rotation)
		{
			this.rotation = rotation;
			
			return this;
		}

		public Dim2DBuilder degrees(float degrees)
		{
			this.rotation = this.rotation.clone().degrees(degrees).flush();

			return this;
		}

		public Dim2DBuilder origin(Pos2D origin)
		{
			this.rotation = this.rotation.clone().origin(origin).flush();

			return this;
		}

		public Dim2DBuilder origin(float x, float y)
		{
			this.rotation = this.rotation.clone().origin(x, y).flush();

			return this;
		}

		public Dim2DBuilder originX(float x)
		{
			this.rotation = this.rotation.clone().originX(x).flush();

			return this;
		}

		public Dim2DBuilder originY(float y)
		{
			this.rotation = this.rotation.clone().originY(y).flush();

			return this;
		}

		public Dim2DBuilder addDegrees(float degrees)
		{
			this.rotation = this.rotation.clone().addDegrees(degrees).flush();

			return this;
		}

		public Dim2DBuilder subtractDegrees(float degrees)
		{
			this.rotation = this.rotation.clone().subtractDegrees(degrees).flush();

			return this;
		}

		public Dim2DBuilder resetPos()
		{
			this.pos = Pos2D.flush();

			return this;
		}

		public Dim2DBuilder scale(float scale)
		{
			this.scale = scale;

			return this;
		}

		public Dim2DBuilder height(float height)
		{
			this.height = height;

			return this;
		}

		public Dim2DBuilder width(float width)
		{
			this.width = width;

			return this;
		}

		public Dim2DBuilder pos(Pos2D position)
		{
			this.pos = position;

			return this;
		}

		public Dim2DBuilder pos(float x, float y)
		{
			this.pos = Pos2D.flush(x, y);
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

		public Dim2DBuilder area(float width, float height)
		{
			return this.width(width).height(height);
		}

		public Dim2DBuilder y(float y)
		{
			return this.pos(this.pos.clone().y(y).flush());
		}

		public Dim2DBuilder x(float x)
		{
			return this.pos(this.pos.clone().x(x).flush());
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

		public Dim2DBuilder addWidth(float width)
		{
			return this.width(this.width + width);
		}

		public Dim2DBuilder addHeight(float height)
		{
			return this.area(this.width, this.height + height);
		}

		public Dim2DBuilder addArea(float width, float height)
		{
			return this.addWidth(width).addHeight(height);
		}

		public Dim2DBuilder addX(float x)
		{
			return this.pos(this.pos.clone().addX(x).flush());
		}

		public Dim2DBuilder addY(float y)
		{
			return this.pos(this.pos.clone().addY(y).flush());
		}

		public Dim2DBuilder addPos(Pos2D pos)
		{
			return this.addX(pos.x()).addY(pos.y());
		}

		/**
		 * @return Returns a clone() of this Dim2D object, unaltered by any modifiers.
		 */
		public Dim2DBuilder clearModifiers(ModifierType... types)
		{
			this.modifiers = ObjectFilter.getTypesFrom(types, new FilterCondition(Arrays.<Object> asList(types))
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

		public Dim2DBuilder addModifier(final Dim2DHolder holder, ModifierType mandatoryType, ModifierType... otherTypes)
		{
			final Modifier modifier = new Modifier(holder, Lists.asList(mandatoryType, otherTypes));

			if (!this.modifiers.contains(modifier))
			{
				this.modifiers.add(modifier);
				
				holder.getDim().listeners.add(new Dim2DListener()
				{

					@Override
					public void notifyChange()
					{
						modifier.getListener().getModifying().refreshModifiedState(false);
					}
					
				});
			}

			return this;
		}

		public Dim2DBuilder removeModifier(final Dim2DHolder holder, ModifierType mandatoryType, ModifierType... otherTypes)
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
		
		public Dim2DBuildWith rotation()
		{
			this.builder.rotation = this.buildWith.getDim().rotation;
			
			return this;
		}

		public Dim2DBuildWith degrees()
		{
			this.builder.rotation = this.builder.rotation.buildWith(this.buildWith.getDim().rotation).degrees().flush();

			return this;
		}

		public Dim2DBuildWith origin()
		{
			this.builder.rotation = this.builder.rotation.clone().origin(this.buildWith.getDim().rotation.origin()).flush();

			return this;
		}

		public Dim2DBuildWith originX()
		{
			this.builder.rotation = this.builder.rotation.clone().originX(this.buildWith.getDim().rotation.origin().x()).flush();

			return this;
		}

		public Dim2DBuildWith originY()
		{
			this.builder.rotation = this.builder.rotation.clone().originY(this.buildWith.getDim().rotation.origin().y()).flush();

			return this;
		}

		public Dim2DBuildWith rotateCW()
		{
			this.builder.rotation = this.builder.rotation.buildWith(this.buildWith.getDim().rotation).addDegrees().flush();

			return this;
		}

		public Dim2DBuildWith rotateCCW()
		{
			this.builder.rotation = this.builder.rotation.buildWith(this.buildWith.getDim().rotation).subtractDegrees().flush();

			return this;
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
			this.builder.pos(this.builder.pos.clone().y(this.buildWith.getDim().pos.y()).flush());

			return this;
		}

		public Dim2DBuildWith x()
		{
			this.builder.pos(this.builder.pos.clone().x(this.buildWith.getDim().pos.x()).flush());

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
			this.builder.pos(this.builder.pos.clone().addX(this.buildWith.getDim().pos.x()).flush());

			return this;
		}

		public Dim2DBuildWith addY()
		{
			this.builder.pos(this.builder.pos.clone().addY(this.buildWith.getDim().pos.y()).flush());

			return this;
		}

		public Dim2DBuildWith addPos()
		{
			this.builder.addX(this.buildWith.getDim().pos.x()).addY(this.buildWith.getDim().pos.y());

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
	
	public static class ModifierDim2DListener implements Dim2DListener
	{
		
		private Dim2D modifying;
		
		public ModifierDim2DListener(Dim2D modifying)
		{
			this.modifying = modifying;
		}

		@Override
		public void notifyChange()
		{
			this.modifying.refreshModifiedState();
		}
		
		public Dim2D getModifying()
		{
			return this.modifying;
		}
		
	}

	public static class Modifier
	{

		private Dim2DHolder holder;

		private ModifierType[] types;
		
		private ModifierDim2DListener listener;

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

		public ModifierDim2DListener getListener()
		{
			return this.listener;
		}
		
		public List<ModifierType> getTypes()
		{
			return Arrays.asList(this.types);
		}
		
		public void refreshListener(Dim2D modifying)
		{
			if (this.listener == null)
			{
				this.listener = new ModifierDim2DListener(modifying);

				this.holder.addDimListener(this.listener);
			}
			else if (this.listener.modifying != modifying)
			{
				this.holder.removeDimListener(this.listener);
				
				this.listener = new ModifierDim2DListener(modifying);
				
				this.holder.addDimListener(this.listener);
			}
		}

		@Override
		public boolean equals(Object obj)
		{
			if (super.equals(obj))
			{
				return true;
			}
			if (!(obj instanceof Modifier))
			{
				return false;
			}
			Modifier modifier = (Modifier) obj;
			return modifier.holder.equals(this.holder) && Arrays.equals(modifier.types, this.types);
		}

		@Override
		public int hashCode()
		{
			return new HashCodeBuilder(97, 37).append(this.holder).append(this.types).toHashCode();
		}

	}

	public static enum ModifierType
	{

		X, Y, POS, HEIGHT, WIDTH, AREA, SCALE, ROTATION, ALL;

	}

	public static enum InternalModifierType
	{
		CENTERING, X_CENTERING, Y_CENTERING, SCALE;
	}

}
