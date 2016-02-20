package com.gildedgames.util.modules.ui.data.rect;

import java.util.List;

import com.gildedgames.util.modules.ui.data.rect.RectModifier.ModifierType;

public interface RectListener
{

	void notifyDimChange(List<ModifierType> changedType);

}
