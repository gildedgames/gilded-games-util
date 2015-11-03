package com.gildedgames.util.ui.util.events.slots;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.event.GuiEvent;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.util.GuiCanvas;
import com.gildedgames.util.ui.util.events.DragBehavior;
import com.gildedgames.util.ui.util.factory.Factory;
import com.google.common.base.Function;

public class SlotStackFactory<T> extends GuiEvent<Gui>
{
	
	private Function<T, GuiFrame> iconFactory;
	
	private Factory<T> dataFactory;

	public SlotStackFactory(Function<T, GuiFrame> iconFactory, Factory<T> dataFactory)
	{
		this.iconFactory = iconFactory;
		this.dataFactory = dataFactory;
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
	}
	
	@Override
	public void onMouseInput(MouseInputPool pool, InputProvider input)
	{
		if (this.isActive(pool, input) && input.isHovered(this.getGui().dim()) && pool.has(ButtonState.PRESS))
		{
			GuiCanvas canvas = GuiCanvas.fetch("dragCanvas", 550.0F);

			if (canvas != null)
			{
				T data = this.dataFactory.create();
				GuiFrame icon = this.iconFactory.apply(data);
				
				SlotStack<T> stack = new SlotStack<T>(icon, data);
				
				stack.events().set("dragBehavior", new DragBehavior<T>(), stack);
				
				stack.dim().mod().pos(input.getMouseX(), input.getMouseY()).flush();

				if (canvas.get("draggedObject") != null)
				{
					if (this.shouldRemoveDragged(stack))
					{
						canvas.remove("draggedObject");
					}
						
					return;
				}
				
				canvas.set("draggedObject", stack);
				
				this.onCreateDraggedState();
			}
		}
		
		super.onMouseInput(pool, input);
	}
	
	public boolean isActive(MouseInputPool pool, InputProvider input)
	{
		return true;
	}
	
	public boolean shouldRemoveDragged(SlotStack<T> createdStack)
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
