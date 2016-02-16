package com.gildedgames.util.group.common.core;

import java.util.UUID;

import com.gildedgames.util.group.GroupModule;
import com.gildedgames.util.group.common.permissions.IGroupPerms;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.io.IO;

public final class Group implements IO<IOBridge, IOBridge>
{

	private GroupPool parentPool;

	private MemberData members;

	private GroupInfo groupInfo;

	private Group()
	{

	}

	protected Group(GroupPool parentPool)
	{
		this.parentPool = parentPool;
	}

	@Override
	public void write(IOBridge output)
	{
		output.setString("pool", this.parentPool.getID());
		output.setIO("info", this.groupInfo);
		output.setBoolean("hasMemberData", this.members != null);
		if (this.members != null)
		{
			this.members.write(output);
		}
	}

	@Override
	public void read(IOBridge input)
	{
		this.parentPool = GroupModule.locate().getPoolFromID(input.getString("pool"));
		this.groupInfo = input.getIO("info");
		if (input.getBoolean("hasMemberData"))
		{
			this.members = new MemberData();
			this.members.read(input);
		}
	}

	public boolean hasMemberData()
	{
		return this.members != null;
	}

	public MemberData getMemberData()
	{
		return this.members;
	}

	protected void setMemberData(MemberData memberData)
	{
		this.members = memberData;
	}

	public GroupPool getParentPool()
	{
		return this.parentPool;
	}

	public String getName()
	{
		return this.groupInfo.getName();
	}

	public IGroupPerms getPermissions()
	{
		return this.groupInfo.getPermissions();
	}

	public UUID getUUID()
	{
		return this.groupInfo.getUUID();
	}

	public GroupInfo getGroupInfo()
	{
		return this.groupInfo;
	}

	protected void setGroupInfo(GroupInfo groupInfo)
	{
		this.groupInfo = groupInfo;
	}

}
