package com.gildedgames.util.ui.util;

import java.util.List;

import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.Position2D;

public class ViewPositionerButton implements IViewPositioner
{

	@Override
	public List<UIView> positionList(List<UIView> views, Dimensions2D listDimensions)
	{
		Position2D startingPos = new Position2D(listDimensions.getX(), listDimensions.getY());
		
		for (UIView view : views)
		{
			if (view != null)
			{
				view.getDimensions().setPos(startingPos.withAdded(0, view.getDimensions().getHeight()));
			}
		}
		
		return views;
	}

}