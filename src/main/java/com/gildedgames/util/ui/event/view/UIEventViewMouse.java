package com.gildedgames.util.ui.event.view;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.event.UIEventMouse;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseEvent;

public abstract class UIEventViewMouse extends UIEventMouse
{
	
	protected final UIView view;

	public UIEventViewMouse(UIView view)
	{
		this(view, new ArrayList<MouseEvent>());
	}
	
	public UIEventViewMouse(UIView view, List<MouseEvent> events)
	{
		super(events);
		
		this.view = view;
	}
	
	public UIEventViewMouse(UIView view, MouseEvent... events)
	{
		super(events);
		
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
