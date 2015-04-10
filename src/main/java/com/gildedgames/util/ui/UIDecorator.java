package com.gildedgames.util.ui;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.DimensionsHolder;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.listeners.KeyboardListener;
import com.gildedgames.util.ui.listeners.MouseListener;


public abstract class UIDecorator<T extends UIElement> implements UIBasic
{

	private T element;
	
	public UIDecorator(T element)
	{
		this.element = element;
	}
	
	public T getDecoratedElement()
	{
		return this.element;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		UIView view = ObjectFilter.getType(this.element, UIView.class);
		
		if (view != null)
		{
			view.draw(graphics, input);
		}
	}

	@Override
	public boolean isVisible()
	{
		UIView view = ObjectFilter.getType(this.element, UIView.class);
		
		if (view != null)
		{
			return view.isVisible();
		}
		
		return false;
	}

	@Override
	public void setVisible(boolean visible)
	{
		UIView view = ObjectFilter.getType(this.element, UIView.class);
		
		if (view != null)
		{
			view.setVisible(visible);
		}
	}

	@Override
	public Dimensions2D getDimensions()
	{
		DimensionsHolder holder = ObjectFilter.getType(this.element, DimensionsHolder.class);
		
		if (holder != null)
		{
			return holder.getDimensions();
		}
		
		return null;
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
		KeyboardListener listener = ObjectFilter.getType(this.element, KeyboardListener.class);
		
		if (listener != null)
		{
			return listener.onKeyboardInput(pool);
		}
		
		return false;
	}

	@Override
	public void onMouseInput(InputProvider input, MouseInputPool pool)
	{
		MouseListener listener = ObjectFilter.getType(this.element, MouseListener.class);
		
		if (listener != null)
		{
			listener.onMouseInput(input, pool);
		}
	}

	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		MouseListener listener = ObjectFilter.getType(this.element, MouseListener.class);
		
		if (listener != null)
		{
			listener.onMouseScroll(input, scrollDifference);
		}
	}

	@Override
	public boolean isFocused()
	{
		UIView view = ObjectFilter.getType(this.element, UIView.class);
		
		if (view != null)
		{
			return view.isFocused();
		}
		
		return false;
	}

	@Override
	public void setFocused(boolean focused)
	{
		UIView view = ObjectFilter.getType(this.element, UIView.class);
		
		if (view != null)
		{
			view.setFocused(focused);
		}
	}
	
	@Override
	public boolean query(Object... input)
	{
		UIView view = ObjectFilter.getType(this.element, UIView.class);
		
		if (view != null)
		{
			return view.query(input);
		}
		
		return false;
	}

	@Override
	public UIContainer getListeners()
	{
		UIBasicAbstract frame = ObjectFilter.getType(this.element, UIBasicAbstract.class);
		
		if (frame != null)
		{
			return frame.getListeners();
		}
		
		return null;
	}
	
	@Override
	public UIBasic getPreviousFrame()
	{
		UIBasicAbstract frame = ObjectFilter.getType(this.element, UIBasicAbstract.class);
		
		if (frame != null)
		{
			return frame.getPreviousFrame();
		}
		
		return null;
	}

}
