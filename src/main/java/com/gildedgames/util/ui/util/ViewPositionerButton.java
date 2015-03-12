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
		
		UIView prevView = null;
		
		for (UIView view : views)
		{	
			if (view != null)
			{
				if (prevView != null)
				{
					view.getDimensions().setPos(prevView.getDimensions().getPosition().withAdded(0, prevView.getDimensions().getHeight()));
				}
				else
				{
					view.getDimensions().setPos(startingPos);
				}
				
				view.getDimensions().setCentering(false);
			}
			
			prevView = view;
		}
		
		return views;
	}

}