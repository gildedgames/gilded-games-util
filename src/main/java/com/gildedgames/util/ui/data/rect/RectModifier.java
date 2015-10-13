package com.gildedgames.util.ui.data.rect;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class RectModifier
{

	private RectHolder modifyWith, modify;

	private ModifierType[] types;

	public RectModifier(RectHolder modify, RectHolder modifyWith, List<ModifierType> types)
	{
		this(modify, modifyWith, types.toArray(new ModifierType[types.size()]));
	}

	public RectModifier(RectHolder modify, RectHolder modifyWith, ModifierType... types)
	{
		this.modify = modify;
		this.modifyWith = modifyWith;
		this.types = types;
	}

	public RectHolder modifyingWith()
	{
		return this.modifyWith;
	}
	
	/**
	 * The Dim2D object this Modifier is applying to.
	 * @return
	 */
	public RectHolder modifiedHolder()
	{
		return this.modify;
	}
	
	public List<ModifierType> getTypes()
	{
		return Arrays.asList(this.types);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (super.equals(obj))
		{
			return true;
		}
		
		if (!(obj instanceof RectModifier))
		{
			return false;
		}
		
		RectModifier modifier = (RectModifier) obj;
		return modifier.modifyWith.equals(this.modifyWith) && Arrays.equals(modifier.types, this.types);
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(97, 37).append(this.modifyWith).append(this.modify).append(this.types).toHashCode();
	}
	
	public static enum ModifierType
	{
		X, Y, POS, WIDTH, HEIGHT, AREA, SCALE, ROTATION, CENTERING, ALL;
	}

}