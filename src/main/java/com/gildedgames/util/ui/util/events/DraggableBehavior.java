package com.gildedgames.util.ui.util.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import com.gildedgames.util.core.gui.viewing.MinecraftGuiWrapper;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.event.GuiEvent;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.util.factory.GenericFactory;

public class DraggableBehavior extends GuiEvent
{
	
	private GenericFactory<? extends GuiFrame> draggedStateFactory;

	public DraggableBehavior(GenericFactory<? extends GuiFrame> draggedStateFactory)
	{
		this.draggedStateFactory = draggedStateFactory;
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
		
		Dim2D dim = this.getGui().getDim();
		boolean isHovered = input.isHovered(dim);
		boolean isPressed = MouseButton.LEFT.isDown();
		
		if (isHovered && isPressed)
		{
			GuiFrame draggedState = this.draggedStateFactory.create();
			
			draggedState.listeners().set("draggingBehavior", new DraggingBehavior());
			
			GuiScreen screen = Minecraft.getMinecraft().currentScreen;
			
			if (screen instanceof MinecraftGuiWrapper)
			{
				MinecraftGuiWrapper wrapper = (MinecraftGuiWrapper)screen;
				
				wrapper.getFrame().listeners().set("draggedState", draggedState);
			}
		}
	}

	@Override
	public void initEvent()
	{
		
	}

}
