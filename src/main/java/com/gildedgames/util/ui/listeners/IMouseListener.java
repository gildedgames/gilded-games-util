package com.gildedgames.util.ui.listeners;

import com.gildedgames.util.ui.UIElement;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseEventPool;

public interface IMouseListener extends UIElement
{

	void onMouseEvent(InputProvider input, MouseEventPool pool);

	void onMouseScroll(InputProvider input, int scrollDifference);

}
