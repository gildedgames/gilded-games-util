package com.gildedgames.util.ui.data;

import java.util.LinkedHashMap;
import java.util.Map;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.ui.common.Ui;

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

	public void setElement(String key, Ui element)
	{
		this.elements.put(key, element);
	}

	public void removeElement(String key)
	{
		this.elements.remove(key);
	}

	public void clear(Class<? extends Ui> classToRemove)
	{
		Map<String, Ui> objectsToRemove = ObjectFilter.getTypesFromValues(this.elements, String.class, classToRemove);

		for (Map.Entry<String, Ui> entry : objectsToRemove.entrySet())
		{
			String key = entry.getKey();

			this.removeElement(key);
		}
	}

	public void setAllElements(Map<String, Ui> elements)
	{
		for (Map.Entry<String, Ui> entry : elements.entrySet())
		{
			this.setElement(entry.getKey(), entry.getValue());
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
