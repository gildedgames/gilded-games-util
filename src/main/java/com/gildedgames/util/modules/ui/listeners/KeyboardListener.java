package com.gildedgames.util.modules.ui.listeners;

import com.gildedgames.util.modules.ui.common.Ui;
import com.gildedgames.util.modules.ui.input.InputProvider;
import com.gildedgames.util.modules.ui.input.KeyboardInputPool;

public interface KeyboardListener extends Ui
{

	boolean onKeyboardInput(KeyboardInputPool pool, InputProvider input);

}
