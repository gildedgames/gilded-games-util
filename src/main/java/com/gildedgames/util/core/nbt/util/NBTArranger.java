package com.gildedgames.util.core.nbt.util;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.io_manager.factory.OutputArranger;

public class NBTArranger extends NBTTagCompound implements OutputArranger<NBTTagCompound>
{

	@Override
	public NBTTagCompound getOutputWrapper()
	{
		return this;
	}

	@Override
	public void rearrange(ArrayList<String> recordedKeys)
	{
		/** TO-DO : Rearrange this.tagMap based on given recordKeys. **/
	}

}