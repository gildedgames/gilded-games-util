package com.gildedgames.util.modules.ui.util.transform;

import com.gildedgames.util.modules.ui.common.Gui;
import com.gildedgames.util.modules.ui.data.rect.Rect;

import java.util.List;

public interface GuiPositioner
{

	List<Gui> positionList(List<Gui> guis, Rect collectionDim);

}
