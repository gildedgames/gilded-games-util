package com.gildedgames.util.ui.input;

import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2DCollection;
import com.gildedgames.util.ui.data.Dim2DHolder;
import com.gildedgames.util.ui.data.Pos2D;

public interface InputProvider
{
	
	int getScreenWidth();
	
	int getScreenHeight();
	
	Pos2D getScreenCenter();
	
	int getScaleFactor();
	
	int getMouseX();
	
	int getMouseY();
	
	boolean isHovered(Dim2D dim);
	
	boolean isHovered(Dim2DHolder holder);
	
	boolean isHovered(Dim2DCollection collection);
	
	InputProvider clone();
	
	InputProvider copyWithMouseXOffset(int xOffset);
	
	InputProvider copyWithMouseYOffset(int yOffset);
	
}
