package com.gildedgames.util.ui.listeners;

import java.util.List;

import com.gildedgames.util.ui.UIElement;
import com.gildedgames.util.ui.input.InputProvider;

public interface IMouseListener extends UIElement
{

	void onMouseState(InputProvider input, List<MouseButton> buttons, List<ButtonState> states);

	void onMouseScroll(InputProvider input, int scrollDifference);

}
