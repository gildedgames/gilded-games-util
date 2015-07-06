package com.gildedgames.util.ui.event.view;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.event.MouseEvent;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseInput;

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

}
