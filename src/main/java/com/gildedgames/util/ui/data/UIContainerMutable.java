package com.gildedgames.util.ui.data;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.data.Dim2D.ModifierType;
import com.gildedgames.util.ui.util.RectangleElement;

public class UIContainerMutable extends UIContainer
{
	
	public UIContainerMutable(Ui attachedUi)
	{
		super(attachedUi);
	}
	
	public void displayDim(Dim2DHolder holder)
	{
		this.elements.put("displayDim", new RectangleElement(holder, new DrawingData(Color.PINK)));
	}

	public void set(String key, Ui element)
	{
		Dim2DHolder gui = ObjectFilter.getType(element, Dim2DHolder.class);
		Dim2DHolder parentModifier = ObjectFilter.getType(this.getAttachedUi(), Dim2DHolder.class);
		
		element.seekContent().parentUi = this.getAttachedUi();

		if (gui != null && gui.modDim() != null && parentModifier != null)
		{
			gui.modDim().addModifier(parentModifier, ModifierType.POS, ModifierType.SCALE).flush();
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
			
			if (elem == element)
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
