package com.gildedgames.util.ui.util.transform;

import java.util.List;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.data.Dim2D;

public class GuiPositionerButton implements GuiPositioner
{

	@Override
	public List<Gui> positionList(List<Gui> guis, Dim2D listDimensions)
	{
		int currentY = 0;

		for (Gui view : guis)
		{
			if (view != null)
			{
				view.modDim().y(currentY).center(false).compile();
			}

			int viewYPlusHeight = view.getDim().getY() + view.getDim().getHeight();
			int yDifference = viewYPlusHeight - currentY;

			currentY += yDifference;
		}

		return guis;
	}

}
