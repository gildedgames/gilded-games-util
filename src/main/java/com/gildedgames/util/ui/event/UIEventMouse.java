package com.gildedgames.util.ui.event;

import com.gildedgames.util.ui.listeners.ButtonState;
import com.gildedgames.util.ui.listeners.IMouseListener;
import com.gildedgames.util.ui.listeners.MouseButton;

public abstract class UIEventMouse extends UIEvent implements IMouseListener
{
	
	private final MouseButton button;
	
	private final ButtonState state;
	
	public UIEventMouse(MouseButton button)
	{
		this(button, null);
	}
	
	public UIEventMouse(MouseButton button, ButtonState state)
	{
		this.button = button;
		this.state = state;
	}
	
	public MouseButton getButton()
	{
		return this.button;
	}

	public ButtonState getState()
	{
		return this.state;
	}
	
}