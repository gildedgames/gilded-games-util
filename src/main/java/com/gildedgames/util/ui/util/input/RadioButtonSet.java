package com.gildedgames.util.ui.util.input;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.rect.Dim2D;
import com.gildedgames.util.ui.data.rect.Rect;
import com.gildedgames.util.ui.event.view.MouseEventGui;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInput;
import com.gildedgames.util.ui.input.MouseInputPool;

public class RadioButtonSet extends GuiFrame
{
	
	private List<RadioButton> buttons = new ArrayList<RadioButton>();

	public RadioButtonSet()
	{
		
	}
	
	public void add(RadioButton button)
	{
		this.buttons.add(button);
		
		this.setButtons();
	}
	
	public boolean remove(RadioButton button)
	{
		boolean flag = this.buttons.remove(button);
		
		this.setButtons();
		
		return flag;
	}
	
	private void setButtons()
	{
		this.content().clear();
		
		List<Rect> rects = new ArrayList<Rect>();
		
		for (final RadioButton button : this.buttons)
		{
			button.events().set("click", new MouseEventGui(new MouseInput(MouseButton.LEFT, ButtonState.PRESSED))
			{

				@Override
				protected void onTrue(InputProvider input, MouseInputPool pool)
				{
					button.setOn(true);
					
					for (RadioButton rButton : RadioButtonSet.this.buttons)
					{
						if (rButton != button)
						{
							rButton.setOn(false);
						}
					}
				}

				@Override
				protected void onFalse(InputProvider input, MouseInputPool pool)
				{
					
				}

				@Override
				public void initEvent() {
					
				}
				
			});
			
			button.dim().mod().pos(0,  this.content().size() * 20).flush();
			
			this.content().set(String.valueOf(this.content().size()), button);
			
			rects.add(button.dim());
		}
		
		Rect combined = Dim2D.combine(rects);

		this.dim().mod().area(combined.width(), combined.height()).flush();
	}
	
	@Override
	public void initContent(InputProvider input)
	{
		this.setButtons();
	}
	
}
