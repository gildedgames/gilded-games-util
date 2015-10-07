package com.gildedgames.util.ui.input;

import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2DCollection;
import com.gildedgames.util.ui.data.Dim2DHolder;

public interface InputProvider
{
	
	void refreshResolution();
	
	float getScreenWidth();
	
	float getScreenHeight();

	float getScaleFactor();
	
	float getMouseX();
	
	float getMouseY();
	
	boolean isHovered(Dim2D dim);
	
	boolean isHovered(Dim2DHolder holder);
	
	boolean isHovered(Dim2DCollection collection);
	
	InputProvider clone();
	
	InputProvider copyWithMouseXOffset(float xOffset);
	
	InputProvider copyWithMouseYOffset(float yOffset);
	
}
