package com.gildedgames.util.ui.listeners;

import com.gildedgames.util.ui.UIElement;
import com.gildedgames.util.ui.input.KeyEventPool;

public interface IKeyboardListener extends UIElement
{

	boolean onKeyEvent(KeyEventPool pool);

}