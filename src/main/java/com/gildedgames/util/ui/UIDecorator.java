package com.gildedgames.util.ui;

import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.listeners.KeyboardListener;
import com.gildedgames.util.ui.listeners.MouseListener;
import com.gildedgames.util.ui.util.ObjectFilter;


public abstract class UIDecorator<T extends UIElement> extends UIFrame
{
	
	protected final static ObjectFilter FILTER = new ObjectFilter();

	private T element;
	
	public UIDecorator(T element)
	{
		super(null, null);
		
		this.element = element;
	}
	
	public T getDecoratedElement()
	{
		return this.element;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
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
	public void onInit(UIContainer container, InputProvider input)
	{
		this.element.onInit(container, input);
	}
	
	@Override
	public void onResolutionChange(UIContainer container, InputProvider input)
	{
		this.element.onResolutionChange(container, input);
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
	public boolean onKeyboardInput(KeyboardInputPool pool)
	{
		KeyboardListener listener = FILTER.getType(this.element, KeyboardListener.class);
		
		if (listener != null)
		{
			return listener.onKeyboardInput(pool);
		}
		
		return false;
	}

	@Override
	public void onMouseInput(InputProvider input, MouseInputPool pool)
	{
		MouseListener listener = FILTER.getType(this.element, MouseListener.class);
		
		if (listener != null)
		{
			listener.onMouseInput(input, pool);
		}
	}

	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		MouseListener listener = FILTER.getType(this.element, MouseListener.class);
		
		if (listener != null)
		{
			listener.onMouseScroll(input, scrollDifference);
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
	public UIContainer getListeners()
	{
		UIFrame base = FILTER.getType(this.element, UIFrame.class);
		
		if (base != null)
		{
			return base.getListeners();
		}
		
		return null;
	}
	
	@Override
	public UIFrame getPreviousFrame()
	{
		UIFrame base = FILTER.getType(this.element, UIFrame.class);
		
		if (base != null)
		{
			return base.getPreviousFrame();
		}
		
		return null;
	}
	
}
