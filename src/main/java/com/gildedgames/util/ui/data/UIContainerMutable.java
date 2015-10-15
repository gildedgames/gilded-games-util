package com.gildedgames.util.ui.data;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.data.rect.RectHolder;
import com.gildedgames.util.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.util.ui.util.RectangleElement;

public class UIContainerMutable extends UIContainer
{

	public UIContainerMutable(Ui attachedUi)
	{
		super(attachedUi);
	}

	public void displayDim(RectHolder holder)
	{
		this.elements.put("displayDim", new RectangleElement(holder, new DrawingData(Color.PINK)));
	}

	public void set(String key, Ui element)
	{
		RectHolder gui = ObjectFilter.cast(element, RectHolder.class);
		RectHolder parentModifier = ObjectFilter.cast(this.getAttachedUi(), RectHolder.class);

		element.seekContent().parentUi = this.getAttachedUi();

		if (gui != null && gui.dim().mod() != null && parentModifier != null)
		{
			gui.dim().add(parentModifier, ModifierType.POS, ModifierType.SCALE);
		}

		this.elements.put(key, element);
	}

	public void remove(String key)
	{
		this.elements.remove(key);
	}

	public void remove(Ui element)
	{
		List<String> keysToRemove = new ArrayList<String>();

		for (Map.Entry<String, Ui> entry : this.elements.entrySet())
		{
			String key = entry.getKey();
			Ui elem = entry.getValue();

			if (elem.equals(element))
			{
				keysToRemove.add(key);
			}
		}

		for (String key : keysToRemove)
		{
			this.elements.remove(key);
		}
	}

	public void clear(Class<? extends Ui> classToRemove)
	{
		Map<String, Ui> objectsToRemove = ObjectFilter.getTypesFromValues(this.elements, String.class, classToRemove);

		for (Map.Entry<String, Ui> entry : objectsToRemove.entrySet())
		{
			String key = entry.getKey();

			this.remove(key);
		}
	}

	public void setAll(Map<String, Ui> elements)
	{
		for (Map.Entry<String, Ui> entry : elements.entrySet())
		{
			this.set(entry.getKey(), entry.getValue());
		}
	}

	public void clear()
	{
		this.elements.clear();
	}

	public UIContainer immutable()
	{
		return this.clone();
	}

	@Override
	public UIContainer clone()
	{
		UIContainerMutable clone = new UIContainerMutable(this.attachedUi);

		clone.elements = new LinkedHashMap<String, Ui>(this.elements);

		return clone;
	}

}
