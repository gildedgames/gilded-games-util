package com.gildedgames.util.ui.common;

import com.gildedgames.util.ui.data.UIElementContainer;
import com.gildedgames.util.ui.listeners.KeyboardListener;
import com.gildedgames.util.ui.listeners.MouseListener;

public interface BasicUI extends UIView, KeyboardListener, MouseListener
{

	UIElementContainer getListeners();

	BasicUI getPreviousFrame();
	
}
