package com.gildedgames.util.ui.util.events.slots;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.util.ui.event.GuiEvent;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.util.GuiCanvas;
import com.gildedgames.util.ui.util.events.DragBehavior;
import com.gildedgames.util.ui.util.factory.Factory;
import com.gildedgames.util.ui.util.factory.Function;

public class SlotBehavior extends GuiEvent<GuiFrame>
{

	private SlotStack slotContents;
	
	private SlotParser parser;
	
	private boolean takenContentsOut;

	public SlotBehavior(SlotParser parser)
	{
		this.parser = parser;
	}
	
	public void setSlotContents(SlotStack draggedState)
	{
		this.setSlotContents(draggedState, true);
	}
	
	public void clearSlotContents()
	{
		this.slotContents = null;
		this.content().remove("slotContents");

		this.parser.onContentsChange(SlotBehavior.this, null);
	}

	public void setSlotContents(SlotStack draggedState, boolean notifyParser)
	{
		if (draggedState == null)
		{
			return;
		}
		
		if (notifyParser && !this.parser.isAllowed(draggedState))
		{
			return;
		}
		
		this.slotContents = draggedState;

		this.slotContents.dim().clear(ModifierType.POS);
		this.slotContents.dim().mod().resetPos().x(this.getGui().dim().width() / 2).y(this.getGui().dim().height() / 2).center(true).flush();

		this.content().set("slotContents", this.slotContents);

		if (notifyParser)
		{
			this.parser.onContentsChange(this, this.slotContents);
		}
	}

	public SlotStack getSlotContents()
	{
		return this.slotContents;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);

		if (this.slotContents != null)
		{
			this.slotContents.dim().mod().center(true).flush();
		}
	}

	@Override
	public void onMouseInput(MouseInputPool pool, InputProvider input)
	{
		if (input.isHovered(this.getGui().dim()) && pool.has(ButtonState.PRESS) && this.parser.onMouseInput(pool, input))
		{
			return;
		}
		
		super.onMouseInput(pool, input);
		
		if (!this.takenContentsOut && pool.has(ButtonState.PRESS) && input.isHovered(this.getGui().dim()))
		{
			GuiCanvas canvas = GuiCanvas.fetch("dragCanvas", false);

			if (canvas != null)
			{
				GuiFrame draggedObject = canvas.get("draggedObject");
				
				if (draggedObject instanceof SlotStack)
				{
					SlotStack stack = (SlotStack)draggedObject;
					
					if (stack.events().contains("dragBehavior"))
					{
						SlotStack original = this.getSlotContents();
						
						stack.events().remove("dragBehavior");
						
						this.setSlotContents(stack);
						
						canvas.remove("draggedObject");
						
						if (original != null)
						{
							original.events().set("dragBehavior", new DragBehavior(), original);

							canvas.set("draggedObject", original);
						}
					}
				}
			}
		}
		
		this.takenContentsOut = false;
	}

	@Override
	public void initEvent()
	{
		Factory<GuiFrame> iconFactory = new Factory<GuiFrame>()
		{

			@Override
			public GuiFrame create()
			{
				return SlotBehavior.this.getSlotContents();
			}

		};

		Function<Object, Object> dataFunction = new Function<Object, Object>()
		{

			@Override
			public Object apply(Object input)
			{
				return SlotBehavior.this.getSlotContents().getData();
			}
	
		};
		
		this.getGui().events().set("dragFactory", new SlotStackFactory(iconFactory, dataFunction)
		{
			
			@Override
			public boolean shouldRemoveDragged(SlotStack createdStack)
			{
				return false;
			}

			@Override
			public boolean isActive(MouseInputPool pool, InputProvider input)
			{
				return SlotBehavior.this.getSlotContents() != null && (!input.isHovered(this.getGui().dim()) || !SlotBehavior.this.parser.onMouseInput(pool, input));
			}

			@Override
			public void onCreateDraggedState()
			{
				SlotBehavior.this.takenContentsOut = true;

				SlotBehavior.this.slotContents = null;
				SlotBehavior.this.content().remove("slotContents");

				SlotBehavior.this.parser.onContentsChange(SlotBehavior.this, null);
			}

		});
	}

}
