package com.gildedgames.util.ui.common;

import com.gildedgames.util.ui.input.InputProvider;

public interface UIViewer
{

	void open(UIFrame frame);
	
	void close();
	
	InputProvider getInputProvider();
	
}
