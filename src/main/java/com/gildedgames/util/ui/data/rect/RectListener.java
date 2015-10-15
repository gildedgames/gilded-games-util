package com.gildedgames.util.ui.data.rect;

import java.util.List;

import com.gildedgames.util.ui.data.rect.RectModifier.ModifierType;

public interface RectListener
{

	void notifyDimChange(List<ModifierType> changedType);

}
