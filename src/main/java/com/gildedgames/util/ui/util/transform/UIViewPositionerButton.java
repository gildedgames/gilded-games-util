package com.gildedgames.util.ui.util.transform;

import java.util.List;

import com.gildedgames.util.ui.common.UIView;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2D.Dim2DBuilder;

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
				view.setDim(Dim2D.build(view).y(currentY).center(false).commit());
			}
			
			int viewYPlusHeight = view.getDim().getY() + view.getDim().getHeight();
			int yDifference = viewYPlusHeight - currentY;
			
			currentY += yDifference;
		}
		
		return views;
	}

}