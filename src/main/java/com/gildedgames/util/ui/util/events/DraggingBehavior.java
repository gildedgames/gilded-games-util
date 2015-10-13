package com.gildedgames.util.ui.util.events;

import java.util.List;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.data.rect.RectModifier;
import com.gildedgames.util.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.util.ui.event.GuiEvent;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;

public class DraggingBehavior extends GuiEvent<GuiFrame>
{
	
	private List<RectModifier> prevModifiers;
	
	private int ticksSinceCreation;

	public DraggingBehavior()
	{
		
	}
	
	@Override
	public void initEvent()
	{
		if (this.prevModifiers == null)
		{
			this.prevModifiers = this.getGui().dim().getModifiersOfType(ModifierType.POS);
		}
		
		this.getGui().dim().mod().disableModifiers(ModifierType.POS).flush();
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
		
		this.ticksSinceCreation++;

		this.getGui().dim().mod().center(true).disableModifiers(ModifierType.POS).pos(Pos2D.flush(input.getMouseX(), input.getMouseY())).flush();
		
		if (MouseButton.LEFT.isDown() && this.ticksSinceCreation > 5)
		{
			GuiFrame frame = ObjectFilter.getType(this.getGui().seekContent().getParentUi(), GuiFrame.class);
			
			if (frame != null)
			{
				frame.events().remove("draggedState");
			}
		}
	}
	
	@Override
	public boolean query(Object... input)
	{
		for (Object obj : input)
		{
			if (obj == DraggingBehavior.class)
			{
				return true;
			}
		}
		
		return false;
	}

}
