package com.gildedgames.util.group.common.notifications;

import java.util.List;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.group.common.core.Group;
import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.notifications.common.core.INotificationResponse;
import com.gildedgames.util.notifications.common.util.AbstractNotificationMessage;

import net.minecraft.entity.player.EntityPlayer;

public class NotificationInvited extends AbstractNotificationMessage
{
	private Group group;

	private GroupMember receiver;

	private NotificationInvited()
	{

	}

	public NotificationInvited(Group group, EntityPlayer sender, EntityPlayer receiver)
	{
		super(sender, receiver);
		this.group = group;
		this.receiver = GroupCore.locate().getPlayers().get(receiver);
	}

	@Override
	public String getTitle()
	{
		return UtilCore.translate("group.invited") + " " + this.group.getName();
	}

	@Override
	public String getDescription()
	{
		return UtilCore.translate("group.inviteddescr1") + " " + this.group.getName() + " " + UtilCore.translate("group.inviteddescr2");
	}

	@Override
	public List<INotificationResponse> getResponses()
	{
		return null;
	}

	@Override
	public void onOpened()
	{
	}

	@Override
	public void onDisposed()
	{
	}

	@Override
	public boolean isRelevant()
	{
		return this.receiver.isInvitedFor(this.group);
	}

	@Override
	public String getKey()
	{
		return "group.invited" + this.group.getName();
	}

	@Override
	protected void writeMessage(IOBridge output)
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void readMessage(IOBridge input)
	{
		// TODO Auto-generated method stub

	}

}
