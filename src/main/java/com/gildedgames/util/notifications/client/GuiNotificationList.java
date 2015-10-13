package com.gildedgames.util.notifications.client;

import java.awt.Color;
import java.util.LinkedHashMap;

import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.core.gui.util.wrappers.MinecraftButton;
import com.gildedgames.util.notifications.NotificationCore;
import com.gildedgames.util.notifications.common.core.INotificationMessage;
import com.gildedgames.util.notifications.common.player.PlayerNotification;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.GuiCollection;
import com.gildedgames.util.ui.util.ScreenDimUtil;
import com.gildedgames.util.ui.util.TextElement;
import com.gildedgames.util.ui.util.decorators.ScrollableGui;
import com.gildedgames.util.ui.util.factory.ContentFactory;
import com.gildedgames.util.ui.util.transform.GuiPositioner;
import com.gildedgames.util.ui.util.transform.GuiPositionerList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.entity.player.EntityPlayer;

public class GuiNotificationList extends GuiFrame
{
	protected PlayerNotification player;

	public GuiNotificationList(EntityPlayer player)
	{
		this.player = NotificationCore.getPlayerNotifications(player);
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		GuiPositioner positioner = new GuiPositionerList(0);
		ContentFactory content = new NotificationButtonContent(this.player);

		GuiCollection notifications = new GuiCollection(Pos2D.flush(), 100, positioner, content);

		ScrollableGui scrollNotifications = new ScrollableGui(Dim2D.build().pos(ScreenDimUtil.getCenter(input)).center(true).area(100, 400).flush(), notifications);

	}

	private static class NotificationButtonContent implements ContentFactory
	{

		private PlayerNotification player;

		protected NotificationButtonContent(PlayerNotification player)
		{
			this.player = player;
		}

		@Override
		public LinkedHashMap<String, Ui> provideContent(ImmutableMap<String, Ui> currentContent, Dim2D contentArea)
		{
			LinkedHashMap<String, Ui> buttons = new LinkedHashMap<String, Ui>();

			for (INotificationMessage message : this.player.getNotifications())
			{
				buttons.put(message.getKey(), new NotificationButton(message));
			}

			return buttons;
		}

	}

	private static class NotificationButton extends GuiFrame
	{
		private INotificationMessage message;

		protected NotificationButton(INotificationMessage message)
		{
			super(Dim2D.build().area(100, 20).flush());
			this.message = message;
		}

		@Override
		public void initContent(InputProvider input)
		{
			super.initContent(input);

			this.content().set("button", new MinecraftButton(Dim2D.build().buildWith(this).area().flush(), ""));
			this.content().set("title", new TextElement(GuiFactory.text(this.message.getTitle(), new Color(0xE5E5E5)), Pos2D.flush(1, 2), false));
			this.content().set("from", new TextElement(GuiFactory.text("From: ", Color.BLACK, 0.75f), Pos2D.flush(1, 12), false));
			this.content().set("username", new TextElement(GuiFactory.text(this.message.getSender().getCommandSenderName(), new Color(0x8FE639), 0.75f), Pos2D.flush(1, 12), false));
		}

	}
}
