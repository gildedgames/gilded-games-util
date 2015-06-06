package com.gildedgames.util.ui.util.transform;

import java.util.List;

import com.gildedgames.util.ui.common.UIView;
import com.gildedgames.util.ui.data.Dim2D;

public class UIViewPositionerButton implements UIViewPositioner
{

	@Override
	public List<UIView> positionList(List<UIView> views, Dim2D listDimensions)
	{
		int currentY = 0;
		
		for (UIView view : views)
		{	
			if (view != null)
			{
				view.getDimensions().setY(currentY);
				view.getDimensions().setCentering(false);
			}
			
			int viewYPlusHeight = view.getDimensions().getY() + view.getDimensions().getHeight();
			int yDifference = viewYPlusHeight - currentY;
			
			currentY += yDifference;
		}
		
		return views;
	}

}