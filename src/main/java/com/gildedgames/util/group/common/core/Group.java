package com.gildedgames.util.group.common.core;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.core.nbt.NBTFactory;
import com.gildedgames.util.group.common.permissions.IGroupPerms;
import com.gildedgames.util.io_manager.IOCore;

public final class Group implements NBT
{

	private final GroupPool parentPool;

	private MemberData members;

	private GroupInfo groupInfo;

	protected Group(GroupPool parentPool)
	{
		this.parentPool = parentPool;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		NBTFactory factory = new NBTFactory();

		IOCore.io().set("info", output, factory, this.groupInfo);

		IOCore.io().set("members", output, factory, this.members);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		NBTFactory factory = new NBTFactory();

		this.groupInfo = IOCore.io().get("info", input, factory);

		this.members = IOCore.io().get("members", input, factory);
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
