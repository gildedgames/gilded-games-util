package com.gildedgames.util.ui.util.transform;

import java.util.List;

import com.gildedgames.util.ui.common.UIView;
import com.gildedgames.util.ui.data.ImmutableDim2D;

public class UIViewPositionerButton implements UIViewPositioner
{

	@Override
	public List<UIView> positionList(List<UIView> views, ImmutableDim2D listDimensions)
	{
		int currentY = 0;
		
		for (UIView view : views)
		{	
			if (view != null)
			{
				view.getDim().setY(currentY);
				view.getDim().setCentering(false);
			}
			
			int viewYPlusHeight = view.getDim().getY() + view.getDim().getHeight();
			int yDifference = viewYPlusHeight - currentY;
			
			currentY += yDifference;
		}
		
		return views;
	}

}