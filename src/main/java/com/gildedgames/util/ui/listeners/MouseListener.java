package com.gildedgames.util.ui.listeners;

import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseInputPool;

public interface MouseListener extends Ui
{

	void onMouseInput(InputProvider input, MouseInputPool pool);

	void onMouseScroll(InputProvider input, int scrollDifference);

}
