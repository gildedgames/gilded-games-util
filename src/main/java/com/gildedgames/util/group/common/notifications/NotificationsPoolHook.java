package com.gildedgames.util.group.common.notifications;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.group.common.IGroupPoolListenerClient;
import com.gildedgames.util.group.common.core.Group;
import com.gildedgames.util.group.common.core.GroupInfo;
import com.gildedgames.util.group.common.permissions.GroupPermsDefault;
import com.gildedgames.util.notifications.NotificationCore;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Client sided only. Sends notifications to the player to
 * notify him of group changes. 
 * @author Emile
 *
 */
public class NotificationsPoolHook implements IGroupPoolListenerClient<NotificationsGroupHook>
{

	@Override
	public NotificationsGroupHook createGroupHook(Group group)
	{
		return new NotificationsGroupHook(group);
	}

	@Override
	public void onGroupAdded(Group group)
	{
	}

	@Override
	public void onGroupRemoved(Group group)
	{
	}

	@Override
	public void onGroupInfoChanged(Group group, GroupInfo infoOld, GroupInfo infoNew)
	{
		if (!infoOld.getName().equals(infoNew.getName()))
		{
			this.sendPopup(UtilCore.translate("group.namechanged") + " " + infoNew.getName(), this.getOwner(group));
		}
		else
		{
			this.sendPopup(UtilCore.translate("group.changedperms") + " " + UtilCore.translate(infoNew.getPermissions().getName()), this.getOwner(group));
		}
	}

	private EntityPlayer getOwner(Group group)
	{
		if (group.getPermissions() instanceof GroupPermsDefault)
		{
			return ((GroupPermsDefault) group.getPermissions()).getOwner();
		}
		return null;
	}

	private void sendPopup(String message)
	{
		this.sendPopup(message, UtilCore.proxy.getPlayer());
	}

	private void sendPopup(String message, EntityPlayer player)
	{
		NotificationCore.sendPopup(message, player, UtilCore.proxy.getPlayer());
	}

	@Override
	public void onJoin(Group group)
	{
		this.sendPopup(UtilCore.translate("group.joined" + " " + group.getName()));
	}

	@Override
	public void onLeave(Group group)
	{
		this.sendPopup(UtilCore.translate("group.left" + " " + group.getName()));
	}

	@Override
	public void onInvited(Group group, EntityPlayer inviter)
	{
		this.sendPopup(UtilCore.translate("group.invited") + " " + group.getName(), inviter);
	}

	@Override
	public void onInviteRemoved(Group group)
	{
		this.sendPopup(UtilCore.translate("group.inviteremoved") + " " + group.getName());
	}

}
