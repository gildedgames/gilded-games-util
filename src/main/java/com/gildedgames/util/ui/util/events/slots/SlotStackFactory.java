package com.gildedgames.util.ui.util.events.slots;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.event.GuiEvent;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.util.GuiCanvas;
import com.gildedgames.util.ui.util.events.DragBehavior;
import com.gildedgames.util.ui.util.factory.Factory;
import com.gildedgames.util.ui.util.factory.Function;

public class SlotStackFactory extends GuiEvent
{
	
	private Factory<? extends GuiFrame> iconFactory;
	
	private Function<Object, Object> dataFunction;

	public SlotStackFactory(Factory<? extends GuiFrame> iconFactory, Function<Object, Object> dataFunction)
	{
		this.iconFactory = iconFactory;
		this.dataFunction = dataFunction;
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
	}
	
	@Override
	public void onMouseInput(MouseInputPool pool, InputProvider input)
	{
		if (this.isActive() && input.isHovered(this.getGui().dim()) && pool.has(MouseButton.LEFT) && pool.has(ButtonState.PRESS))
		{
			GuiFrame icon = this.iconFactory.create();
			
			SlotStack stack = new SlotStack(icon, this.dataFunction.apply(icon));
			
			stack.events().set("dragBehavior", new DragBehavior(), stack);
			
			GuiCanvas canvas = GuiCanvas.fetch("dragCanvas");

			if (canvas != null)
			{
				canvas.set("draggedObject", stack);
				
				this.onCreateDraggedState();
			}
		}
		
		super.onMouseInput(pool, input);
	}
	
	public boolean isActive()
	{
		return true;
	}
	
	public void onCreateDraggedState()
	{
		
	}

	@Override
	public void initEvent()
	{
		
	}

}
