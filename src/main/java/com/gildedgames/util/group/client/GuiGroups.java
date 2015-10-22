package com.gildedgames.util.group.client;

import java.awt.Color;
import java.util.LinkedHashMap;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.core.gui.util.decorators.MinecraftGui;
import com.gildedgames.util.core.gui.util.wrappers.MinecraftButton;
import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.group.common.core.Group;
import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.ui.UiCore;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.data.rect.Dim2D;
import com.gildedgames.util.ui.data.rect.Rect;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.util.ScreenDimUtil;
import com.gildedgames.util.ui.util.TextElement;
import com.gildedgames.util.ui.util.decorators.ScrollableGui;
import com.gildedgames.util.ui.util.factory.ContentFactory;
import com.gildedgames.util.ui.util.input.RadioButton;
import com.gildedgames.util.ui.util.input.RadioButtonSet;
import com.gildedgames.util.ui.util.transform.GuiPositioner;
import com.gildedgames.util.ui.util.transform.GuiPositionerList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class GuiGroups extends GuiFrame
{
	private GroupMember member;

	public GuiGroups(EntityPlayer player)
	{
		this.member = GroupCore.locate().getPlayers().get(player);
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		GuiPositioner positioner = new GuiPositionerList(0);

		//buttons.put("title", new TextElement(GuiFactory.text(UtilCore.translate("gui.grouplist"), Color.white, 2.3f), Pos2D.flush(), false));
		final RadioButtonSet groups = new RadioButtonSet(Pos2D.flush(), 100, positioner, new GroupsButtonContent(this.member));

		ScrollableGui scrollGroups = new ScrollableGui(Dim2D.build().pos(ScreenDimUtil.getCenter(input)).center(true).area(200, 200).flush(), groups);

		this.content().set("groups", scrollGroups);

		this.content().set("join", new MinecraftButton(Dim2D.build().pos(130, 200).area(75, 20).flush(), UtilCore.translate("gui.join"))
		{
			@Override
			public void onMouseInput(MouseInputPool pool, InputProvider input)
			{
				super.onMouseInput(pool, input);
				if (input.isHovered(this) && pool.has(MouseButton.LEFT) && pool.has(ButtonState.PRESSED))
				{
					groups.confirm();
				}
			}
		});

		this.content().set("create", new MinecraftButton(Dim2D.build().pos(210, 200).area(75, 20).flush(), UtilCore.translate("gui.create"))
		{
			@Override
			public void onMouseInput(MouseInputPool pool, InputProvider input)
			{
				super.onMouseInput(pool, input);
				if (input.isHovered(this) && pool.has(MouseButton.LEFT) && pool.has(ButtonState.PRESSED))
				{
					UiCore.locate().open("", new MinecraftGui(new GuiCreateGroup()));
				}
			}
		});
	}

	private static class GroupsButtonContent implements ContentFactory<RadioButton>
	{
		private GroupMember member;

		public GroupsButtonContent(GroupMember member)
		{
			this.member = member;
		}

		@Override
		public LinkedHashMap<String, RadioButton> provideContent(ImmutableMap<String, Ui> currentContent, Rect contentArea)
		{
			LinkedHashMap<String, RadioButton> buttons = new LinkedHashMap<String, RadioButton>();
			for (Group group : GroupCore.locate().getDefaultPool().getGroups())
			{
				if (group.getPermissions().canJoin(this.member))
				{
					buttons.put(group.getName(), new GroupButton(group));
				}
			}

			return buttons;
		}
	}

	private static class GroupButton extends RadioButtonDefault
	{
		private Group group;

		public GroupButton(Group group)
		{
			super(175, 20);
			this.group = group;
		}

		@Override
		public void initContent(InputProvider input)
		{
			super.initContent(input);
			this.content().set("group", new TextElement(GuiFactory.text(this.group.getName(), new Color(0xE5E5E5)), Dim2D.build().pos(1, 2).flush()));
			this.content().set("perm", new TextElement(GuiFactory.text(this.group.getPermissions().getName(), Color.BLACK, 0.75f), Dim2D.build().pos(1, 12).flush()));
			this.content().set("username", new TextElement(GuiFactory.text(this.group.getPermissions().owner().getProfile().getUsername(), new Color(0x8FE639), 0.75f), Dim2D.build().pos(10, 2).flush()));
		}

		@Override
		public void onConfirmed()
		{
			super.onConfirmed();
			GroupCore.locate().getDefaultPool().addMember(Minecraft.getMinecraft().thePlayer, this.group);
		}
	}
}
