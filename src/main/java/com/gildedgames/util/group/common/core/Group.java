package com.gildedgames.util.group.common.core;

import com.gildedgames.util.group.common.permissions.IGroupPerms;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.io.IO;

public final class Group implements IO<IOBridge, IOBridge>
{

	private final GroupPool parentPool;

	private MemberData members;

	private GroupInfo groupInfo;

	protected Group(GroupPool parentPool)
	{
		this.parentPool = parentPool;
	}

	@Override
	public void write(IOBridge output)
	{
		output.setIO("info", this.groupInfo);
		output.setIO("members", this.members);
	}

	@Override
	public void read(IOBridge input)
	{
		this.groupInfo = input.getIO("info");
		this.members = input.getIO("members");
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

	protected void setGroupInfo(GroupInfo groupInfo)
	{
		this.groupInfo = groupInfo;
	}

}
