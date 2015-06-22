package com.gildedgames.util.ui.common;

import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.core.nbt.NBTFactory;
import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2D.Dim2DBuilder;
import com.gildedgames.util.ui.data.Dim2D.Dim2DModifier;
import com.gildedgames.util.ui.data.Dim2DHolder;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.data.UIElementContainer;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.listeners.KeyboardListener;
import com.gildedgames.util.ui.listeners.MouseListener;

public class UIFrame implements UIView, KeyboardListener, MouseListener
{

	private UIElementContainer parentNode = new UIElementContainer();
	
	private UIElement framedElement;
	
	public UIFrame(UIElement framedElement)
	{
		this.framedElement = framedElement;
		
		this.parentNode.setElement("framedElement", this.framedElement);
	}
	
	public UIElement getFramedElement()
	{
		return this.framedElement;
	}

	public void onInit(InputProvider input)
	{
		this.parentNode.clear();
		this.parentNode.setElement("framedElement", this.framedElement);
		
		this.onInit(this.parentNode, input);
	}
	
	public void onResolutionChange(InputProvider input)
	{
		this.onInit(input);
	}
	
	@Override
	public void onInit(UIElementContainer container, InputProvider input)
	{
		if (container == null)
		{
			return;
		}
		
		for (Map.Entry<String, UIElement> entry : ObjectFilter.getTypesFromValues(container.map(), String.class, UIElement.class).entrySet())
		{
			String key = entry.getKey();
			UIElement element = entry.getValue();
			
			UIElementContainer internal = container.getContainerFor(key);
			
			Dim2DHolder parentModifier = ObjectFilter.getType(element, Dim2DHolder.class);
			
			/*if (parentModifier != null)
			{
				parentModifier.getDim().clearModifiers();
				
				for (UIElement internalElement : internal)
				{
					Dim2DHolder child = ObjectFilter.getType(internalElement, Dim2DHolder.class);
					
					if (child != null)
					{
						child.getDim().clearModifiers();
					}
				}
			}*/
			
			element.onInit(internal, input);

			if (parentModifier != null)
			{
				for (UIElement internalElement : internal)
				{
					Dim2DHolder child = ObjectFilter.getType(internalElement, Dim2DHolder.class);
					
					if (child != null)
					{
						child.modDim().addModifier(parentModifier).commit();
					}
				}
			}
			
			BasicUI basic = ObjectFilter.getType(element, BasicUI.class);
			
			if (basic != null)
			{
				this.onInit(basic.getListeners(), input);
			}
			
			this.onInit(internal, input);
		}
	}

	@Override
	public void onResolutionChange(UIElementContainer container, InputProvider input)
	{
		if (container == null)
		{
			return;
		}
		
		for (Map.Entry<String, UIElement> entry : ObjectFilter.getTypesFromValues(container.map(), String.class, UIElement.class).entrySet())
		{
			String key = entry.getKey();
			UIElement element = entry.getValue();
			
			UIElementContainer internal = container.getContainerFor(key);
			
			Dim2DHolder parentModifier = ObjectFilter.getType(element, Dim2DHolder.class);

			element.onResolutionChange(internal, input);

			if (parentModifier != null)
			{
				for (UIElement internalElement : internal)
				{
					Dim2DHolder child = ObjectFilter.getType(internalElement, Dim2DHolder.class);
					
					if (child != null)
					{
						child.modDim().addModifier(parentModifier).commit();
					}
				}
			}
			
			BasicUI basic = ObjectFilter.getType(element, BasicUI.class);
			
			if (basic != null)
			{
				this.onResolutionChange(basic.getListeners(), input);
			}

			this.onResolutionChange(internal, input);
		}
	}

	@Override
	public void onMouseInput(InputProvider input, MouseInputPool pool)
	{
		this.onMouseInput(this.parentNode, input, pool);
	}
	
