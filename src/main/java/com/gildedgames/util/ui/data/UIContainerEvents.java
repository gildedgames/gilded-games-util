package com.gildedgames.util.ui.data;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.event.GuiEvent;

public class UIContainerEvents extends UIContainerMutable
{

	public UIContainerEvents(Ui attachedUi)
	{
		super(attachedUi);
	}
	
	@Override
	public void set(String key, Ui element)
	{
		if (element instanceof GuiEvent && this.getAttachedUi() instanceof Gui)
		{
			GuiEvent event = (GuiEvent)element;
			Gui gui = (Gui)this.getAttachedUi();
			
			event.setGui(gui);
		}
		
		super.set(key, element);
	}
	
}
