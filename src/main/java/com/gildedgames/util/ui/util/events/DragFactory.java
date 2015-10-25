package com.gildedgames.util.ui.util.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import com.gildedgames.util.core.gui.viewing.MinecraftGuiWrapper;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.event.GuiEvent;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.util.factory.Factory;
import com.gildedgames.util.ui.util.factory.Function;

public class DragFactory extends GuiEvent
{
	
	private Factory<? extends GuiFrame> iconFactory;
	
	private Function<Object, Object> dataFunction;

	public DragFactory(Factory<? extends GuiFrame> iconFactory, Function<Object, Object> dataFunction)
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
			
			DraggedState draggedState = new DraggedState(icon, this.dataFunction.apply(icon));
			
			draggedState.events().set("dragBehavior", new DragBehavior(), draggedState);

			GuiScreen screen = Minecraft.getMinecraft().currentScreen;
			
			if (screen instanceof MinecraftGuiWrapper)
			{
				MinecraftGuiWrapper wrapper = (MinecraftGuiWrapper)screen;
				
				wrapper.getFrame().events().set("draggedState", draggedState);
				//UiCore.locate().getCurrentFrame().events().set("draggedState", draggedState);
				
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
