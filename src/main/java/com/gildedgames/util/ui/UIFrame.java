package com.gildedgames.util.ui;

import java.util.List;

import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.listeners.ButtonState;
import com.gildedgames.util.ui.listeners.MouseButton;
import com.gildedgames.util.ui.util.UIElementWrapper;


public abstract class UIFrame implements UIBase
{
	
	private final UIElementWrapper elementWrapper;
	
	private boolean visible = true, enabled = true, focused = false;
	
	private Dimensions2D dim;
	
	private UIBase parent;
	
	public UIFrame(UIBase parent, Dimensions2D dim)
	{
		this.elementWrapper = new UIElementWrapper();
		
		this.dim = dim;
	}
	
	public UIBase getParent()
	{
		return this.parent;
	}
	
	@Override
	public void init(UIElementContainer container, InputProvider input)
	{
		this.elementWrapper.init(container, input);
	}
	
	@Override
	public void draw(IGraphics graphics, InputProvider input)
	{
		this.elementWrapper.draw(graphics, input);
	}
	
	@Override
	public void add(UIElement element)
	{
		this.elementWrapper.add(element);
	}

	@Override
	public void remove(UIElement element)
	{
		this.elementWrapper.remove(element);
	}
	
	@Override
	public void clear()
	{
		this.elementWrapper.clear();
	}
	
	@Override
	public void clear(Class<? extends UIElement> classToRemove)
	{
		this.elementWrapper.clear(classToRemove);
	}
	
	@Override
	public List<UIElement> getElements()
	{
		return this.elementWrapper.getElements();
	}
	
	@Override
	public boolean onKeyState(char charTyped, int keyTyped, ButtonState state)
	{
		return this.elementWrapper.onKeyState(charTyped, keyTyped, state);
	}
	
	@Override
	public void onMouseState(InputProvider input, MouseButton button, ButtonState state)
	{
		this.elementWrapper.onMouseState(input, button, state);
	}

	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		this.elementWrapper.onMouseScroll(input, scrollDifference);
	}
	
	@Override
	public boolean isVisible()
	{
		return this.visible;
	}

	@Override
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	
	@Override
	public boolean isEnabled()
	{
		return this.enabled;
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	@Override
	public Dimensions2D getDimensions()
	{
		return this.dim;
	}

	@Override
	public void setDimensions(Dimensions2D dim)
	{
		this.dim = dim;
	}
	
	@Override
	public boolean isFocused()
	{
		return this.focused;
	}
	
	@Override
	public void setFocused(boolean focused)
	{
		this.focused = focused;
	}
	
	@Override
	public List<UIView> queryAll(Object... input)
	{
		return this.elementWrapper.queryAll(input);
	}
	
	@Override
	public boolean query(Object... input)
	{
		return this.elementWrapper.query(input);
	}
	
	@Override
	public boolean contains(UIElement element)
	{
		return this.elementWrapper.contains(element);
	}
	
}
