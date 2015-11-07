package com.gildedgames.util.group.common.notifications;

import java.util.UUID;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.group.common.IGroupPoolListenerClient;
import com.gildedgames.util.group.common.core.Group;
import com.gildedgames.util.group.common.core.GroupInfo;
import com.gildedgames.util.group.common.permissions.GroupPermsDefault;
import com.gildedgames.util.notifications.NotificationCore;

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

	private UUID getOwner(Group group)
	{
		if (group.getPermissions() instanceof GroupPermsDefault)
		{
			return GroupCore.locate().getPlayers().get(((GroupPermsDefault) group.getPermissions()).owner()).getProfile().getUUID();
		}
		return null;
	}

	private void sendPopup(String message)
	{
		this.sendPopup(message, UtilCore.proxy.getPlayer().getGameProfile().getId());
	}

	private void sendPopup(String message, UUID uuid)
	{
		NotificationCore.sendPopup(message, uuid, UtilCore.proxy.getPlayer().getGameProfile().getId());
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
	public void onInvited(Group group, UUID inviter)
	{
		NotificationCore.sendNotification(new NotificationMessageInvited(inviter, UtilCore.proxy.getPlayer().getGameProfile().getId(), group));
	}

	@Override
	public void onInviteRemoved(Group group)
	{
		this.sendPopup(UtilCore.translate("group.inviteremoved") + " " + group.getName());
	}

}
