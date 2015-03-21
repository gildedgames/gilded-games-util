package com.gildedgames.util.group.common.core;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.core.nbt.NBTFactory;
import com.gildedgames.util.group.common.permissions.IGroupPerms;
import com.gildedgames.util.io_manager.IOCore;

public class GroupInfo implements NBT
{

	private IGroupPerms permissions;

	private String name;

	protected GroupInfo(String name, IGroupPerms permissions)
	{
		this.name = name;
		this.permissions = permissions;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		NBTFactory factory = new NBTFactory();
		output.setString("name", this.name);
		IOCore.io().set("permissions", output, factory, this.permissions);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		NBTFactory factory = new NBTFactory();
		this.name = input.getString("name");
		this.permissions = IOCore.io().get("permissions", input, factory);
	}

	public String getName()
	{
		return this.name;
	}

	public IGroupPerms getPermissions()
	{
		return this.permissions;
	}

}
