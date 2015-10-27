package com.gildedgames.util.ui.util.events.slots;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.util.ui.event.GuiEvent;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.util.events.DragBehavior;
import com.gildedgames.util.ui.util.events.DragCanvas;
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
		if (input.isHovered(this.getGui().dim()) && this.parser.onMouseInput(pool, input))
		{
			return;
		}
		
		super.onMouseInput(pool, input);
		
		if (!this.takenContentsOut && pool.has(MouseButton.LEFT) && pool.has(ButtonState.PRESS) && input.isHovered(this.getGui().dim()))
		{
			DragCanvas canvas = DragCanvas.fetch();

			if (canvas != null)
			{
				GuiFrame draggedObject = canvas.getDraggedObject();
				
				if (draggedObject instanceof SlotStack)
				{
					SlotStack stack = (SlotStack)draggedObject;
					
					if (stack.events().contains("dragBehavior"))
					{
						stack.events().remove("dragBehavior");
						
						this.setSlotContents(stack);
						
						canvas.clearDraggedObject();
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
				SlotBehavior.this.getSlotContents().events().set("dragBehavior", new DragBehavior(), SlotBehavior.this.getSlotContents());
				
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
			public boolean isActive()
			{
				return SlotBehavior.this.getSlotContents() != null;
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
