package com.gildedgames.util.modules.notifications.client.gui;

import java.awt.Color;
import java.util.LinkedHashMap;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.core.gui.util.decorators.MinecraftGui;
import com.gildedgames.util.core.gui.util.wrappers.MinecraftButton;
import com.gildedgames.util.modules.notifications.common.core.INotificationMessage;
import com.gildedgames.util.modules.notifications.common.core.INotificationResponse;
import com.gildedgames.util.modules.notifications.common.networking.messages.PacketClickedResponse;
import com.gildedgames.util.modules.ui.UiModule;
import com.gildedgames.util.modules.ui.common.GuiFrame;
import com.gildedgames.util.modules.ui.common.Ui;
import com.gildedgames.util.modules.ui.data.Pos2D;
import com.gildedgames.util.modules.ui.data.rect.Dim2D;
import com.gildedgames.util.modules.ui.data.rect.Rect;
import com.gildedgames.util.modules.ui.input.ButtonState;
import com.gildedgames.util.modules.ui.input.InputProvider;
import com.gildedgames.util.modules.ui.input.MouseButton;
import com.gildedgames.util.modules.ui.input.MouseInputPool;
import com.gildedgames.util.modules.ui.util.GuiCollection;
import com.gildedgames.util.modules.ui.util.InputHelper;
import com.gildedgames.util.modules.ui.util.TextElement;
import com.gildedgames.util.modules.ui.util.decorators.ScrollableGui;
import com.gildedgames.util.modules.ui.util.factory.ContentFactory;
import com.gildedgames.util.modules.ui.util.transform.GuiPositioner;
import com.gildedgames.util.modules.ui.util.transform.GuiPositionerList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.client.Minecraft;

public class GuiNotification extends GuiFrame
{
	private INotificationMessage message;

	public GuiNotification(INotificationMessage message)
	{
		this.message = message;
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);
		GuiPositioner positioner = new GuiPositionerList(0);
		ContentFactory<Ui> content = new NotificationContent(this.message);

		GuiCollection<Ui> notifications = new GuiCollection<>(Pos2D.flush(), 100, positioner, content);

		ScrollableGui scrollNotifications = new ScrollableGui(Dim2D.build().pos(InputHelper.getCenter(input)).center(true).area(200, 200).flush(), notifications);

		this.content().set("notification", scrollNotifications);
	}

	private static class NotificationContent implements ContentFactory<Ui>
	{
		private INotificationMessage message;

		public NotificationContent(INotificationMessage message)
		{
			this.message = message;
		}

		@Override
		public LinkedHashMap<String, Ui> provideContent(ImmutableMap<String, Ui> currentContent, Rect contentArea)
		{
			LinkedHashMap<String, Ui> buttons = new LinkedHashMap<>();
			buttons.put("title", new TextElement(GuiFactory.text(this.message.getTitle(), Color.white, 2.3f), Dim2D.flush()));

			String senderName = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(this.message.getSender()).getGameProfile().getName();

			buttons.put("sender", new TextElement(GuiFactory.text(UtilModule.translate("gui.from") + senderName, Color.YELLOW), Dim2D.flush()));
			buttons.put("description", GuiFactory.textBox(Dim2D.build().width(200).flush(), false, GuiFactory.text(this.message.getDescription(), Color.white)));

			int i = 0;
			for (INotificationResponse response : this.message.getResponses())
			{
				buttons.put(Integer.toString(i), new ResponseButton(this.message, response, i));
				i++;
			}

			buttons.put("back", new MinecraftButton(Dim2D.build().area(175, 20).flush(), UtilModule.translate("gui.back"))
			{
				@Override
				public void onMouseInput(MouseInputPool pool, InputProvider input)
				{
					super.onMouseInput(pool, input);
					if (input.isHovered(this) && pool.has(MouseButton.LEFT) && pool.has(ButtonState.PRESS))
					{
						UiModule.locate().open("", new MinecraftGui(new GuiNotifications(Minecraft.getMinecraft().thePlayer)));
					}
				}
			});
			return buttons;
		}

	}

	private static class ResponseButton extends GuiFrame
	{
		private INotificationMessage message;

		private INotificationResponse response;

		private int index;

		public ResponseButton(INotificationMessage message, INotificationResponse response, int index)
		{
			super(Dim2D.build().area(175, 20).flush());
			this.message = message;
			this.response = response;
			this.index = index;
		}

		@Override
		public void initContent(InputProvider input)
		{
			super.initContent(input);
			this.content().set("button", new MinecraftButton(Dim2D.build().buildWith(this).area().flush(), ""));
			this.content().set("title", new TextElement(GuiFactory.text(this.response.getName(), new Color(0xE5E5E5)), Dim2D.build().pos(1, 2).flush()));
		}

		@Override
		public void onMouseInput(MouseInputPool pool, InputProvider input)
		{
			super.onMouseInput(pool, input);
			if (input.isHovered(this) && pool.has(MouseButton.LEFT) && pool.has(ButtonState.PRESS))
			{
				UtilModule.NETWORK.sendToServer(new PacketClickedResponse(this.message, this.index));
				this.response.openScreenAfterClicked(UiModule.locate().getGuiScreen());
			}
		}
	}

}
