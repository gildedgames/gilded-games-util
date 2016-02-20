package com.gildedgames.util.modules.ui.listeners;

import com.gildedgames.util.modules.ui.common.Ui;
import com.gildedgames.util.modules.ui.input.InputProvider;
import com.gildedgames.util.modules.ui.input.MouseInputPool;

public interface MouseListener extends Ui
{

	void onMouseInput(MouseInputPool pool, InputProvider input);

	void onMouseScroll(int scrollDifference, InputProvider input);

}
