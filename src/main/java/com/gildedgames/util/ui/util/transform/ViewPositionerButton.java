package com.gildedgames.util.ui.util.transform;

import java.util.List;

import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.data.Dimensions2D;

public class ViewPositionerButton implements ViewPositioner
{

	@Override
	public List<UIView> positionList(List<UIView> views, Dimensions2D listDimensions)
	{
		UIView prevView = null;
		
		for (UIView view : views)
		{	
			if (view != null)
			{
				if (prevView != null)
				{
					view.getDimensions().setPos(prevView.getDimensions().getPos().withAdded(0, prevView.getDimensions().getHeight()));
				}
				
				view.getDimensions().setCentering(false);
			}
			
			prevView = view;
		}
		
		return views;
	}

}