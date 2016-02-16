package com.gildedgames.util.group.common.notifications;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.group.common.IGroupHook;
import com.gildedgames.util.group.common.core.Group;
import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.notifications.NotificationModule;

import net.minecraft.client.Minecraft;

public class NotificationsGroupHook implements IGroupHook
{
	private Group group;

	public NotificationsGroupHook(Group group)
	{
		this.group = group;
	}

	@Override
	public void onMemberAdded(GroupMember member)
	{
		this.sendPopup(member.getProfile().getUsername() + " " + UtilModule.translate("group.playerjoined"), member);
	}

	private void sendPopup(String message, GroupMember about)
	{
		NotificationModule.sendPopup(message, about.getProfile().getUUID(), Minecraft.getMinecraft().thePlayer.getGameProfile().getId());
	}

	@Override
	public void onMemberRemoved(GroupMember member)
	{
		this.sendPopup(member.getProfile().getUsername() + " " + UtilModule.translate("group.playerleft"), member);
	}

	@Override
	public void onMemberInvited(GroupMember member)
	{
	}

	@Override
	public void onInviteRemoved(GroupMember member)
	{
	}

	@Override
	public void write(IOBridge output)
	{
	}

	@Override
	public void read(IOBridge input)
	{
	}

}
