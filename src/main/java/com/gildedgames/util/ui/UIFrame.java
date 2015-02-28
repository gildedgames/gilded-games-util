package com.gildedgames.util.ui;

import java.util.List;

import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.listeners.ButtonState;
import com.gildedgames.util.ui.listeners.MouseButton;
import com.gildedgames.util.ui.util.UIElementWrapper;


public abstract class UIFrame implements UIOverhead
{
	
	private final UIElementWrapper elementWrapper;
	
	private boolean visible = true;
	
	private boolean enabled = true;
	
	private Dimensions2D focusArea;
	
	public UIFrame(Dimensions2D focusArea)
	{
		this.elementWrapper = new UIElementWrapper();
		
		this.focusArea = focusArea;
	}
	
	@Override
	public void init(UIElementHolder holder, Dimensions2D screen)
	{
		this.elementWrapper.init(holder, screen);
	}
	
	@Override
	public void draw(IGraphics graphics, InputProvider input)
	{
		this.elementWrapper.draw(graphics, input);
	}
	
	@Override
	public final void add(UIElement element)
	{
		this.elementWrapper.add(element);
	}

	@Override
	public final void remove(UIElement element)
	{
		this.elementWrapper.remove(element);
	}
	
	@Override
	public final void clear()
	{
		this.elementWrapper.clear();
	}
	
	@Override
	public final List<UIElement> getElements()
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
	public Dimensions2D getFocusArea()
	{
		return this.focusArea;
	}

	@Override
	public void setFocusArea(Dimensions2D focusArea)
	{
		this.focusArea = focusArea;
	}
	
	@Override
	public void onFocused(InputProvider input)
	{
		this.elementWrapper.onFocused(input);
	}
	
	@Override
	public void setScreen(Dimensions2D screen)
	{
		this.elementWrapper.setScreen(screen);
	}
	
}
