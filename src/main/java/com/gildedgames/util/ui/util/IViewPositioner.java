package com.gildedgames.util.ui.util;

import java.util.List;

import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.data.Dimensions2D;

public interface IViewPositioner
{
	
	List<UIView> positionList(List<UIView> views, Dimensions2D listDimensions);
	
}
