package com.gildedgames.util.ui;

import java.util.Collections;
import java.util.List;

import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyEventPool;
import com.gildedgames.util.ui.input.MouseEventPool;
import com.gildedgames.util.ui.listeners.IKeyboardListener;
import com.gildedgames.util.ui.listeners.IMouseListener;
import com.gildedgames.util.ui.util.ObjectFilter;


public abstract class UIDecorator implements UIBase
{
	
	protected final static ObjectFilter FILTER = new ObjectFilter();

	private UIElement element;
	
	public UIDecorator(UIElement element)
	{
		this.element = element;
	}
	
	public <T extends UIElement> T getDecoratedElement()
	{
		return (T)this.element;
	}

	@Override
	public void draw(IGraphics graphics, InputProvider input)
	{
		UIView view = FILTER.getType(this.element, UIView.class);
		
		if (view != null)
		{
			view.draw(graphics, input);
		}
	}

	@Override
	public boolean isVisible()
	{
		UIView view = FILTER.getType(this.element, UIView.class);
		
		if (view != null)
		{
			return view.isVisible();
		}
		
		return false;
	}

	@Override
	public void setVisible(boolean visible)
	{
		UIView view = FILTER.getType(this.element, UIView.class);
		
		if (view != null)
		{
			view.setVisible(visible);
		}
	}

	@Override
	public Dimensions2D getDimensions()
	{
		UIView view = FILTER.getType(this.element, UIView.class);
		
		if (view != null)
		{
			return view.getDimensions();
		}
		
		return null;
	}

	@Override
	public void setDimensions(Dimensions2D dim)
	{
		UIView view = FILTER.getType(this.element, UIView.class);
		
		if (view != null)
		{
			view.setDimensions(dim);
		}
	}

	@Override
	public void init(UIElementContainer container, InputProvider input)
	{
		this.element.init(container, input);
	}

	@Override
	public boolean isEnabled()
	{
		return this.element.isEnabled();
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		this.element.setEnabled(enabled);
	}

	@Override
	public void add(UIElement element)
	{
		UIElementContainer container = FILTER.getType(this.element, UIElementContainer.class);
		
		if (container != null)
		{
			container.add(element);
		}
	}

	@Override
	public void remove(UIElement element)
	{
		UIElementContainer container = FILTER.getType(this.element, UIElementContainer.class);
		
		if (container != null)
		{
			container.remove(element);
		}
	}

	@Override
	public void clear()
	{
		UIElementContainer container = FILTER.getType(this.element, UIElementContainer.class);
		
		if (container != null)
		{
			container.clear();
		}
	}

	@Override
	public List<UIElement> getElements()
	{
		UIElementContainer container = FILTER.getType(this.element, UIElementContainer.class);
		
		if (container != null)
		{
			return container.getElements();
		}
		
		return Collections.EMPTY_LIST;
	}

	@Override
	public boolean onKeyEvent(KeyEventPool pool)
	{
		IKeyboardListener listener = FILTER.getType(this.element, IKeyboardListener.class);
		
		if (listener != null)
		{
			return listener.onKeyEvent(pool);
		}
		
		return false;
	}

	@Override
	public void onMouseEvent(InputProvider input, MouseEventPool pool)
	{
		IMouseListener listener = FILTER.getType(this.element, IMouseListener.class);
		
		if (listener != null)
		{
			listener.onMouseEvent(input, pool);
		}
	}

	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		IMouseListener listener = FILTER.getType(this.element, IMouseListener.class);
		
		if (listener != null)
		{
			listener.onMouseScroll(input, scrollDifference);
		}
	}
	
	@Override
	public void clear(Class<? extends UIElement> classToRemove)
	{
		UIElementContainer container = FILTER.getType(this.element, UIElementContainer.class);
		
		if (container != null)
		{
			container.clear(classToRemove);
		}
	}
	
	@Override
	public boolean isFocused()
	{
		UIView view = FILTER.getType(this.element, UIView.class);
		
		if (view != null)
		{
			return view.isFocused();
		}
		
		return false;
	}

	@Override
	public void setFocused(boolean focused)
	{
		UIView view = FILTER.getType(this.element, UIView.class);
		
		if (view != null)
		{
			view.setFocused(focused);
		}
	}
	
	@Override
	public boolean query(Object... input)
	{
		UIView view = FILTER.getType(this.element, UIView.class);
		
		if (view != null)
		{
			return view.query(input);
		}
		
		return false;
	}
	
	@Override
	public List<UIView> queryAll(Object... input)
	{
		UIElementContainer container = FILTER.getType(this.element, UIElementContainer.class);
		
		if (container != null)
		{
			return container.queryAll(input);
		}
		
		return Collections.EMPTY_LIST;
	}
	
	@Override
	public boolean contains(UIElement element)
	{
		UIElementContainer container = FILTER.getType(this.element, UIElementContainer.class);
		
		if (container != null)
		{
			return container.contains(element);
		}
		
		return false;
	}

}
