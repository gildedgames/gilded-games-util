package com.gildedgames.util.ui.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.Ui;
import com.google.common.collect.ImmutableList;

public class UiContainer implements Iterable<Ui>, NBT
{

	protected Map<String, Ui> elements = new LinkedHashMap<String, Ui>();

	protected UiContainer parent;

	public UiContainer()
	{

	}

	protected UiContainer(UiContainer parent)
	{
		this.parent = parent;
	}

	public UiContainer getParent()
	{
		return this.parent;
	}

	public Ui getElement(String key)
	{
		return this.elements.get(key);
	}

	public <T extends Ui> T getElement(String key, Class<? extends T> clazz)
	{
		return (T) this.elements.get(key);
	}

	public ImmutableList<Ui> elements()
	{
		return ImmutableList.copyOf(this.elements.values());
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
		List<Gui> views = new ArrayList<Gui>();

		for (Gui element : ObjectFilter.getTypesFrom(this.elements(), Gui.class))
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

	public boolean containsElement(Ui element)
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

	private void addCombinedDimensions(UiContainer container, List<Dim2D> areas)
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
	public UiContainer clone()
	{
		UiContainer clone = new UiContainer();

		clone.parent = this.parent;
		clone.elements = new LinkedHashMap<String, Ui>(this.elements);

		return clone;
	}

	public UiContainer merge(UiContainer first, UiContainer... rest)
	{
		return this.merge(false, first, rest);
	}

	public UiContainer merge(boolean newContentFirst, UiContainer first, UiContainer... rest)
	{
		UiContainer clone = this.clone();
		UiContainer merged = new UiContainer();

		if (!newContentFirst)
		{
			merged.elements = clone.elements;
		}

		merged.elements.putAll(first.elements);

		for (UiContainer container : rest)
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

}
