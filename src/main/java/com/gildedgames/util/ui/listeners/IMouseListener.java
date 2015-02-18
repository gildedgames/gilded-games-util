package com.gildedgames.util.ui.listeners;

import com.gildedgames.util.ui.UIElement;

public interface IMouseListener extends UIElement
{

	void onMouseState(int mouseX, int mouseY, MouseButton button, ButtonState state);

	void onMouseScroll(int mouseX, int mouseY, int scrollDifference);

}
