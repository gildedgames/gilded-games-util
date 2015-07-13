package com.gildedgames.util.ui.data;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.util.RectangleElement;

public class UiContainerMutable extends UiContainer
{

	public UiContainerMutable()
	{
		super();
	}

	protected UiContainerMutable(UiContainer parent)
	{
		super(parent);
	}
	
	public void displayDim(Dim2DHolder holder)
	{
		this.elements.put("displayDim", new RectangleElement(holder, new DrawingData(Color.PINK)));
	}

	public void set(String key, Ui element)
	{
		this.elements.put(key, element);
	}

	public void remove(String key)
	{
		this.elements.remove(key);
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

	public UiContainer immutable()
	{
		return this.clone();
	}

	@Override
	public UiContainer clone()
	{
		UiContainerMutable clone = new UiContainerMutable();

		clone.parent = this.parent;
		clone.elements = new LinkedHashMap<String, Ui>(this.elements);

		return clone;
	}

}
