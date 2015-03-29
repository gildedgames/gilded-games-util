package com.gildedgames.util.group.common.notifications;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.group.common.IGroupHook;
import com.gildedgames.util.group.common.core.Group;
import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.notifications.NotificationCore;

public class NotificationsGroupHook implements IGroupHook
{
	Group group;

	public NotificationsGroupHook(Group group)
	{
		this.group = group;
	}

	@Override
	public void write(NBTTagCompound output)
	{
	}

	@Override
	public void read(NBTTagCompound input)
	{
	}

	@Override
	public void onMemberAdded(GroupMember member)
	{
		this.sendPopup(member.getProfile().getUsername() + " " + UtilCore.translate("group.playerjoined"), member);
	}

	private void sendPopup(String message, GroupMember about)
	{
		NotificationCore.sendPopup(message, about.getProfile().getEntity(), Minecraft.getMinecraft().thePlayer);
	}

	@Override
	public void onMemberRemoved(GroupMember member)
	{
		this.sendPopup(member.getProfile().getUsername() + " " + UtilCore.translate("group.playerleft"), member);
	}

	@Override
	public void onMemberInvited(GroupMember member)
	{
	}

	@Override
	public void onInviteRemoved(GroupMember member)
	{
	}

}
