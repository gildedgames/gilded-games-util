package com.gildedgames.util.ui.listeners;

import com.gildedgames.util.ui.UIElement;
import com.gildedgames.util.ui.input.InputProvider;

public interface IMouseListener extends UIElement
{

	void onMouseState(InputProvider input, MouseButton button, ButtonState state);

	void onMouseScroll(InputProvider input, int scrollDifference);

}
