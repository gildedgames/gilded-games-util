package com.gildedgames.util.ui;

import com.gildedgames.util.ui.util.UIDimensions;

public interface UIElement
{

	void init(UIElementHolder elementHolder, UIDimensions screenDimensions);

	boolean isEnabled();

	void setEnabled(boolean enabled);

}
