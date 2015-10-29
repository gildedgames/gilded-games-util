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
import com.gildedgames.util.ui.util.SkinButton;
import com.gildedgames.util.ui.util.TextElement;
import com.gildedgames.util.ui.util.decorators.ScrollableGui;
import com.gildedgames.util.ui.util.factory.ContentFactory;
import com.gildedgames.util.ui.util.input.RadioButton;
import com.gildedgames.util.ui.util.input.RadioButtonSet;
import com.gildedgames.util.ui.util.transform.GuiPositioner;
import com.gildedgames.util.ui.util.transform.GuiPositionerList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.entity.player.EntityPlayer;

public class GuiEditGroup extends GuiFrame
{
	private Group group;

	public GuiEditGroup(EntityPlayer player)
	{
		this.group = GroupCore.locate().getPlayers().get(player).groupsInFor(GroupCore.locate().getDefaultPool()).get(0);
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		GuiPositioner positioner = new GuiPositionerList(0);

		//buttons.put("title", new TextElement(GuiFactory.text(UtilCore.translate("gui.grouplist"), Color.white, 2.3f), Pos2D.flush(), false));
		final RadioButtonSet groups = new RadioButtonSet(Pos2D.flush(), 100, positioner, new PlayersContent(this.group));

		ScrollableGui scrollGroups = new ScrollableGui(Dim2D.build().pos(ScreenDimUtil.getCenter(input)).center(true).area(200, 200).flush(), groups);

		this.content().set("groups", scrollGroups);

		this.content().set("join", new MinecraftButton(Dim2D.build().pos(130, 200).area(75, 20).flush(), UtilCore.translate("gui.join"))
		{
			@Override
			public void onMouseInput(MouseInputPool pool, InputProvider input)
			{
				super.onMouseInput(pool, input);
				if (input.isHovered(this) && pool.has(MouseButton.LEFT) && pool.has(ButtonState.PRESS))
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
				if (input.isHovered(this) && pool.has(MouseButton.LEFT) && pool.has(ButtonState.PRESS))
				{
					UiCore.locate().open("", new MinecraftGui(new GuiCreateGroup()));
				}
			}
		});
	}

	private static class PlayersContent implements ContentFactory<RadioButton>
	{
		private Group group;

		public PlayersContent(Group group)
		{
			this.group = group;
		}

		@Override
		public LinkedHashMap<String, RadioButton> provideContent(ImmutableMap<String, Ui> currentContent, Rect contentArea)
		{
			LinkedHashMap<String, RadioButton> buttons = new LinkedHashMap<String, RadioButton>();
			for (GroupMember member : this.group.getMemberData().getMembers())
			{
				buttons.put(member.getProfile().getUsername(), new PlayerButton(member.getProfile().getEntity()));
			}

			return buttons;
		}
	}

	private static class PlayerButton extends RadioButtonDefault
	{
		private EntityPlayer player;

		public PlayerButton(EntityPlayer player)
		{
			super(175, 20);
			this.player = player;
		}

		@Override
		public void initContent(InputProvider input)
		{
			super.initContent(input);
			this.content().set("head", new SkinButton(this.player, 2, 2));
			this.content().set("username", new TextElement(GuiFactory.text(this.player.getCommandSenderName(), new Color(0xE5E5E5), 0.75f), Dim2D.build().pos(19, 12).flush()));
		}

		@Override
		public void onConfirmed()
		{
			// TODO Auto-generated method stub
			super.onConfirmed();
		}
	}
}
