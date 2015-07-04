package com.gildedgames.util.ui.util.transform;

import java.util.List;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.data.Dim2D;

public interface GuiPositioner
{
	
	List<Gui> positionList(List<Gui> guis, Dim2D listDimensions);
	
}
