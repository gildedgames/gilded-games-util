package com.gildedgames.util.ui.common;

import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public interface GuiViewer
{

	void open(GuiFrame frame);
	
	void close(GuiFrame frame);
	
	InputProvider getInputProvider();
	
	TickInfo getTickInfo();
	
	Graphics2D getGraphics();
	
}
