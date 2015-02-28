package com.gildedgames.util.ui;

import java.util.Collections;
import java.util.List;

import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.listeners.ButtonState;
import com.gildedgames.util.ui.listeners.IKeyboardListener;
import com.gildedgames.util.ui.listeners.IMouseListener;
import com.gildedgames.util.ui.listeners.MouseButton;
import com.gildedgames.util.ui.util.ListFilter;


public abstract class UIDecorator implements UIOverhead
{
	
	protected final static ListFilter FILTER = new ListFilter();

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
	public Dimensions2D getFocusArea()
	{
		UIView view = FILTER.getType(this.element, UIView.class);
		
		if (view != null)
		{
			return view.getFocusArea();
		}
		
		return null;
	}

	@Override
	public void setFocusArea(Dimensions2D focusArea)
	{
		UIView view = FILTER.getType(this.element, UIView.class);
		
		if (view != null)
		{
			view.setFocusArea(focusArea);
		}
	}

	@Override
	public void onFocused(InputProvider input)
	{
		UIView view = FILTER.getType(this.element, UIView.class);
		
		if (view != null)
		{
			view.onFocused(input);
		}
	}

	@Override
	public void init(UIElementHolder elementHolder, Dimensions2D screen)
	{
		this.element.init(elementHolder, screen);
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
	public void setScreen(Dimensions2D screen)
	{
		UIElementHolder elementHolder = FILTER.getType(this.element, UIElementHolder.class);
		
		if (elementHolder != null)
		{
			elementHolder.setScreen(screen);
		}
	}

	@Override
	public void add(UIElement element)
	{
		UIElementHolder elementHolder = FILTER.getType(this.element, UIElementHolder.class);
		
		if (elementHolder != null)
		{
			elementHolder.add(element);
		}
	}

	@Override
	public void remove(UIElement element)
	{
		UIElementHolder elementHolder = FILTER.getType(this.element, UIElementHolder.class);
		
		if (elementHolder != null)
		{
			elementHolder.remove(element);
		}
	}

	@Override
	public void clear()
	{
		UIElementHolder elementHolder = FILTER.getType(this.element, UIElementHolder.class);
		
		if (elementHolder != null)
		{
			elementHolder.clear();
		}
	}

	@Override
	public List<UIElement> getElements()
	{
		UIElementHolder elementHolder = FILTER.getType(this.element, UIElementHolder.class);
		
		if (elementHolder != null)
		{
			return elementHolder.getElements();
		}
		
		return Collections.EMPTY_LIST;
	}

	@Override
	public boolean onKeyState(char charTyped, int keyTyped, ButtonState state)
	{
		IKeyboardListener listener = FILTER.getType(this.element, IKeyboardListener.class);
		
		if (listener != null)
		{
			return listener.onKeyState(charTyped, keyTyped, state);
		}
		
		return false;
	}

	@Override
	public void onMouseState(InputProvider input, MouseButton button, ButtonState state)
	{
		IMouseListener listener = FILTER.getType(this.element, IMouseListener.class);
		
		if (listener != null)
		{
			listener.onMouseState(input, button, state);
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

}
