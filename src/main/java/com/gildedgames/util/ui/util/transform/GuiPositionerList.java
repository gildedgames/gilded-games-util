package com.gildedgames.util.ui.util.transform;

import java.util.List;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.data.Dim2D;

public class GuiPositionerList implements GuiPositioner
{

	private final int paddingY;

	public GuiPositionerList(int paddingY)
	{
		this.paddingY = paddingY;
	}

	@Override
	public List<Gui> positionList(List<Gui> guis, Dim2D collectionDim)
	{
		int currentY = 0;

		for (Gui view : guis)
		{
			view.modDim().y(currentY).center(false).compile();

			currentY += view.getDim().getHeight() + this.paddingY;
		}

		return guis;
	}

}
