package com.gildedgames.util.group.common.network;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.group.common.IGroup;
import com.gildedgames.util.group.common.IGroupPerms;
import com.gildedgames.util.group.common.IGroupPool;
import com.gildedgames.util.group.common.player.GroupMember;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;

public class GroupController implements IGroupController
{
	
	private IGroup targetGroup;
	
	private final Side side;
	
	public GroupController(Side side)
	{
		this.side = side;
	}
	
	@Override
	public void setTargetGroup(IGroup targetGroup)
	{
		this.targetGroup = targetGroup;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		this.targetGroup.write(output);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		this.targetGroup.read(input);
	}

	@Override
	public String getName()
	{
		return this.targetGroup.getName();
	}

	@Override
	public void setName(String name)
	{
		this.targetGroup.setName(name);
	}

	@Override
	public IGroupPerms getPermissions()
	{
		return this.targetGroup.getPermissions();
	}

	@Override
	public void setPermissions(IGroupPerms permissions)
	{
		if (this.side.isClient())
		{
			UtilCore.NETWORK.sendToServer(null);
		}
		else
		{
			UtilCore.NETWORK.sendToAll(null);
		}
		
		this.targetGroup.setPermissions(permissions);
	}

	@Override
	public boolean join(EntityPlayer player)
	{
		
		
		return false;
	}

	@Override
	public boolean leave(EntityPlayer player)
	{
		return false;
	}

	@Override
	public boolean invite(EntityPlayer player)
	{
		return false;
	}

	@Override
	public GroupMember getOwner()
	{
		return null;
	}

	@Override
	public List<GroupMember> getMembers()
	{
		return null;
	}

	@Override
	public IGroupPool getParentPool()
	{
		return null;
	}

}
