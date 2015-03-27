package com.gildedgames.util.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gildedgames.util.ui.util.ObjectFilter;

public class UIContainer implements Iterable<UIElement>
{
	
	protected final static ObjectFilter FILTER = new ObjectFilter();

	protected final Map<UIElement, UIContainer> elementContainerMap = new HashMap<UIElement, UIContainer>();

	public void add(UIElement element)
	{
		this.elementContainerMap.put(element, new UIContainer());
		
		if (element instanceof UIHearable)
		{
			UIHearable hearable = (UIHearable)element;
			
			//this.elements.add()
		}
	}

	public void remove(UIElement element)
	{
		this.elementContainerMap.remove(element);
	}

	public void clear()
	{
		this.elementContainerMap.clear();
	}

	public List<UIElement> getElements()
	{
		return new ArrayList<UIElement>(this.elementContainerMap.keySet());
	}
	
	public List<UIContainer> getChildren()
	{
		return new ArrayList<UIContainer>(this.elementContainerMap.values());
	}
	
	/**
	 * Get the internal UIContainer for the selected UIElement
	 * @param element
	 * @return
	 */
	public UIContainer getInternal(UIElement element)
	{
		return this.elementContainerMap.get(element);
	}

	public void clear(Class<? extends UIElement> classToRemove)
	{
		List objectsToRemove = FILTER.getTypesFrom(this.getElements(), classToRemove);

		this.elementContainerMap.keySet().removeAll(objectsToRemove);
	}

	public List<UIView> queryAll(Object... input)
	{
		List<UIView> views = new ArrayList<UIView>();

		for (UIView element : FILTER.getTypesFrom(this.getElements(), UIView.class))
		{
			if (element == null)
			{
				continue;
			}

			if (element.query(input))
			{
				views.add(element);
			}
		}

		return views;
	}

	public boolean contains(UIElement element)
	{
		return this.getElements().contains(element);
	}

	public void addAll(Collection<? extends UIElement> elements)
	{
		for (UIElement element : elements)
		{
			this.elementContainerMap.put(element, new UIContainer());
		}
	}

	public int size()
	{
		return this.elementContainerMap.size();
	}

	public void removeAll(Collection<? extends UIElement> elements)
	{
		for (UIElement element : elements)
		{
			this.elementContainerMap.remove(element);
		}
	}

	@Override
	public Iterator<UIElement> iterator()
	{
		return this.getElements().iterator();
	}

}
