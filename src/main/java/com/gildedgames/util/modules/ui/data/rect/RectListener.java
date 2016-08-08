package com.gildedgames.util.modules.ui.data.rect;

import com.gildedgames.util.modules.ui.data.rect.RectModifier.ModifierType;

import java.util.List;

public interface RectListener
{

	void notifyDimChange(List<ModifierType> changedType);

}
