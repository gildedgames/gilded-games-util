package com.gildedgames.util.ui.data.rect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.core.ObjectFilter.FilterCondition;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.data.Rotation2D;
import com.gildedgames.util.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.util.ui.input.InputProvider;
import com.google.common.collect.Lists;

/**
 * A wrapper around a Rect object to provide a modified state via RectModifiers.
 * @author Brandon Pearce
 */
public class ModDim2D implements Rect
{

	private List<RectModifier> modifiers = new ArrayList<RectModifier>();

	private List<RectListener> listeners = new ArrayList<RectListener>();

	List<ModifierType> modsDisabled = new ArrayList<ModifierType>();

	/**
	 * Originalstate: Non-modified rectangle value without modifiers, the base values
	 * modifiedState: original state with modifiers applied.
	 */
	private Rect originalState = Dim2D.flush(), modifiedState = Dim2D.flush();

	private BuildIntoRectHolder buildInto;

	private RectListener ourListener;

	private boolean preventRecursion = false;

	public ModDim2D()
	{
		this.buildInto = new BuildIntoRectHolder(this);

		this.ourListener = this.createListener();
	}

	public static ModDim2D build()
	{
		return new ModDim2D();
	}

	public static ModDim2D build(Rect rect)
	{
		ModDim2D dim = new ModDim2D();
		dim.set(rect);
		return dim;
	}

	public static ModDim2D clone(RectHolder owner)
	{
		return owner.dim().clone();
	}

	private RectListener createListener()
	{
		return new RectListener()
		{

			@Override
			public void notifyDimChange()
			{
				ModDim2D.this.refreshModifiedState();
			}

		};
	}

	public Rect originalState()
	{
		return this.originalState;
	}

	private Rect modifiedState()
	{
		for (RectModifier modifier : this.modifiers)
		{
			modifier.modifyingWith().updateState();
		}
		return this.modifiedState;
	}

	public BuildIntoRectHolder mod()
	{
		return this.buildInto;
	}

	public RectBuilder copyRect()
	{
		return new RectBuilder(this);
	}

	@Override
	public ModDim2D clone()
	{
		ModDim2D clone = new ModDim2D();
		clone.set(this);
		return clone;
	}

	public ModDim2D set(RectHolder holder)
	{
		this.set(holder.dim());
		return this;
	}

	public ModDim2D set(Rect dim)
	{
		this.originalState = dim;
		this.refreshModifiedState();
		this.buildInto.set(this.originalState);
		return this;
	}

	public ModDim2D set(ModDim2D modDim)
	{
		this.modifiers = new ArrayList<RectModifier>(modDim.modifiers);
		this.listeners = new ArrayList<RectListener>(modDim.listeners);
		this.originalState = modDim.originalState;
		this.modifiedState = modDim.modifiedState;
		this.buildInto.set(this.originalState);
		return this;
	}

	public boolean areDisabled(ModifierType... mods)
	{
		boolean areDisabled = true;

		for (ModifierType type : mods)
		{
			if (type == ModifierType.ALL)
			{
				return true;
			}

			boolean value = this.modsDisabled.contains(type);

			if (!value)
			{
				areDisabled = false;
			}
		}

		return areDisabled;
	}

	/**
	 * Calculate the values for the modified state of this Dim2D object, based on the provided pool of Modifiers.
	 * @param modifiers
	 */
	protected void refreshModifiedState()
	{
		Rotation2D rotation = this.originalState.rotation();
		float scale = this.originalState.scale();

		Pos2D pos = this.originalState.pos();

		float width = this.originalState.width();
		float height = this.originalState.height();

		for (RectModifier modifier : this.mods())
		{
			if (modifier == null)
			{
				continue;
			}

			RectHolder modifyingWith = modifier.modifyingWith();

			if (modifier.getTypes().contains(ModifierType.ROTATION) || modifier.getTypes().contains(ModifierType.ALL))
			{
				if (modifyingWith.dim() != null && modifyingWith.dim() != this)
				{
					rotation = rotation.buildWith(modifyingWith.dim().rotation()).addDegrees().flush();
				}
			}

			if (modifier.getTypes().contains(ModifierType.SCALE) || modifier.getTypes().contains(ModifierType.ALL))
			{
				if (modifyingWith.dim() != null && modifyingWith.dim() != this)
				{
					scale *= modifyingWith.dim().scale();
				}
			}

			if (modifyingWith.dim() != null && modifyingWith.dim() != this)
			{
				if (modifier.getTypes().contains(ModifierType.POS) || modifier.getTypes().contains(ModifierType.ALL))
				{
					pos = pos.clone().addX(modifyingWith.dim().pos().x()).flush();
				}

				if (modifier.getTypes().contains(ModifierType.POS) || modifier.getTypes().contains(ModifierType.ALL))
				{
					pos = pos.clone().addY(modifyingWith.dim().pos().y()).flush();
				}
			}

			if (modifier.getTypes().contains(ModifierType.AREA) || modifier.getTypes().contains(ModifierType.WIDTH) || modifier.getTypes().contains(ModifierType.ALL))
			{
				if (modifyingWith.dim() != null && modifyingWith.dim() != this)
				{
					width += modifyingWith.dim().width();
				}
			}

			if (modifier.getTypes().contains(ModifierType.AREA) || modifier.getTypes().contains(ModifierType.HEIGHT) || modifier.getTypes().contains(ModifierType.ALL))
			{
				if (modifyingWith.dim() != null && modifyingWith.dim() != this)
				{
					height += modifyingWith.dim().height();
				}
			}
		}

		final float offsetX = this.originalState.isCenteredX() ? width * this.originalState.scale() / 2 : 0;
		final float offsetY = this.originalState.isCenteredY() ? height * this.originalState.scale() / 2 : 0;
		width *= this.originalState.scale();
		height *= this.originalState.scale();

		pos = pos.clone().subtract(offsetX, offsetY).flush();

		this.modifiedState = Dim2D.build(this.originalState).pos(pos).area(width, height).rotation(rotation).scale(scale).flush();

		if (this.preventRecursion)
		{
			return;
		}

		this.preventRecursion = true;

		for (RectListener listener : this.listeners)
		{
			listener.notifyDimChange();
		}

		this.preventRecursion = false;
	}

