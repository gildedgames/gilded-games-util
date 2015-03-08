package com.gildedgames.util.ui.event;

import java.util.List;

import com.gildedgames.util.ui.listeners.ButtonState;
import com.gildedgames.util.ui.listeners.IMouseListener;
import com.gildedgames.util.ui.listeners.MouseButton;

public abstract class UIEventMouse extends UIEvent implements IMouseListener
{
	
	private final List<MouseButton> buttons;
	
	private final List<ButtonState> states;
	
	public UIEventMouse(List<MouseButton> buttons)
	{
		this(buttons, null);
	}
	
	public UIEventMouse(List<MouseButton> buttons, List<ButtonState> states)
	{
		this.buttons = buttons;
		this.states = states;
	}
	
	public List<MouseButton> getButtons()
	{
		return this.buttons;
	}

	public List<ButtonState> getStates()
	{
		return this.states;
	}
	
}