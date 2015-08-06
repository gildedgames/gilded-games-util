package com.gildedgames.util.ui.common;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.data.UIContainer;
import com.gildedgames.util.ui.input.InputProvider;

public interface Ui extends NBT
{

	UIContainer seekContent();
	
	void init(InputProvider input);
	
	void initContent(InputProvider input);
	
	void onResolutionChange(InputProvider input);
	
	boolean isEnabled();
	
	void setEnabled(boolean enabled);
	
	/**
	 * Will not be called if this element is disabled with isEnabled()
	 * @param input
	 */
	void tick(TickInfo tickInfo, InputProvider input);
	
}
