package com.gildedgames.util.ui.listeners;

import com.gildedgames.util.ui.common.UIElement;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseInputPool;

public interface MouseListener extends UIElement
{

	void onMouseInput(InputProvider input, MouseInputPool pool);

	void onMouseScroll(InputProvider input, int scrollDifference);

}
