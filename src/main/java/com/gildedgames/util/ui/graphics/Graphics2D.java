package com.gildedgames.util.ui.graphics;

import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.data.Pos2D;



public interface Graphics2D
{

	void drawSprite(Sprite sprite, Dim2D dim, DrawingData data);
	
	void drawText(Text text, Dim2D dim, DrawingData data);

	void drawLine(Pos2D startPos, Pos2D endPos, DrawingData drawingData);

	void drawRectangle(Dim2D dim, DrawingData data);
	
	void drawGradientRectangle(Dim2D dim, DrawingData startColor, DrawingData endColor);
	
}