	private void onMouseInput(UIElementContainer container, InputProvider input, MouseInputPool pool)
	{
		if (container == null)
		{
			return;
		}
		
		for (Map.Entry<String, MouseListener> entry : ObjectFilter.getTypesFromValues(container.map(), String.class, MouseListener.class).entrySet())
		{
			String key = entry.getKey();
			MouseListener element = entry.getValue();
			
			UIElementContainer internal = container.getContainerFor(key);
			
			if (element.isEnabled())
			{
				element.onMouseInput(input, pool);
				
				BasicUI basic = ObjectFilter.getType(element, BasicUI.class);
				
				if (basic != null)
				{
					this.onMouseInput(basic.getListeners(), input, pool);
				}
				
				this.onMouseInput(internal, input, pool);
			}
		}
	}

	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		this.onMouseScroll(this.parentNode, input, scrollDifference);
	}
	
	private void onMouseScroll(UIElementContainer container, InputProvider input, int scrollDifference)
	{
		if (container == null)
		{
			return;
		}
		
		for (Map.Entry<String, MouseListener> entry : ObjectFilter.getTypesFromValues(container.map(), String.class, MouseListener.class).entrySet())
		{
			String key = entry.getKey();
			MouseListener element = entry.getValue();
			
			UIElementContainer internal = container.getContainerFor(key);
			
			if (element.isEnabled())
			{
				element.onMouseScroll(input, scrollDifference);
				
				BasicUI basic = ObjectFilter.getType(element, BasicUI.class);
				
				if (basic != null)
				{
					this.onMouseScroll(basic.getListeners(), input, scrollDifference);
				}
				
				this.onMouseScroll(internal, input, scrollDifference);
			}
		}
	}

	@Override
	public boolean onKeyboardInput(KeyboardInputPool pool)
	{
		return this.onKeyboardInput(this.parentNode, pool);
	}
	
	private boolean onKeyboardInput(UIElementContainer container, KeyboardInputPool pool)
	{
		if (container == null)
		{
			return false;
		}
		
		boolean success = false;
		
		for (Map.Entry<String, KeyboardListener> entry : ObjectFilter.getTypesFromValues(container.map(), String.class, KeyboardListener.class).entrySet())
		{
			String key = entry.getKey();
			KeyboardListener element = entry.getValue();
			
			UIElementContainer internal = container.getContainerFor(key);
			
			if (element.isEnabled())
			{
				BasicUI basic = ObjectFilter.getType(element, BasicUI.class);

				success = element.onKeyboardInput(pool)
						|| this.onKeyboardInput(internal, pool)
						|| (basic != null && this.onKeyboardInput(basic.getListeners(), pool))
						|| success;
			}
		}
		
		return success;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		this.draw(this.parentNode, graphics, input);
	}
	
	private void draw(UIElementContainer container, Graphics2D graphics, InputProvider input)
	{
		if (container == null)
		{
			return;
		}
		
		for (Map.Entry<String, UIView> entry : ObjectFilter.getTypesFromValues(container.map(), String.class, UIView.class).entrySet())
		{
			String key = entry.getKey();
			UIView element = entry.getValue();
			
			UIElementContainer internal = container.getContainerFor(key);
			
			if (element.isVisible())
			{
				element.draw(graphics, input);
				
				BasicUI basic = ObjectFilter.getType(element, BasicUI.class);
				
				if (basic != null)
				{
					this.draw(basic.getListeners(), graphics, input);
				}
				
				this.draw(internal, graphics, input);
			}
		}
	}
	
	@Override
	public void tick(InputProvider input, TickInfo tickInfo)
	{
		this.tick(this.parentNode, input, tickInfo);
	}
	
	private void tick(UIElementContainer container, InputProvider input, TickInfo tickInfo)
	{
		if (container == null)
		{
			return;
		}
		
		for (Map.Entry<String, UIElement> entry : ObjectFilter.getTypesFromValues(container.map(), String.class, UIElement.class).entrySet())
		{
			String key = entry.getKey();
			UIElement element = entry.getValue();
			
			UIElementContainer internal = container.getContainerFor(key);
			
			if (element.isEnabled())
			{
				element.tick(input, tickInfo);

				BasicUI basic = ObjectFilter.getType(element, BasicUI.class);
				
				if (basic != null)
				{
					this.tick(basic.getListeners(), input, tickInfo);
				}
				
				this.tick(internal, input, tickInfo);
			}
		}
	}
	
	@Override
	public boolean isEnabled()
	{
		return true;
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		
	}

	@Override
	public boolean isVisible()
	{
		return true;
	}

	@Override
	public void setVisible(boolean visible)
	{
		
	}

	@Override
	public Dim2D getDim()
	{
		return new Dim2D();
	}

	public boolean isFocused()
	{
		return true;
	}

	@Override
	public void setFocused(boolean focused)
	{
		
	}

	@Override
	public boolean query(Object... input)
	{
		return false;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		IOCore.io().set("parentNode", output, new NBTFactory(), this.parentNode);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		this.parentNode = IOCore.io().get("parentNode", input, new NBTFactory());
	}

	@Override
	public void setDim(Dim2D dim)
	{

	}

	@Override
	public Dim2DModifier modDim()
	{
		return new Dim2DModifier(this);
	}

}