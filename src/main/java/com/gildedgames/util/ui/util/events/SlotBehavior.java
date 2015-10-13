package com.gildedgames.util.ui.util.events;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.UIContainer;
import com.gildedgames.util.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.util.ui.event.GuiEvent;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.util.factory.GenericFactory;

public abstract class SlotBehavior extends GuiEvent<GuiFrame>
{

	private GuiFrame slotContents;

	private int cooldown;

	public static final int COOLDOWN_REQUIRED = 5;

	public SlotBehavior()
	{

	}

	public abstract void onSlotChange();

	public void setSlotContents(GuiFrame gui)
	{
		this.slotContents = gui;

		this.slotContents.dim().disableModifiers(ModifierType.POS);
		this.slotContents.dim().mod().resetPos().scale(0.75F).x(6.5F).y(6.75F).flush();

		this.content().set("slotContents", this.slotContents);

		this.onSlotChange();

		this.cooldown = 0;
	}

	public GuiFrame getSlotContents()
	{
		return this.slotContents;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);

		this.cooldown++;

		if (this.slotContents != null)
		{
			this.slotContents.dim().mod().center(true).flush();
		}

		if (this.cooldown > COOLDOWN_REQUIRED && MouseButton.LEFT.isDown() && input.isHovered(this.getGui().dim()))
		{
			UIContainer topParent = this.content().getTopParent();

			List<Gui> draggables = new ArrayList<Gui>();

			for (UIContainer container : topParent.getAttachedUi().seekAllContent())
			{
				draggables.addAll(container.queryAll(DraggingBehavior.class));
			}

			if (draggables.size() >= 1 && draggables.get(0) instanceof DraggingBehavior)
			{
				DraggingBehavior behavior = (DraggingBehavior) draggables.get(0);

				this.setSlotContents(behavior.getGui());

				if (behavior.getGui() instanceof GuiFrame)
				{
					GuiFrame frame = behavior.getGui();

					frame.events().remove(behavior);
				}
			}
		}
	}

	@Override
	public void initEvent()
	{
		GenericFactory<GuiFrame> factory = new GenericFactory<GuiFrame>()
		{

			@Override
			public GuiFrame create()
			{
				return SlotBehavior.this.getSlotContents();
			}

		};

		this.getGui().events().set("draggableBehavior", new DraggableBehavior(factory)
		{

			@Override
			public boolean isActive()
			{
				return SlotBehavior.this.getSlotContents() != null && SlotBehavior.this.cooldown > COOLDOWN_REQUIRED;
			}

			@Override
			public void onCreateDraggedState()
			{
				SlotBehavior.this.slotContents = null;

				SlotBehavior.this.onSlotChange();

				SlotBehavior.this.cooldown = 0;
			}

		});
	}

}
