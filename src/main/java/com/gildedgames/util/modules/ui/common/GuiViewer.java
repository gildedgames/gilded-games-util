package com.gildedgames.util.modules.ui.common;

import com.gildedgames.util.modules.ui.data.TickInfo;
import com.gildedgames.util.modules.ui.graphics.Graphics2D;
import com.gildedgames.util.modules.ui.input.InputProvider;

public interface GuiViewer
{

	void open(GuiFrame frame);
	
	void close(GuiFrame frame);
	
	InputProvider getInputProvider();
	
	TickInfo getTickInfo();
	
	Graphics2D getGraphics();
	
}
