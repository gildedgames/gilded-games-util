package com.gildedgames.util.group.client;

import java.util.LinkedHashMap;

import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.data.rect.Dim2D;
import com.gildedgames.util.ui.data.rect.Rect;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.GuiCollection;
import com.gildedgames.util.ui.util.ScreenDimUtil;
import com.gildedgames.util.ui.util.decorators.ScrollableGui;
import com.gildedgames.util.ui.util.factory.ContentFactory;
import com.gildedgames.util.ui.util.transform.GuiPositioner;
import com.gildedgames.util.ui.util.transform.GuiPositionerList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.entity.player.EntityPlayer;

public class GuiGroup extends GuiFrame
{
	private GroupMember member;

	public GuiGroup(EntityPlayer player)
	{
		this.member = GroupCore.locate().getPlayers().get(player);
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		GuiPositioner positioner = new GuiPositionerList(0);
		ContentFactory content = new GroupsButtonContent();

		//buttons.put("title", new TextElement(GuiFactory.text(UtilCore.translate("gui.grouplist"), Color.white, 2.3f), Pos2D.flush(), false));
		GuiCollection notifications = new GuiCollection(Pos2D.flush(), 100, positioner, content);

		ScrollableGui scrollNotifications = new ScrollableGui(Dim2D.build().pos(ScreenDimUtil.getCenter(input)).center(true).area(200, 200).flush(), notifications);

		this.content().set("notifications", scrollNotifications);
	}

	private static class GroupsButtonContent implements ContentFactory
	{
		@Override
		public LinkedHashMap<String, Ui> provideContent(ImmutableMap<String, Ui> currentContent, Rect contentArea)
		{
			LinkedHashMap<String, Ui> buttons = new LinkedHashMap<String, Ui>();

			return buttons;
		}
	}
}
