package com.gildedgames.util.ui.util.events;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Dim2D.ModifierType;
import com.gildedgames.util.ui.data.UIContainer;
import com.gildedgames.util.ui.event.GuiEvent;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;

public abstract class SlotBehavior extends GuiEvent
{
	
	private Gui slotContents;

	public SlotBehavior()
	{
		
	}
	
	public abstract void onSlotChange();

	public void setSlotContents(Gui gui)
	{
		this.slotContents = gui;
		
		this.slotContents.modDim().clearModifiers(ModifierType.POS).resetPos().flush();

		this.content().set("slotContents", this.slotContents);
		
		this.onSlotChange();
	}
	
	public Gui getSlotContents()
	{
		return this.slotContents;
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
		
		if (this.slotContents != null)
		{
			this.slotContents.modDim().center(true).x(this.getGui().getDim().width() / 2).y(this.getGui().getDim().height() / 2).flush();
		}

		if (MouseButton.LEFT.isDown() && input.isHovered(this.getGui().getDim()))
		{
			UIContainer topParent = this.content().getTopParent();
			
			List<Gui> draggables = new ArrayList<Gui>();
			
			for (UIContainer container : topParent.getAttachedUi().seekAllContent())
			{
				draggables.addAll(container.queryAll(DraggingBehavior.class));
			}
			
			if (draggables.size() >= 1 && draggables.get(0) instanceof DraggingBehavior)
			{
				DraggingBehavior behavior = (DraggingBehavior)draggables.get(0);
				
				this.setSlotContents(behavior.getGui());
				
				if (behavior.getGui() instanceof GuiFrame)
				{
					GuiFrame frame = (GuiFrame)behavior.getGui();
					
					frame.listeners().remove(behavior);
				}
			}
		}
	}

	@Override
	public void initEvent()
	{
		
	}

}
