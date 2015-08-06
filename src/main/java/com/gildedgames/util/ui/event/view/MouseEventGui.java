package com.gildedgames.util.ui.event.view;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.event.MouseEvent;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseInput;
import com.gildedgames.util.ui.input.MouseInputPool;

public abstract class MouseEventGui extends MouseEvent
{

	protected final Gui gui;

	public MouseEventGui(Gui view)
	{
		this(view, new ArrayList<MouseInput>());
	}

	public MouseEventGui(Gui view, List<MouseInput> events)
	{
		super(events);

		this.gui = view;
	}

	public MouseEventGui(Gui view, MouseInput... events)
	{
		super(events);

		this.gui = view;
	}

	public Gui getView()
	{
		return this.gui;
	}

	@Override
	public void onMouseInput(MouseInputPool pool, InputProvider input)
	{
		if (input.isHovered(this.gui.getDim()) && this.behaviorsMet(input, pool, this.scrollDifference) && pool.containsAll(this.getEvents()))
		{
			this.onTrue(input, pool);
		}
		else
		{
			this.onFalse(input, pool);
		}
	}

	protected abstract void onTrue(InputProvider input, MouseInputPool pool);

	protected abstract void onFalse(InputProvider input, MouseInputPool pool);

}
