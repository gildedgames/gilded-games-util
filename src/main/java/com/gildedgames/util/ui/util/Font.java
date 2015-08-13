package com.gildedgames.util.ui.util;

import java.util.List;

public interface Font
{
	
	double getWidth(String text);

	double getHeight(String text);

	List<String> splitStringsIntoArea(String text, double width);

}
