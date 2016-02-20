package com.gildedgames.util.modules.ui.util.transform;

import java.util.List;

import com.gildedgames.util.modules.ui.common.Gui;
import com.gildedgames.util.modules.ui.data.rect.Rect;

public interface GuiPositioner
{
	
	List<Gui> positionList(List<Gui> guis, Rect collectionDim);
	
}
