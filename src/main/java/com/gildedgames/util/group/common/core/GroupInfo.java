package com.gildedgames.util.group.common.core;

import com.gildedgames.util.group.common.permissions.IGroupPerms;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.io.IO;

public class GroupInfo implements IO<IOBridge, IOBridge>
{

	private IGroupPerms permissions;

	private String name;

	private GroupInfo()
	{

	}

	protected GroupInfo(String name, IGroupPerms permissions)
	{
		this.name = name;
		this.permissions = permissions;
	}

	@Override
	public void write(IOBridge output)
	{
		output.setString("name", this.name);
		output.setIO("permissions", this.permissions);
	}

	@Override
	public void read(IOBridge input)
	{
		this.name = input.getString("name");
		this.permissions = input.getIO("permissions");
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
