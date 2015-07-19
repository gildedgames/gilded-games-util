package com.gildedgames.util.ui.common;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2D.Dim2DBuilder;
import com.gildedgames.util.ui.data.Dim2D.Dim2DModifier;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.data.UIContainer;
import com.gildedgames.util.ui.data.UIContainerMutable;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.listeners.KeyboardListener;
import com.gildedgames.util.ui.listeners.MouseListener;
import com.gildedgames.util.ui.util.GuiViewerHelper;

public class GuiFrame implements Gui, KeyboardListener, MouseListener
{
	
	private boolean visible = true, enabled = true, focused = false;

	private UIContainerMutable mainContent = new UIContainerMutable();
	
	private UIContainerMutable listeners = new UIContainerMutable();
	
	private Dim2D dim = Dim2D.flush();

	public GuiFrame(Dim2D dim)
	{
		this.dim = dim;
	}
	
	@Override
	public UIContainer seekContent()
	{
		return this.mainContent.immutable();
	}

	public UIContainerMutable listeners()
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
		
		GuiViewerHelper.processInitPre(input, this.content(), this.listeners());
	}

	@Override
	public void initContent(InputProvider input)
	{
		 
	}
	
	@Override
	public boolean onKeyboardInput(KeyboardInputPool pool)
	{
		return GuiViewerHelper.processKeyboardInput(pool, this.content(), this.listeners());
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		GuiViewerHelper.processDraw(graphics, input, this.content(), this.listeners());
	}
	
	@Override
	public void tick(InputProvider input, TickInfo tickInfo)
	{
		GuiViewerHelper.processTick(input, tickInfo, this.content(), this.listeners());
	}

	@Override
	public void onMouseInput(InputProvider input, MouseInputPool pool)
	{
		GuiViewerHelper.processMouseInput(input, pool, this.content(), this.listeners());
	}

	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		GuiViewerHelper.processMouseScroll(input, scrollDifference, this.content(), this.listeners());
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

}
