package com.gildedgames.util.ui.event.view;

import java.util.List;

import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.event.UIEventMouse;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.listeners.ButtonState;
import com.gildedgames.util.ui.listeners.MouseButton;

public abstract class UIEventViewMouse extends UIEventMouse
{
	
	protected final UIView view;

	public UIEventViewMouse(UIView view)
	{
		this(view, null, null);
	}
	
	public UIEventViewMouse(UIView view, List<MouseButton> buttons)
	{
		this(view, buttons, null);
	}
	
	public UIEventViewMouse(UIView view, List<MouseButton> buttons, List<ButtonState> states)
	{
		super(buttons, states);
		
		this.view = view;
	}

	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		
	}
	
	public UIView getView()
	{
		return this.view;
	}

}
