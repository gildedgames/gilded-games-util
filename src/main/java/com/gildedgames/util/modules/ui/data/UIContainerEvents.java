package com.gildedgames.util.modules.ui.data;

import com.gildedgames.util.core.gui.viewing.MinecraftGuiViewer;
import com.gildedgames.util.modules.ui.common.Gui;
import com.gildedgames.util.modules.ui.common.Ui;
import com.gildedgames.util.modules.ui.event.GuiEvent;

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
			GuiEvent event = (GuiEvent) element;
			Gui gui = (Gui) this.getAttachedUi();

			event.setGui(gui);
		}

		element.init(MinecraftGuiViewer.instance().getInputProvider());

		super.set(key, element);
	}

	public void set(String key, Ui element, Gui attachedGui)
	{
		if (element instanceof GuiEvent)
		{
			GuiEvent event = (GuiEvent) element;

			event.setGui(attachedGui);
		}

		element.init(MinecraftGuiViewer.instance().getInputProvider());

		super.set(key, element);
	}

}
