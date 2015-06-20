package com.gildedgames.util.ui.util.transform;

import java.util.List;

import com.gildedgames.util.ui.common.UIView;
import com.gildedgames.util.ui.data.ImmutableDim2D;

public interface UIViewPositioner
{
	
	List<UIView> positionList(List<UIView> views, ImmutableDim2D listDimensions);
	
}
