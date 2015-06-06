package com.gildedgames.util.ui.event;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.ui.common.UIElement;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.data.UIElementContainer;
import com.gildedgames.util.ui.input.InputProvider;

public abstract class ElementEvent implements UIElement
{
	
	private boolean enabled = true;

	@Override
	public void tick(InputProvider input, TickInfo tickInfo)
	{
		
	}

	@Override
	public void onInit(UIElementContainer elementcontainer, InputProvider input)
	{
		
	}
	
	@Override
	public void onResolutionChange(UIElementContainer container, InputProvider input)
	{
		
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
	public void write(NBTTagCompound output)
	{
		
	}

	@Override
	public void read(NBTTagCompound input)
	{
		
	}

}
