package com.gildedgames.util.modules.ui.event;

import com.gildedgames.util.modules.ui.common.Gui;
import com.gildedgames.util.modules.ui.common.GuiFrame;

public abstract class GuiEvent<E extends Gui> extends GuiFrame
{

	private E gui;

	public GuiEvent()
	{

	}

	public E getGui()
	{
		return this.gui;
	}

	public void setGui(E gui)
	{
		this.gui = gui;

		this.initEvent();
	}

	public abstract void initEvent();

}
