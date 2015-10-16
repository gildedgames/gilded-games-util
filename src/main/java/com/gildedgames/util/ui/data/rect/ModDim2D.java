package com.gildedgames.util.ui.data.rect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.core.ObjectFilter.FilterCondition;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.data.Rotation2D;
import com.gildedgames.util.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.util.ui.input.InputProvider;

/**
 * A wrapper around a Rect object to provide a modified state via RectModifiers.
 * @author Brandon Pearce
 */
public class ModDim2D implements Rect
{

	private List<RectModifier> modifiers = new ArrayList<RectModifier>();

	private List<RectListener> listeners = new ArrayList<RectListener>();

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
			public void notifyDimChange(List<ModifierType> types)
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

	/**
	 * Calculate the values for the modified state of this Dim2D object, based on the provided pool of Modifiers.
	 * @param modifiers
	 */
	protected void refreshModifiedState()
	{
		Rect oldModifiedState = this.modifiedState;
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

			if (modifyingWith.dim() == null || modifyingWith.dim() == this)
			{
				continue;
			}

			if (modifier.getType().equals(ModifierType.ROTATION))
			{
				rotation = rotation.buildWith(modifyingWith.dim().rotation()).addDegrees().flush();
			}

			if (modifier.getType().equals(ModifierType.SCALE))
			{
				scale *= modifyingWith.dim().scale();
			}

			if (modifier.getType().equals(ModifierType.X))
			{
				pos = pos.clone().addX(modifyingWith.dim().pos().x()).flush();
			}

			if (modifier.getType().equals(ModifierType.Y))
			{
				pos = pos.clone().addY(modifyingWith.dim().pos().y()).flush();
			}

			if (modifier.getType().equals(ModifierType.WIDTH))
			{
				width += modifyingWith.dim().width();
			}

			if (modifier.getType().equals(ModifierType.HEIGHT))
			{
				height += modifyingWith.dim().height();
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

		List<ModifierType> changedTypes = ModDim2D.getChangedTypes(oldModifiedState, this.modifiedState);

		if (changedTypes.isEmpty())
		{
			return;
		}

		for (RectListener listener : this.listeners)
		{
			listener.notifyDimChange(changedTypes);
		}

		this.preventRecursion = false;
	}

	public static List<ModifierType> getChangedTypes(Rect r1, Rect r2)
	{
		List<ModifierType> types = new ArrayList<ModifierType>();
		if (r1.x() != r2.x())
		{
			types.add(ModifierType.X);
		}
		if (r1.y() != r2.y())
		{
			types.add(ModifierType.Y);
		}
		if (r1.width() != r2.width())
		{
			types.add(ModifierType.WIDTH);
		}
		if (r1.height() != r2.height())
		{
			types.add(ModifierType.HEIGHT);
		}
		if (r1.rotation() != r2.rotation())
		{
			types.add(ModifierType.ROTATION);
		}
		return types;
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
		if (types.length == 0)
		{
			for (RectModifier modifier : this.modifiers)
			{
				modifier.modifyingWith().dim().removeListener(this.ourListener);
			}
			this.modifiers.clear();
			this.refreshModifiedState();
			return this;
		}

		List<ModifierType> list = new ArrayList<ModifierType>();
		for (ModifierType type : types)
		{
			list.add(type);
		}

		final List<ModifierType> filteredTypes = this.filterModifierTypes(list);
		this.modifiers = ObjectFilter.getTypesFrom(this.modifiers, new FilterCondition<RectModifier>(this.modifiers)
		{
			@Override
			public boolean isType(RectModifier modifier)
			{
				for (ModifierType type : ObjectFilter.getTypesFrom(filteredTypes, ModifierType.class))
				{
					if (modifier.getType().equals(type))
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

	private List<ModifierType> filterModifierTypes(List<ModifierType> types)
	{
		if (types.contains(ModifierType.AREA))
		{
			types.remove(ModifierType.AREA);
			if (!types.contains(ModifierType.WIDTH))
			{
				types.add(ModifierType.WIDTH);
			}
			if (!types.contains(ModifierType.HEIGHT))
			{
				types.add(ModifierType.HEIGHT);
			}
		}

		if (types.contains(ModifierType.POS))
		{
			types.remove(ModifierType.POS);
			if (!types.contains(ModifierType.X))
			{
				types.add(ModifierType.X);
			}
			if (!types.contains(ModifierType.Y))
			{
				types.add(ModifierType.Y);
			}
		}

		if (types.contains(ModifierType.ALL))
		{
			types.remove(ModifierType.ALL);
			for (ModifierType type : ModifierType.values())
			{
				if (!types.contains(type))
				{
					types.add(type);
				}
			}
		}
		return types;
	}

	public ModDim2D add(final RectHolder modifyingWith, ModifierType mandatoryType, ModifierType... otherTypes)
	{
		if (modifyingWith.dim().equals(this))
		{
			throw new IllegalArgumentException();
		}

		for (ModifierType type : this.filterModifierTypes(this.toList(mandatoryType, otherTypes)))
		{
			final RectModifier modifier = new RectModifier(modifyingWith, type);

			if (!this.modifiers.contains(modifier))
			{
				this.modifiers.add(modifier);

				modifyingWith.dim().addListener(this.ourListener);

				this.refreshModifiedState();
			}
		}
		return this;
	}

	private List<ModifierType> toList(ModifierType manda, ModifierType... types)
	{
		List<ModifierType> list = new ArrayList<ModifierType>();
		list.add(manda);
		for (ModifierType type : types)
		{
			list.add(type);
		}
		return list;
	}

	/**
	 * Removes from this modDim the modifiers using the given 
	 * RectHolder for the given types.
	 * @param modifyingWith
	 * @param mandatoryType
	 * @param otherTypes
	 * @return
	 */
	public boolean remove(final RectHolder modifyingWith, ModifierType mandatoryType, ModifierType... otherTypes)
	{
		boolean success = true;
		outerloop:
		for (ModifierType type : this.filterModifierTypes(this.toList(mandatoryType, otherTypes)))
		{
			Iterator<RectModifier> iter = this.modifiers.iterator();
			while (iter.hasNext())
			{
				RectModifier modifier = iter.next();
				if (modifier.modifyingWith() == modifyingWith && modifier.getType().equals(type))
				{
					iter.remove();
					continue outerloop;
				}
			}
			success = false;
		}

		if (!this.hasModifiersFor(modifyingWith))
		{
			modifyingWith.dim().removeListener(this.ourListener);
		}

		this.refreshModifiedState();

		return success;
	}

	private boolean hasModifiersFor(RectHolder holder)
	{
		for (RectModifier rModifier : this.modifiers)
		{
			if (rModifier.modifyingWith().equals(holder))
			{
				return true;
			}
		}
		return false;
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

	@Override
	public String toString()
	{
		return this.modifiedState.toString();
	}

}
