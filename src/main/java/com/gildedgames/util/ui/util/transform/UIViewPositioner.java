package com.gildedgames.util.ui.util.transform;

import java.util.List;

import com.gildedgames.util.ui.common.UIView;
import com.gildedgames.util.ui.data.Dim2D;

public interface UIViewPositioner
{
	
	List<UIView> positionList(List<UIView> views, Dim2D listDimensions);
	
}
