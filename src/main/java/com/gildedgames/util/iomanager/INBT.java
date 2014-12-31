package com.gildedgames.util.iomanager;

import net.minecraft.nbt.NBTTagCompound;

public interface INBT
{

	public void writeToNBT(NBTTagCompound tag);

	public void readFromNBT(NBTTagCompound tag);

}
