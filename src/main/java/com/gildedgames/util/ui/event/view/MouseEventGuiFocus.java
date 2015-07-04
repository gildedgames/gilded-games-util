package com.gildedgames.util.ui.event.view;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.event.MouseEvent;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseInput;
import com.gildedgames.util.ui.input.MouseInputPool;

public class MouseEventGuiFocus extends MouseEvent
{

	protected final Gui gui;

	public MouseEventGuiFocus(Gui view)
	{
		this(view, new ArrayList<MouseInput>());
	}

	public MouseEventGuiFocus(Gui view, List<MouseInput> input)
	{
		super(input);

		this.gui = view;
	}

	public MouseEventGuiFocus(Gui view, MouseInput... input)
	{
		super(input);

		this.gui = view;
	}

	@Override
	public void onMouseInput(InputProvider input, MouseInputPool pool)
	{
		if (input.isHovered(this.gui.getDim()) && pool.containsAll(this.getEvents()))
		{
			this.gui.setVisible(true);
		}
		else
		{
			this.gui.setVisible(false);
		}
	}

	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{

	}

	public Gui getView()
	{
		return this.gui;
	}

}
