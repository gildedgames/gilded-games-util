package com.gildedgames.util.ui.input;

import com.gildedgames.util.ui.data.Dim2D;

public interface InputProvider
{
	
	int getScreenWidth();
	
	int getScreenHeight();
	
	int getScaleFactor();
	
	int getMouseX();
	
	int getMouseY();
	
	boolean isHovered(Dim2D dim);
	
	InputProvider clone();
	
	InputProvider copyWithMouseXOffset(int xOffset);
	
	InputProvider copyWithMouseYOffset(int yOffset);
	
}
