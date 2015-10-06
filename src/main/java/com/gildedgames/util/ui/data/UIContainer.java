package com.gildedgames.util.ui.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.Ui;

public class UIContainer implements Iterable<Ui>, NBT
{

	protected Map<String, Ui> elements = new LinkedHashMap<String, Ui>();

	protected Ui attachedUi;
	
	protected Ui parentUi;
	
	public UIContainer(Ui attachedUi)
	{
		this.attachedUi = attachedUi;
	}
	
	public Ui getAttachedUi()
	{
		return this.attachedUi;
	}
	
	public Ui getParentUi()
	{
		return this.parentUi;
	}

	public Ui get(String key)
	{
		return this.elements.get(key);
	}

	public <T> T get(String key, Class<? extends T> clazz)
	{
		return (T) this.elements.get(key);
	}

	public Collection<Ui> elements()
	{
		return this.elements.values();
	}

	public Map<String, Ui> map()
	{
		return new LinkedHashMap<String, Ui>(this.elements);
	}

	public boolean isEmpty()
	{
		return this.elements.isEmpty();
	}

	public List<Gui> queryAll(Object... input)
	{
		List<Gui> guis = new ArrayList<Gui>();

		if (this.getAttachedUi() instanceof Gui)
		{
			Gui gui = (Gui)this.getAttachedUi();
			
			if (gui.query(input))
			{
				guis.add(gui);
			}
		}

		for (Gui element : ObjectFilter.getTypesFrom(this.elements(), Gui.class))
		{
			if (element == null)
			{
				continue;
			}
			
			for (UIContainer container : element.seekAllContent())
			{
				guis.addAll(container.queryAll(input));
			}

			if (element.query(input))
			{
				guis.add(element);
			}
		}

		return guis;
	}

	public boolean contains(String key)
	{
		return this.map().containsKey(key);
	}

	public boolean contains(Ui element)
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
		for (Map.Entry<String, Ui> entry : container.map().entrySet())
		{
			String key = entry.getKey();
			Ui element = entry.getValue();

			if (element instanceof Gui)
			{
				Gui view = (Gui) element;

				areas.add(view.getDim());

				this.addCombinedDimensions(view.seekContent(), areas);
			}
		}
	}

	@Override
	public UIContainer clone()
	{
		UIContainer clone = new UIContainer(this.attachedUi);

		clone.elements = new LinkedHashMap<String, Ui>(this.elements);

		return clone;
	}

	public UIContainer merge(UIContainer first, UIContainer... rest)
	{
		return this.merge(false, first, rest);
	}

	public UIContainer merge(boolean newContentFirst, UIContainer first, UIContainer... rest)
	{
		UIContainer clone = this.clone();
		UIContainer merged = new UIContainer(this.attachedUi);

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
	public Iterator<Ui> iterator()
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
	
	public UIContainer getTopParent()
	{
		UIContainer parent = this.getParentUi().seekContent();
		
		while (parent != null)
		{
			if (parent.getParentUi() == null)
			{
				return parent;
			}
			
			if (parent.getParentUi().seekContent() == null)
			{
				return parent;
			}
			
			parent = parent.getParentUi().seekContent();
		}
		
		return null;
	}

}
