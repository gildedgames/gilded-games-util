package com.gildedgames.util.ui.event.view;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.ui.common.UIView;
import com.gildedgames.util.ui.event.MouseEvent;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseInput;
import com.gildedgames.util.ui.input.MouseInputPool;

public class MouseEventViewFocus extends MouseEvent
{
	
	protected final UIView view;

	public MouseEventViewFocus(UIView view)
	{
		this(view, new ArrayList<MouseInput>());
	}
	
	public MouseEventViewFocus(UIView view, List<MouseInput> input)
	{
		super(input);
		
		this.view = view;
	}
	
	public MouseEventViewFocus(UIView view, MouseInput... input)
	{
		super(input);
		
		this.view = view;
	}

	@Override
	public void onMouseInput(InputProvider input, MouseInputPool pool)
	{
		if (input.isHovered(this.view.getDimensions()) && pool.containsAll(this.getEvents()))
		{
			this.view.setVisible(true);
		}
		else
		{
			this.view.setVisible(false);
		}
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
