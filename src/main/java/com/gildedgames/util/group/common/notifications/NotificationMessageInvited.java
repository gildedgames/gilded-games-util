package com.gildedgames.util.group.common.notifications;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.group.GroupModule;
import com.gildedgames.util.group.common.core.Group;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.util.IOUtil;
import com.gildedgames.util.notifications.common.core.INotificationResponse;
import com.gildedgames.util.notifications.common.util.AbstractNotificationMessage;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class NotificationMessageInvited extends AbstractNotificationMessage
{
	private Group group;

	private NotificationMessageInvited()
	{

	}

	public NotificationMessageInvited(UUID sender, UUID receiver, Group group)
	{
		super(sender, receiver);
		this.group = group;
	}

	@Override
	public String getTitle()
	{
		return UtilModule.translate("group.invited") + " " + this.group.getName();
	}

	@Override
	public String getDescription()
	{
		return UtilModule.translate("group.inviteddescr1") + " " + this.group.getName() + " " + UtilModule.translate("group.inviteddescr2");
	}

	@Override
	public List<INotificationResponse> getResponses()
	{
		List<INotificationResponse> responses = new ArrayList<>();
		responses.add(new INotificationResponse()
		{
			@Override
			public String getName()
			{
				return UtilModule.translate("gui.accept");
			}

			@Override
			public boolean onClicked(EntityPlayer player)
			{
				Group group = NotificationMessageInvited.this.group;
				group.getParentPool().addMember(NotificationMessageInvited.this.getReceiver(), group);
				return true;
			}

			@Override
			public GuiScreen openScreenAfterClicked(GuiScreen currentScreen)
			{
				return currentScreen;
			}
		});
		responses.add(new INotificationResponse()
		{
			@Override
			public String getName()
			{
				return UtilModule.translate("gui.decline");
			}

			@Override
			public boolean onClicked(EntityPlayer player)
			{
				Group group = NotificationMessageInvited.this.group;
				group.getParentPool().removeInvitation(NotificationMessageInvited.this.getReceiver(), group);
				return true;
			}

			@Override
			public GuiScreen openScreenAfterClicked(GuiScreen currentScreen)
			{
				return currentScreen;
			}
		});
		return responses;
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
		return GroupModule.locate().getPlayers().get(this.getReceiver()).isInvitedFor(this.group);
	}

	@Override
	public String getKey()
	{
		return UtilModule.translate("group.invited") + " " + this.group.getName();//TODO: Uses getName as key. Might need to be sth else if group name changes
	}

	@Override
	protected void writeMessage(IOBridge output)
	{
		IOUtil.setGroup(output, "group", this.group);
	}

	@Override
	protected void readMessage(IOBridge input)
	{
		this.group = IOUtil.getGroup(input, "group");
	}

}
