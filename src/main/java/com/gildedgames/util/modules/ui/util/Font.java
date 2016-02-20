package com.gildedgames.util.modules.ui.util;

import java.util.List;

public interface Font
{
	
	float getWidth(String text);

	float getHeight(String text);

	List<String> splitStringsIntoArea(String text, float width);

}
