package com.gildedgames.util.ui.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.core.nbt.NBTFactory;
import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.ui.common.BasicUI;
import com.gildedgames.util.ui.common.UIElement;
import com.gildedgames.util.ui.common.UIView;

public class UIElementContainer implements Iterable<UIElement>, NBT
{

	protected Map<String, UIElement> elements = new LinkedHashMap<String, UIElement>();

	protected Map<String, UIElementContainer> internalContainers = new LinkedHashMap<String, UIElementContainer>();
	
	private UIElementContainer parent;

	public UIElementContainer()
	{
		
	}
	
	private UIElementContainer(UIElementContainer parent)
	{
		this.parent = parent;
	}
	
	public UIElementContainer getParent()
	{
		return this.parent;
	}
	
	public void setElement(String key, UIElement element)
	{
		this.setElement(key, element, new UIElementContainer(this));
	}

	private void setElement(String key, UIElement element, UIElementContainer internalContainer)
	{
		this.elements.put(key, element);
		this.internalContainers.put(key, internalContainer);
	}
	
	public void remove(String key)
	{
		this.internalContainers.get(key).clear();
		
		this.elements.remove(key);
		this.internalContainers.remove(key);
	}
	
	public List<UIElement> values()
	{
		return new ArrayList<UIElement>(this.elements.values());
	}
	
	public Map<String, UIElement> map()
	{
		return new LinkedHashMap<String, UIElement>(this.elements);
	}
	
	public List<UIElementContainer> internalContainers()
	{
		return new ArrayList<UIElementContainer>(this.internalContainers.values());
	}
	
	/**
	 * Get the internal UIContainer for the selected UIElement
	 * @param element
	 * @return
	 */
	public UIElementContainer getContainerFor(String key)
	{
		return this.internalContainers.get(key);
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

	public List<UIView> queryAll(Object... input)
	{
		List<UIView> views = new ArrayList<UIView>();

		for (UIView element : ObjectFilter.getTypesFrom(this.values(), UIView.class))
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

	public void setAllElements(Map<String, UIElement> elements)
	{
		for (Map.Entry<String, UIElement> entry : elements.entrySet())
		{
			this.setElement(entry.getKey(), entry.getValue());
		}
	}
	
	public void set(UIElementContainer container)
	{
		UIElementContainer clone = container.clone();
		container.clear();
		
		for (Map.Entry<String, UIElement> entry : clone.map().entrySet())
		{
			container.setElement(entry.getKey(), entry.getValue(), clone.getContainerFor(entry.getKey()));
		}
	}

	public int size()
	{
		return this.elements.size();
	}

	public void clear()
	{
		for (UIElementContainer container : this.internalContainers.values())
		{
			container.clear();
		}
		
		this.elements.clear();
		this.internalContainers.clear();
	}
	
	public Dim2D getCombinedDimensions()
	{
		List<Dim2D> areas = new ArrayList<Dim2D>();

		this.addCombinedDimensions(this, areas);

		return Dim2D.combine(areas);
	}
	
	private void addCombinedDimensions(UIElementContainer container, List<Dim2D> areas)
	{
		for (Map.Entry<String, UIElement> entry : container.map().entrySet())
		{
			String key = entry.getKey();
			UIElement element = entry.getValue();
			
			if (element instanceof UIView)
			{
				UIView view = (UIView) element;

				areas.add(view.getDim());
				
				UIElementContainer internal = container.getContainerFor(key);
				
				this.addCombinedDimensions(internal, areas);
			}
		}
	}
	
	public UIElementContainer clone()
	{
		UIElementContainer clone = new UIElementContainer();
		
		clone.parent = this.parent;
		clone.elements = this.elements;
		clone.internalContainers = this.internalContainers;
		
		return clone;
	}

	@Override
	public Iterator<UIElement> iterator()
	{
		return this.values().iterator();
	}

	@Override
	public void write(NBTTagCompound output)
	{
		output.setInteger("entryCount", this.elements.size());
		
		int objectCount = 0;
		
		for (Map.Entry<String, UIElement> entry : this.elements.entrySet())
		{
			String key = entry.getKey();
			UIElement element = entry.getValue();
			
			String objectName = "object" + objectCount;
		
			if (element != null)
			{
				UIElementContainer internal = this.getContainerFor(key);

				output.setString(objectName + "key", key);
				
				IOCore.io().set(objectName, output, new NBTFactory(), element);
				IOCore.io().set(objectName + "Container", output, new NBTFactory(), internal);
				
				BasicUI basic = ObjectFilter.getType(element, BasicUI.class);
				
				if (basic != null)
				{
					output.setBoolean(objectName + "HasListeners", true);
					IOCore.io().set(objectName + "Listeners", output, new NBTFactory(), basic.getListeners());
				}
				else
				{
					output.setBoolean(objectName + "HasListeners", false);
				}
			}
			
			objectCount++;
		}
	}

	@Override
	public void read(NBTTagCompound input)
	{
		int entryCount = input.getInteger("entryCount");
		
		for (int i = 0; i < entryCount; i++)
		{
			String objectName = "object" + i;
			
			String objectKey = input.getString(objectName + "Key");
			
			UIElement element = IOCore.io().get(objectName, input, new NBTFactory());
			UIElementContainer internalContainer = IOCore.io().get(objectName + "Container", input, new NBTFactory());
			
			this.setElement(objectKey, element, internalContainer);
			
			boolean hasListeners = input.getBoolean(objectName + "HasListeners");
			
			if (hasListeners && element instanceof BasicUI)
			{
				BasicUI basic = (BasicUI)element;
				
				UIElementContainer listeners = IOCore.io().get(objectName + "Listeners", input, new NBTFactory());
				
				basic.getListeners().set(listeners);
			}
		}
	}

}
