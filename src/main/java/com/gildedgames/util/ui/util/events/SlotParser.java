package com.gildedgames.util.ui.util.events;

public interface SlotParser
{

	boolean isAllowed(DraggedState state);
	
	void onContentsChange(DraggedState newContents);
	
}