	public Collection<RectModifier> mods()
	{
		return this.modifiers;
	}

	public boolean containsModifier(RectHolder modifier)
	{
		return this.modifiers.contains(modifier);
	}

	/**
	 * Clears the modifiers that you pass along to the method
	 */
	public ModDim2D clear(final ModifierType... types)
	{
		this.modifiers = ObjectFilter.getTypesFrom(this.modifiers, new FilterCondition<RectModifier>(this.modifiers)
		{
			@Override
			public boolean isType(RectModifier modifier)
			{
				for (ModifierType type : ObjectFilter.getTypesFrom(types, ModifierType.class))
				{
					if (modifier.getTypes().contains(type))
					{
						return false;
					}
				}

				return true;
			}
		});

		this.refreshModifiedState();
		return this;
	}

	public ModDim2D add(final RectHolder modifyingWith, ModifierType mandatoryType, ModifierType... otherTypes)
	{
		if (modifyingWith.dim().equals(this))
		{
			throw new IllegalArgumentException();
		}

		final RectModifier modifier = new RectModifier(modifyingWith, Lists.asList(mandatoryType, otherTypes));

		if (!this.modifiers.contains(modifier))
		{
			this.modifiers.add(modifier);

			modifyingWith.dim().addListener(this.ourListener);

			this.refreshModifiedState();
		}
		return this;
	}

	/**
	 * Removes from this modDim the ModifierTypes given.
	 * @param modifyingWith
	 * @param mandatoryType
	 * @param otherTypes
	 * @return
	 */
	public boolean remove(final RectHolder modifyingWith, ModifierType mandatoryType, ModifierType... otherTypes)
	{
		RectModifier modifier = new RectModifier(modifyingWith, Lists.asList(mandatoryType, otherTypes));

		boolean success = this.modifiers.remove(modifier);

		//True when no remaining modifiers use the given modifyingWith
		boolean removeListener = true;
		for (RectModifier rModifier : this.modifiers)
		{
			if (rModifier.modifyingWith().equals(modifyingWith))
			{
				removeListener = false;
			}
		}

		if (removeListener)
		{
			modifyingWith.dim().removeListener(this.ourListener);
		}

		this.refreshModifiedState();

		return success;
	}

	public void addListener(RectListener listener)
	{
		if (!this.listeners.contains(listener))
		{
			this.listeners.add(listener);
		}
	}

	public boolean removeListener(RectListener listener)
	{
		return this.listeners.remove(listener);
	}

	@Override
	public Rotation2D rotation()
	{
		return this.modifiedState().rotation();
	}

	@Override
	public float degrees()
	{
		return this.modifiedState().degrees();
	}

	@Override
	public Pos2D origin()
	{
		return this.modifiedState().origin();
	}

	@Override
	public float scale()
	{
		return this.modifiedState().scale();
	}

	@Override
	public Pos2D pos()
	{
		return this.modifiedState().pos();
	}

	@Override
	public Pos2D maxPos()
	{
		return this.modifiedState().maxPos();
	}

	@Override
	public float maxX()
	{
		return this.modifiedState().maxX();
	}

	@Override
	public float maxY()
	{
		return this.modifiedState().maxY();
	}

	@Override
	public float x()
	{
		return this.modifiedState().x();
	}

	@Override
	public float y()
	{
		return this.modifiedState().y();
	}

	@Override
	public float width()
	{
		return this.modifiedState().width();
	}

	@Override
	public float height()
	{
		return this.modifiedState().height();
	}

	@Override
	public boolean isCenteredX()
	{
		return this.modifiedState().isCenteredX();
	}

	@Override
	public boolean isCenteredY()
	{
		return this.modifiedState().isCenteredY();
	}

	@Override
	public boolean intersects(Pos2D pos)
	{
		return this.modifiedState().intersects(pos);
	}

	@Override
	public boolean intersects(Rect dim)
	{
		return this.modifiedState().intersects(dim);
	}

	@Override
	public boolean isHovered(InputProvider input)
	{
		return this.modifiedState().isHovered(input);
	}

	@Override
	public RectBuilder rebuild()
	{
		return this.modifiedState().rebuild();
	}

	public ModDim2D disableModifiers(ModifierType... pos)
	{
		if (pos.length == 0)
		{
			this.modsDisabled.add(ModifierType.ALL);
			return this;
		}
		this.modsDisabled.addAll(Arrays.asList(pos));
		return this;
	}

}
