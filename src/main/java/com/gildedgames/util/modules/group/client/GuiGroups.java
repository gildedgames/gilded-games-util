package com.gildedgames.util.modules.group.client;

import java.awt.Color;
import java.util.LinkedHashMap;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.core.client.ClientProxy;
import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.core.gui.util.decorators.MinecraftGui;
import com.gildedgames.util.core.gui.util.wrappers.MinecraftButton;
import com.gildedgames.util.modules.group.GroupModule;
import com.gildedgames.util.modules.group.common.core.Group;
import com.gildedgames.util.modules.group.common.permissions.GroupPermsDefault;
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
import com.gildedgames.util.modules.ui.util.GuiPolling;
import com.gildedgames.util.modules.ui.util.InputHelper;
import com.gildedgames.util.modules.ui.util.TextElement;
import com.gildedgames.util.modules.ui.util.decorators.ScrollableGui;
import com.gildedgames.util.modules.ui.util.factory.ContentFactory;
import com.gildedgames.util.modules.ui.util.input.RadioButtonSet;
import com.gildedgames.util.modules.ui.util.transform.GuiPositioner;
import com.gildedgames.util.modules.ui.util.transform.GuiPositionerList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class GuiGroups extends GuiFrame
{
	public GuiGroups()
	{
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		GuiPositioner positioner = new GuiPositionerList(0);

		//buttons.put("title", new TextElement(GuiFactory.text(UtilCore.translate("gui.grouplist"), Color.white, 2.3f), Pos2D.flush(), false));
		final RadioButtonSet<GroupButton> groups = new RadioButtonSet<>(Pos2D.flush(), 100, positioner, new GroupsButtonContent());

		ScrollableGui scrollGroups = new ScrollableGui(Dim2D.build().pos(InputHelper.getCenter(input)).center(true).area(200, 200).flush(), groups);

		this.content().set("groups", scrollGroups);

		this.content().set("join", new MinecraftButton(Dim2D.build().pos(130, 200).area(75, 20).flush(), UtilModule.translate("gui.join"))
		{
			@Override
			public void onMouseInput(MouseInputPool pool, InputProvider input)
			{
				super.onMouseInput(pool, input);
				if (input.isHovered(this) && pool.has(MouseButton.LEFT) && pool.has(ButtonState.PRESS))
				{
					final Group group = groups.getSelected().group;
					final EntityPlayer player = Minecraft.getMinecraft().thePlayer;
					GroupModule.locate().getDefaultPool().addMember(player.getGameProfile().getId(), group);

					UiModule.locate().open("", new MinecraftGui(new GuiPolling()
					{
						@Override
						protected boolean condition()
						{
							return group.hasMemberData() && group.getMemberData().contains(player.getGameProfile().getId());
						}

						@Override
						protected void onCondition()
						{
							ClientProxy.GROUP_TAB.onOpen(player);
						}
					}));
				}
			}
		});

		this.content().set("create", new MinecraftButton(Dim2D.build().pos(210, 200).area(75, 20).flush(), UtilModule.translate("gui.create"))
		{
			@Override
			public void onMouseInput(MouseInputPool pool, InputProvider input)
			{
				super.onMouseInput(pool, input);
				if (input.isHovered(this) && pool.has(MouseButton.LEFT) && pool.has(ButtonState.PRESS))
				{
					UiModule.locate().open("", new MinecraftGui(new GuiCreateGroup()));
				}
			}
		});
	}

	private static class GroupsButtonContent implements ContentFactory<GroupButton>
	{
		@Override
		public LinkedHashMap<String, GroupButton> provideContent(ImmutableMap<String, Ui> currentContent, Rect contentArea)
		{
			LinkedHashMap<String, GroupButton> buttons = new LinkedHashMap<>();
			for (Group group : GroupModule.locate().getDefaultPool().getGroups())
			{
				if (group.getPermissions().isVisible(group))
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
			this.content().set("perm", new TextElement(GuiFactory.text(this.group.getPermissions().getName(), Color.green, 0.75f), Dim2D.build().pos(1, 12).flush()));

			this.content().set("username", new TextElement(GuiFactory.text(((GroupPermsDefault) this.group.getPermissions()).ownerUsername(), new Color(0xE5E5E5), 0.75f), Dim2D.build().pos(19, 12).flush()));
		}

	}
}
