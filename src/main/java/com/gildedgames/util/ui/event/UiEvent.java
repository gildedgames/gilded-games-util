package com.gildedgames.util.ui.event;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.data.UIContainer;
import com.gildedgames.util.ui.input.InputProvider;

public abstract class UiEvent implements Ui
{
	
	private boolean enabled = true;

	@Override
	public void tick(TickInfo tickInfo, InputProvider input)
	{
		
	}
	
	@Override
	public UIContainer seekContent()
	{
		return new UIContainer();
	}
	
	@Override
	public void init(InputProvider input)
	{
		
	}
	
	@Override
	public void initContent(InputProvider input)
	{
		
	}
	
	@Override
	public void onClose(InputProvider input)
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
