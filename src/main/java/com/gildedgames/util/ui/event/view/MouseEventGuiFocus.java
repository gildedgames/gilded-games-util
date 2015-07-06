package com.gildedgames.util.ui.event.view;

import java.util.List;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseInput;
import com.gildedgames.util.ui.input.MouseInputPool;

public class MouseEventGuiFocus extends MouseEventGui
{

	public MouseEventGuiFocus(Gui view)
	{
		super(view);
	}

	public MouseEventGuiFocus(Gui view, List<MouseInput> events)
	{
		super(view, events);
	}

	public MouseEventGuiFocus(Gui view, MouseInput... events)
	{
		super(view, events);
	}

	@Override
	public void onMouseInput(InputProvider input, MouseInputPool pool)
	{
		if (input.isHovered(this.gui.getDim()) && this.behaviorsMet(input, pool, this.scrollDifference) && pool.containsAll(this.getEvents()))
		{
			this.gui.setVisible(true);
		}
		else
		{
			this.gui.setVisible(false);
		}
	}

}
