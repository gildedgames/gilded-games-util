package com.gildedgames.util.ui.common;

import com.gildedgames.util.ui.input.InputProvider;

public interface GuiViewer
{

	void open(GuiFrame frame);
	
	void close();
	
	InputProvider getInputProvider();
	
}
