package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.factory.TestButtonFactory2;
import com.gildedgames.util.ui.util.transform.GuiPositionerGrid;

public class GuiCalculator extends GuiFrame
{
	
	private String currentInput = "";
	
	private int currentValue;

	public GuiCalculator(Pos2D pos)
	{
		super(Dim2D.build().pos(pos).flush());
	}

	@Override
	public void initContent(InputProvider input)
	{
		GuiCollection numbers = new GuiCollection(new Pos2D(), 60, new GuiPositionerGrid(), new TestButtonFactory2(9));
	}
	
	public void displayInput(String number)
	{
		this.currentInput += number;
	}

}
