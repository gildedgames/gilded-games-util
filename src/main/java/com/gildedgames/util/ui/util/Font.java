package com.gildedgames.util.ui.util;

import java.util.List;

public interface Font
{
	int getWidth(String text);

	int getHeight(String text);

	List<String> splitStringsIntoArea(String text, int width);

}
