package com.gildedgames.util.ui.util.transform;

import java.util.List;

import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.data.Dimensions2D;

public interface ViewPositioner
{
	
	List<UIView> positionList(List<UIView> views, Dimensions2D listDimensions);
	
}
