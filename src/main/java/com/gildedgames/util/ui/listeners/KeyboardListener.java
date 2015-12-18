package com.gildedgames.util.ui.listeners;

import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyboardInputPool;

public interface KeyboardListener extends Ui
{

	boolean onKeyboardInput(KeyboardInputPool pool, InputProvider input);

}