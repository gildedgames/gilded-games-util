package com.gildedgames.util.ui.util.factory;

import java.util.LinkedHashMap;

import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.event.view.MouseEventGui;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInput;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.util.GuiCalculator;
import com.google.common.collect.ImmutableMap;

public class CalculatorButtonFactory implements ContentFactory
{
	
	private GuiCalculator calculator;
	
	private int buttonCount;
	
	public CalculatorButtonFactory(GuiCalculator calculator)
	{
		this.calculator = calculator;
		this.buttonCount = 9;
	}

	@Override
	public LinkedHashMap<String, Ui> provideContent(ImmutableMap<String, Ui> currentContent, Dim2D contentArea)
	{
		LinkedHashMap<String, Ui> buttons = new LinkedHashMap<String, Ui>();

		for (int count = 0; count < this.buttonCount; count++)
		{
			GuiFrame button = GuiFactory.createButton(new Pos2D(), 20, String.valueOf(count + 1), false);
			
			button.listeners().setElement("type", new MouseEventGui(button, new MouseInput(MouseButton.LEFT, ButtonState.DOWN))
			{

				@Override
				protected void onTrue(InputProvider input, MouseInputPool pool)
				{
					
				}

				@Override
				protected void onFalse(InputProvider input, MouseInputPool pool)
				{
					
				}
				
			});
			

			buttons.put("button" + count, button);
		}

		return buttons;
	}

}
