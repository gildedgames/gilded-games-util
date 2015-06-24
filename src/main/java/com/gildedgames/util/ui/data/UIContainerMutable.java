package com.gildedgames.util.ui.data;

import java.util.LinkedHashMap;
import java.util.Map;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.ui.common.UIElement;

public class UIContainerMutable extends UIContainer
{
	
	public UIContainerMutable()
	{
		super();
	}
	
	protected UIContainerMutable(UIContainer parent)
	{
		super(parent);
	}
	
	public void setElement(String key, UIElement element)
	{
		this.elements.put(key, element);
	}
	
	public void remove(String key)
	{
		this.elements.remove(key);
	}

	public void clear(Class<? extends UIElement> classToRemove)
	{
		Map<String, UIElement> objectsToRemove = ObjectFilter.getTypesFromValues(this.elements, String.class, classToRemove);

		for (Map.Entry<String, UIElement> entry : objectsToRemove.entrySet())
		{
			String key = entry.getKey();
			
			this.remove(key);
		}
	}

	public void setAllElements(Map<String, UIElement> elements)
	{
		for (Map.Entry<String, UIElement> entry : elements.entrySet())
		{
			this.setElement(entry.getKey(), entry.getValue());
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
		UIContainerMutable clone = new UIContainerMutable();
		
		clone.parent = this.parent;
		clone.elements = new LinkedHashMap<String, UIElement>(this.elements);
		
		return clone;
	}

}
