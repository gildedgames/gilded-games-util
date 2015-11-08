package com.gildedgames.util.group.common.core;

import java.util.UUID;

import com.gildedgames.util.group.common.permissions.IGroupPerms;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.io.IO;
import com.gildedgames.util.io_manager.util.IOUtil;

public class GroupInfo implements IO<IOBridge, IOBridge>
{

	private IGroupPerms permissions;

	private String name;

	private UUID uuid;

	private GroupInfo()
	{

	}

	public GroupInfo(UUID uuid, String name, IGroupPerms permissions)
	{
		this.name = name;
		this.permissions = permissions;
		this.uuid = uuid;
	}

	@Override
	public void write(IOBridge output)
	{
		output.setString("name", this.name);
		output.setIO("permissions", this.permissions);
		IOUtil.setUUID(this.uuid, output, "uuid");
	}

	@Override
	public void read(IOBridge input)
	{
		this.name = input.getString("name");
		this.permissions = input.getIO("permissions");
		this.uuid = IOUtil.getUUID(input, "uuid");
	}

	public String getName()
	{
		return this.name;
	}

	public IGroupPerms getPermissions()
	{
		return this.permissions;
	}

	public UUID getUUID()
	{
		return this.uuid;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
