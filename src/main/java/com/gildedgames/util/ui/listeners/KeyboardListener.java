package com.gildedgames.util.ui.listeners;

import com.gildedgames.util.ui.common.UIElement;
import com.gildedgames.util.ui.input.KeyboardInputPool;

public interface KeyboardListener extends UIElement
{

	boolean onKeyboardInput(KeyboardInputPool pool);

}