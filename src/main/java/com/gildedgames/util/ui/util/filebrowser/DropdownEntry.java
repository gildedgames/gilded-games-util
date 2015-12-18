package com.gildedgames.util.ui.util.filebrowser;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Pos2D;

public interface DropdownEntry
{

	GuiFrame createVisuals();
	
	void onOpen();
	
	void onHover(Pos2D cursorPos);
	
	/**
	 * Return null if there is no sub menu on this entry.
	 * @return
	 */
	DropdownMenu subMenu();
	
}
