package com.gildedgames.util.ui.data.rect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.core.ObjectFilter.FilterCondition;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.data.Rotation2D;
import com.gildedgames.util.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.util.ui.input.InputProvider;
import com.google.common.collect.ImmutableList;
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
	
	private RectHolder owner;
	
	private Rect originalState, modifiedState;
	
	private BuildIntoRectHolder buildInto;
	
	private RectBuilder builder;
	
	public ModDim2D(RectHolder owner)
	{
		this.owner = owner;
		
		this.buildInto = new BuildIntoRectHolder(this.owner);
		this.builder = Dim2D.build(this.owner);
	}
	
	public BuildIntoRectHolder mod()
	{
		return this.buildInto;
	}
	
	public RectBuilder copy()
	{
		return this.builder;
	}
	
	public void set(RectHolder holder)
	{
		this.set(holder.dim());
	}
	
	public void set(Rect dim)
	{
		this.originalState = dim;
	}
	
	public void set(ModDim2D modDim)
	{
		this.modifiers = new ArrayList<RectModifier>(modDim.modifiers);
		this.listeners = new ArrayList<RectListener>(modDim.listeners);
		this.owner = modDim.owner;
		this.originalState = modDim.originalState;
		this.modifiedState = modDim.modifiedState;
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
	protected void refreshModifiedState(ModDim2D modifiers)
	{
		Rotation2D rotation = this.rotation();
		float scale = this.scale();
		
		final float offsetX = this.isCenteredX() ? this.width() * this.scale() / 2 : 0;
		final float offsetY = this.isCenteredY() ? this.height() * this.scale() / 2 : 0;
		
		Pos2D pos = Pos2D.flush(this.pos().x() - offsetX, this.pos().y() - offsetY);
		
		float width = this.width();
		float height = this.height();
		
		for (RectModifier modifier : modifiers.mods())
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
			
			if (modifier.getTypes().contains(ModifierType.AREA) || modifier.getTypes().contains(ModifierType.ALL))
			{
				if (modifyingWith.dim() != null && modifyingWith.dim() != this)
				{
					width += modifyingWith.dim().width();
				}
			}
			
			if (modifier.getTypes().contains(ModifierType.AREA) || modifier.getTypes().contains(ModifierType.ALL))
			{
				if (modifyingWith.dim() != null && modifyingWith.dim() != this)
				{
					height += modifyingWith.dim().height();
				}
			}
		}
		
		width *= this.scale();
		height *= this.scale();
		
		this.modifiedState = Dim2D.build(this.originalState).pos(pos).area(width,  height).rotation(rotation).scale(scale).flush();
	}
	
	public List<RectModifier> mods()
	{
		return this.modifiers;
	}
	
	public RectHolder getOwner()
	{
		return this.owner;
	}
	
	public boolean containsModifier(RectHolder modifier)
	{
		return this.modifiers.contains(modifier);
	}
	
	public ImmutableList<RectModifier> getModifiersOfType(ModifierType type)
	{
		return ImmutableList.<RectModifier>copyOf(ObjectFilter.<RectModifier>getTypesFrom(this.modifiers, new FilterCondition(this.modifiers)
		{

			@Override
			public boolean isType(Object object)
			{
				if (object instanceof RectModifier)
				{
					RectModifier modifier = (RectModifier)object;
					
					if (modifier.getTypes().contains(ModifierType.POS))
					{
						return true;
					}
				}
				
				return false;
			}
			
		}));
	}
	
	/**
	 * @return Returns a clone() of this Dim2D object, unaltered by any modifiers.
	 */
	public void clear(ModifierType... types)
	{
		this.modifiers = ObjectFilter.getTypesFrom(types, new FilterCondition(Arrays.<Object> asList(types))
		{

			@Override
			public boolean isType(Object object)
			{
				if (object instanceof RectModifier)
				{
					RectModifier modifier = (RectModifier) object;

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
	}

	public boolean add(final RectHolder modifyingWith, ModifierType mandatoryType, ModifierType... otherTypes)
	{
		final RectModifier modifier = new RectModifier(this.owner, modifyingWith, Lists.asList(mandatoryType, otherTypes));

		if (!this.modifiers.contains(modifier))
		{
			boolean result = this.modifiers.add(modifier);
			
			modifyingWith.dim().addListener(new RectListener()
			{

				@Override
				public void notifyDimChange()
				{
					
				}
				
			});
			
			return result;
		}
		
		return false;
	}

	public boolean remove(final RectHolder holder, ModifierType mandatoryType, ModifierType... otherTypes)
	{
		RectModifier modifier = new RectModifier(this.owner, holder, Lists.asList(mandatoryType, otherTypes));

		return this.modifiers.remove(modifier);
	}

	public boolean addListener(RectListener listener)
	{
		return this.listeners.add(listener);
	}
	
	public boolean removeListener(RectListener listener)
	{
		return this.listeners.remove(listener);
	}

	@Override
	public Rotation2D rotation()
	{
		return this.modifiedState.rotation();
	}

	@Override
	public float degrees()
	{
		return this.modifiedState.degrees();
	}

	@Override
	public Pos2D origin()
	{
		return this.modifiedState.origin();
	}

	@Override
	public float scale()
	{
		return this.modifiedState.scale();
	}

	@Override
	public Pos2D pos()
	{
		return this.modifiedState.pos();
	}

	@Override
	public Pos2D maxPos()
	{
		return this.modifiedState.maxPos();
	}

	@Override
	public float maxX()
	{
		return this.modifiedState.maxX();
	}

	@Override
	public float maxY()
	{
		return this.modifiedState.maxY();
	}

	@Override
	public float x()
	{
		return this.modifiedState.x();
	}

	@Override
	public float y()
	{
		return this.modifiedState.y();
	}

	@Override
	public float width()
	{
		return this.modifiedState.width();
	}

	@Override
	public float height()
	{
		return this.modifiedState.height();
	}

	@Override
	public boolean isCenteredX()
	{
		return this.modifiedState.isCenteredX();
	}

	@Override
	public boolean isCenteredY()
	{
		return this.modifiedState.isCenteredY();
	}

	@Override
	public boolean intersects(Pos2D pos)
	{
		return this.modifiedState.intersects(pos);
	}

	@Override
	public boolean intersects(Rect dim)
	{
		return this.modifiedState.intersects(dim);
	}

	@Override
	public boolean isHovered(InputProvider input)
	{
		return this.modifiedState.isHovered(input);
	}

	@Override
	public RectBuilder rebuild()
	{
		return this.modifiedState.rebuild();
	}

}
