package com.gildedgames.util.ui.data.rect;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class RectModifier
{

	private RectHolder modifyWith;

	private ModifierType[] types;

	public RectModifier(RectHolder modifyWith, List<ModifierType> types)
	{
		this(modifyWith, types.toArray(new ModifierType[types.size()]));
	}

	public RectModifier(RectHolder modifyWith, ModifierType... types)
	{
		this.modifyWith = modifyWith;
		this.types = types;
	}

	public RectHolder modifyingWith()
	{
		return this.modifyWith;
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
		return new HashCodeBuilder(97, 37).append(this.modifyWith).append(this.types).toHashCode();
	}

	public static enum ModifierType
	{
		X, Y, POS, WIDTH, HEIGHT, AREA, SCALE, ROTATION, CENTERING, ALL;
	}

}
