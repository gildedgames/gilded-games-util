package com.gildedgames.util.ui.common;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.data.UIContainer;
import com.gildedgames.util.ui.data.UIContainerEvents;
import com.gildedgames.util.ui.data.UIContainerMutable;
import com.gildedgames.util.ui.data.rect.ModDim2D;
import com.gildedgames.util.ui.data.rect.Rect;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.listeners.KeyboardListener;
import com.gildedgames.util.ui.listeners.MouseListener;
import com.gildedgames.util.ui.util.GuiProcessingHelper;

import net.minecraft.nbt.NBTTagCompound;

public class GuiFrame implements Gui, KeyboardListener, MouseListener
{

	private boolean visible = true, enabled = true, focused = false;

	private UIContainerMutable mainContent = new UIContainerMutable(this);

	private UIContainerEvents events = new UIContainerEvents(this);

	private ModDim2D dim = new ModDim2D();

	private List<UIContainer> containers;

	public GuiFrame()
	{

	}

	public GuiFrame(Rect rect)
	{
		this.dim.set(rect);
	}

	@Override
	public UIContainer seekContent()
	{
		return this.mainContent;
	}

	public UIContainerEvents events()
	{
		return this.events;
	}

	protected UIContainerMutable content()
	{
		return this.mainContent;
	}

	@Override
	public boolean isVisible()
	{
		return this.visible;
	}

	@Override
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	@Override
	public boolean isEnabled()
	{
		return this.enabled;
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	@Override
	public ModDim2D dim()
	{
		return this.dim;
	}

	@Override
	public boolean isFocused()
	{
		return this.focused;
	}

	@Override
	public void setFocused(boolean focused)
	{
		this.focused = focused;
	}

	@Override
	public boolean query(Object... input)
	{
		return false;
	}

	@Override
	public void init(InputProvider input)
	{
		this.initContent(input);

		GuiProcessingHelper.processInitPre(this, input, this.content(), this.events());

		this.postInit(input);
	}

	@Override
	public void initContent(InputProvider input)
	{

	}

	protected void postInit(InputProvider input)
	{

	}

	@Override
	public void onClose(InputProvider input)
	{
		GuiProcessingHelper.processClose(input, this.content(), this.events());
	}

	@Override
	public boolean onKeyboardInput(KeyboardInputPool pool, InputProvider input)
	{
		return GuiProcessingHelper.processKeyboardInput(pool, input, this.content(), this.events());
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		GuiProcessingHelper.processDraw(graphics, input, this.content(), this.events());
	}

	@Override
	public void tick(TickInfo tickInfo, InputProvider input)
	{
		GuiProcessingHelper.processTick(input, tickInfo, this.content(), this.events());
	}

	@Override
	public void onMouseInput(MouseInputPool pool, InputProvider input)
	{
		GuiProcessingHelper.processMouseInput(input, pool, this.content(), this.events());
	}

	@Override
	public void onMouseScroll(int scrollDifference, InputProvider input)
	{
		GuiProcessingHelper.processMouseScroll(input, scrollDifference, this.content(), this.events());
	}

	@Override
	public void onResolutionChange(InputProvider input)
	{
		this.init(input);
	}

	@Override
	public void write(NBTTagCompound output)
	{

	}

	@Override
	public void read(NBTTagCompound input)
	{

	}

	@Override
	public int ticksClosing()
	{
		return 0;
	}

	@Override
	public int ticksOpening()
	{
		return 0;
	}

	@Override
	public List<UIContainer> seekAllContent()
	{
		if (this.containers == null)
		{
			this.containers = new ArrayList<UIContainer>();

			this.containers.add(this.mainContent);
			this.containers.add(this.events);
		}

		return this.containers;
	}

	@Override
	public void updateState()
	{
		
	}

}
