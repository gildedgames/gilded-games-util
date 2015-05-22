package com.gildedgames.util.ui;

import com.gildedgames.util.ui.listeners.KeyboardListener;
import com.gildedgames.util.ui.listeners.MouseListener;

public interface UIBasic extends UIView, KeyboardListener, MouseListener
{

	UIContainer getListeners();

	UIBasic getPreviousFrame();
	
}
