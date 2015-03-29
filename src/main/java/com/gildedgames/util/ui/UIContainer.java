package com.gildedgames.util.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.util.ObjectFilter;

public class UIContainer implements Iterable<UIElement>
{
	
	protected final static ObjectFilter FILTER = new ObjectFilter();

	protected final Map<UIElement, UIContainer> elementContainerMap = new HashMap<UIElement, UIContainer>();

	private UIContainer parent;
	
	public UIContainer()
	{
		
	}
	
	private UIContainer(UIContainer parent)
	{
		this.parent = parent;
	}
	
	public UIContainer getParent()
	{
		return this.parent;
	}
	
	public void add(UIElement element)
	{
		UIContainer container = new UIContainer(this);
		
		this.elementContainerMap.put(element, container);
	}

	public void remove(UIElement element)
	{
		this.elementContainerMap.remove(element);
	}

	public void clear()
	{
		this.elementContainerMap.clear();
	}

	public List<UIElement> values()
	{
		return new ArrayList<UIElement>(this.elementContainerMap.keySet());
	}
	
	public List<UIContainer> children()
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
		List objectsToRemove = FILTER.getTypesFrom(this.values(), classToRemove);

		this.elementContainerMap.keySet().removeAll(objectsToRemove);
	}

	public List<UIView> queryAll(Object... input)
	{
		List<UIView> views = new ArrayList<UIView>();

		for (UIView element : FILTER.getTypesFrom(this.values(), UIView.class))
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
		return this.values().contains(element);
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
	
	public Dimensions2D getCombinedDimensions()
	{
		List<Dimensions2D> areas = new ArrayList<Dimensions2D>();

		this.addCombinedDimensions(this, areas);

		return Dimensions2D.combine(areas);
	}
	
	private void addCombinedDimensions(UIContainer container, List<Dimensions2D> areas)
	{
		for (UIElement element : container.values())
		{
			if (element instanceof UIView)
			{
				UIView view = (UIView) element;

				areas.add(view.getDimensions());
				
				UIContainer internal = container.getInternal(element);
				
				this.addCombinedDimensions(internal, areas);
			}
		}
	}

	@Override
	public Iterator<UIElement> iterator()
	{
		return this.values().iterator();
	}

}
