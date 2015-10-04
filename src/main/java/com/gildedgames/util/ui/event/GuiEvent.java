package com.gildedgames.util.ui.event;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.GuiFrame;

public abstract class GuiEvent extends GuiFrame
{
	
	private Gui gui;

	public GuiEvent()
	{
		
	}
	
	public Gui getGui()
	{
		return this.gui;
	}
	
	public void setGui(Gui gui)
	{
		this.gui = gui;
		
		this.initEvent();
	}
	
	public abstract void initEvent();
	
}
