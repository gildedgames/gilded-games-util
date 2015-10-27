package com.gildedgames.util.ui.util.events.slots;

import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseInputPool;

public interface SlotParser
{

	boolean isAllowed(SlotStack state);
	
	void onContentsChange(SlotBehavior slot, SlotStack newContents);
	
	/**
	 * Return true to cancel the normal slot behavior on mouse input.
	 * @param pool
	 * @param input
	 * @return
	 */
	boolean onMouseInput(MouseInputPool pool, InputProvider input);
	
}