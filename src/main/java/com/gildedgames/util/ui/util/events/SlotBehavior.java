package com.gildedgames.util.ui.util.events;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.UIContainer;
import com.gildedgames.util.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.util.ui.event.GuiEvent;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.util.factory.Factory;
import com.gildedgames.util.ui.util.factory.Function;

public class SlotBehavior extends GuiEvent<GuiFrame>
{

	private DraggedState slotContents;
	
	private SlotParser parser;
	
	private boolean takenContentsOut;

	public SlotBehavior(SlotParser parser)
	{
		this.parser = parser;
	}

	public void setSlotContents(DraggedState draggedState)
	{
		if (!this.parser.isAllowed(draggedState))
		{
			return;
		}
		
		this.slotContents = draggedState;

		this.slotContents.dim().clear(ModifierType.POS);
		this.slotContents.dim().mod().resetPos().x(this.getGui().dim().width() / 2).y(this.getGui().dim().height() / 2).center(true).flush();

		this.content().set("slotContents", this.slotContents);

		this.parser.onContentsChange(this.slotContents);
	}

	public DraggedState getSlotContents()
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
		super.onMouseInput(pool, input);
		
		if (!this.takenContentsOut && pool.has(MouseButton.LEFT) && pool.has(ButtonState.PRESS) && input.isHovered(this.getGui().dim()))
		{
			UIContainer topParent = this.content().getTopParent();

			List<Gui> draggables = new ArrayList<Gui>();

			for (UIContainer container : topParent.getAttachedUi().seekAllContent())
			{
				draggables.addAll(container.queryAll(DragBehavior.class));
			}

			if (draggables.size() >= 1 && draggables.get(0) instanceof DragBehavior)
			{
				DragBehavior behavior = (DragBehavior) draggables.get(0);

				this.setSlotContents(behavior.getGui());

				if (behavior.getGui() instanceof GuiFrame)
				{
					GuiFrame frame = behavior.getGui();

					frame.events().remove(behavior);
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
		
		this.getGui().events().set("dragFactory", new DragFactory(iconFactory, dataFunction)
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
				SlotBehavior.this.content().remove("draggedState");

				SlotBehavior.this.parser.onContentsChange(null);
			}

		});
	}

}
