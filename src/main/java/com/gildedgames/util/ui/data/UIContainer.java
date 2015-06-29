package com.gildedgames.util.ui.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.ui.common.UIElement;
import com.gildedgames.util.ui.common.UIView;
import com.google.common.collect.ImmutableList;

public class UIContainer implements Iterable<UIElement>, NBT
{

	protected Map<String, UIElement> elements = new LinkedHashMap<String, UIElement>();

	protected UIContainer parent;

	public UIContainer()
	{
		
	}
	
	protected UIContainer(UIContainer parent)
	{
		this.parent = parent;
	}
	
	public UIContainer getParent()
	{
		return this.parent;
	}
	
	public UIElement getElement(String key)
	{
		return this.elements.get(key);
	}
	
	public <T extends UIElement> T getElement(String key, Class<? extends T> clazz)
	{
		return (T) this.elements.get(key);
	}
	
	public ImmutableList<UIElement> elements()
	{
		return ImmutableList.copyOf(this.elements.values());
	}
	
	public Map<String, UIElement> map()
	{
		return new LinkedHashMap<String, UIElement>(this.elements);
	}
	
	public boolean isEmpty()
	{
		return this.elements.isEmpty();
	}
	
	public List<UIView> queryAll(Object... input)
	{
		List<UIView> views = new ArrayList<UIView>();

		for (UIView element : ObjectFilter.getTypesFrom(this.elements(), UIView.class))
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
	
	public boolean containsKey(String key)
	{
		return this.map().containsKey(key);
	}

	public boolean containsElement(UIElement element)
	{
		return this.map().containsValue(element);
	}
	
	public int size()
	{
		return this.elements.size();
	}
	
	public Dim2D getCombinedDimensions()
	{
		List<Dim2D> areas = new ArrayList<Dim2D>();

		this.addCombinedDimensions(this, areas);

		return Dim2D.combine(areas);
	}
	
	private void addCombinedDimensions(UIContainer container, List<Dim2D> areas)
	{
		for (Map.Entry<String, UIElement> entry : container.map().entrySet())
		{
			String key = entry.getKey();
			UIElement element = entry.getValue();
			
			if (element instanceof UIView)
			{
				UIView view = (UIView) element;

				areas.add(view.getDim());

				this.addCombinedDimensions(view.seekContent(), areas);
			}
		}
	}
	
	public UIContainer clone()
	{
		UIContainer clone = new UIContainer();
		
		clone.parent = this.parent;
		clone.elements = new LinkedHashMap<String, UIElement>(this.elements);
		
		return clone;
	}
	
	public UIContainer merge(UIContainer first, UIContainer... rest)
	{
		return this.merge(false, first, rest);
	}

	public UIContainer merge(boolean newContentFirst, UIContainer first, UIContainer... rest)
	{
		UIContainer clone = this.clone();
		UIContainer merged = new UIContainer();
		
		if (!newContentFirst)
		{
			merged.elements = clone.elements;
		}
		
		merged.elements.putAll(first.elements);
		
		for (UIContainer container : rest)
		{
			merged.elements.putAll(container.elements);
		}
		
		if (newContentFirst)
		{
			merged.elements.putAll(clone.elements);
		}
		
		return merged;
	}
	
	@Override
	public Iterator<UIElement> iterator()
	{
		return this.elements().iterator();
	}
	
	@Override
	public void write(NBTTagCompound output)
	{
		
	}

	@Override
	public void read(NBTTagCompound input)
	{
		
	}

}
