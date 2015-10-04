package com.gildedgames.util.ui.common;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2D.Dim2DBuilder;
import com.gildedgames.util.ui.data.Dim2D.Dim2DModifier;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.data.UIContainer;
import com.gildedgames.util.ui.data.UIContainerEvents;
import com.gildedgames.util.ui.data.UIContainerMutable;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.listeners.KeyboardListener;
import com.gildedgames.util.ui.listeners.MouseListener;
import com.gildedgames.util.ui.util.GuiProcessingHelper;

public class GuiFrame implements Gui, KeyboardListener, MouseListener
{
	
	private boolean visible = true, enabled = true, focused = false;

	private UIContainerMutable mainContent = new UIContainerMutable(this);
	
	private UIContainerEvents listeners = new UIContainerEvents(this);
	
	private Dim2D dim;
	
	private List<UIContainer> containers;
	
	public GuiFrame()
	{
		this(Dim2D.flush());
	}

	public GuiFrame(Dim2D dim)
	{
		this.dim = dim;
	}
	
	@Override
	public UIContainer seekContent()
	{
		return this.mainContent;
	}

	public UIContainerEvents listeners()
	{
		return this.listeners;
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
	public Dim2D getDim()
	{
		return this.dim;
	}
	
	@Override
	public void setDim(Dim2D dim)
	{
		this.dim = dim;
	}
	
	@Override
	public Dim2DModifier modDim()
	{
		return new Dim2DModifier(this);
	}
	
	@Override
	public Dim2DBuilder copyDim()
	{
		return Dim2D.build(this);
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
		
		GuiProcessingHelper.processInitPre(this, input, this.content(), this.listeners()); 
	}

	@Override
	public void initContent(InputProvider input)
	{
		 
	}
	
	@Override
	public void onClose(InputProvider input)
	{
		GuiProcessingHelper.processClose(input, this.content(), this.listeners());
	}
	
	@Override
	public boolean onKeyboardInput(KeyboardInputPool pool, InputProvider input)
	{
		return GuiProcessingHelper.processKeyboardInput(pool, input, this.content(), this.listeners());
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		GuiProcessingHelper.processDraw(graphics, input, this.content(), this.listeners());
	}
	
	@Override
	public void tick(TickInfo tickInfo, InputProvider input)
	{
		GuiProcessingHelper.processTick(input, tickInfo, this.content(), this.listeners());
	}

	@Override
	public void onMouseInput(MouseInputPool pool, InputProvider input)
	{
		GuiProcessingHelper.processMouseInput(input, pool, this.content(), this.listeners());
	}

	@Override
	public void onMouseScroll(int scrollDifference, InputProvider input)
	{
		GuiProcessingHelper.processMouseScroll(input, scrollDifference, this.content(), this.listeners());
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
			this.containers.add(this.listeners);
		}
		
		return this.containers;
	}

}
