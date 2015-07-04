package com.gildedgames.util.ui.event;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.data.UiContainer;
import com.gildedgames.util.ui.input.InputProvider;

public abstract class UiEvent implements Ui
{
	
	private boolean enabled = true;

	@Override
	public void tick(InputProvider input, TickInfo tickInfo)
	{
		
	}
	
	@Override
	public UiContainer seekContent()
	{
		return new UiContainer();
	}
	
	@Override
	public void init(InputProvider input)
	{
		
	}

	@Override
	public void onResolutionChange(InputProvider input)
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
