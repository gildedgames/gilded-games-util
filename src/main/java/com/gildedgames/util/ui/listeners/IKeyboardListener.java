package com.gildedgames.util.ui.listeners;

import java.util.List;

import com.gildedgames.util.ui.UIElement;

public interface IKeyboardListener extends UIElement
{

	boolean onKeyState(char charTyped, int keyTyped, List<ButtonState> states);

}