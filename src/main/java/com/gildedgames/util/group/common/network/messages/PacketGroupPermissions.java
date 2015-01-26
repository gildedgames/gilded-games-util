package com.gildedgames.util.group.common.network.messages;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import com.gildedgames.util.core.CustomPacket;
import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.group.common.IGroup;
import com.gildedgames.util.group.common.IGroupPerms;
import com.gildedgames.util.group.common.IGroupPool;

public class PacketGroupPermissions extends CustomPacket<PacketGroupPermissions>
{
	
	private IGroup group;
	
	private IGroupPerms permissions;

	public PacketGroupPermissions()
	{
	}

	public PacketGroupPermissions(IGroup group, IGroupPerms permissions)
	{
		this.group = group;
		this.permissions = permissions;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		String groupName = ByteBufUtils.readUTF8String(buf);
		String poolID = ByteBufUtils.readUTF8String(buf);
		
		IGroupPool pool = GroupCore.locate().getFromID(poolID);
	
		this.group = pool.get(groupName);
		
		// Read Permissions
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.group.getName());
		ByteBufUtils.writeUTF8String(buf, this.group.getParentPool().getID());
		
		// Write Permissions
	}

	@Override
	public void handleClientSide(PacketGroupPermissions message, EntityPlayer player)
	{
		this.group.setPermissions(this.permissions);
	}

	@Override
	public void handleServerSide(PacketGroupPermissions message, EntityPlayer player)
	{
		if (player instanceof EntityPlayerMP)
		{
			
			this.group.setPermissions(this.permissions);
		}
	}

}
