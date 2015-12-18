package com.gildedgames.util.ui.input;

public interface MouseInputBehavior
{

	boolean isMet(InputProvider input, MouseInputPool pool, int scrollDifference);
	
}