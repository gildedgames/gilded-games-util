package com.gildedgames.util.ui.util.transform;

import java.util.List;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.data.rect.Rect;

public interface GuiPositioner
{
	
	List<Gui> positionList(List<Gui> guis, Rect collectionDim);
	
}
