package com.gildedgames.util.group.client;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.UUID;

import com.gildedgames.util.core.ClientProxy;
import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.core.gui.util.decorators.MinecraftGui;
import com.gildedgames.util.core.gui.util.wrappers.MinecraftButton;
import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.group.common.core.Group;
import com.gildedgames.util.group.common.permissions.IGroupPerms;
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
import com.gildedgames.util.ui.util.GuiPolling;
import com.gildedgames.util.ui.util.InputHelper;
import com.gildedgames.util.ui.util.SkinButton;
import com.gildedgames.util.ui.util.TextElement;
import com.gildedgames.util.ui.util.decorators.ScrollableGui;
import com.gildedgames.util.ui.util.factory.ContentFactory;
import com.gildedgames.util.ui.util.input.RadioButtonSet;
import com.gildedgames.util.ui.util.transform.GuiPositioner;
import com.gildedgames.util.ui.util.transform.GuiPositionerList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class GuiEditGroup extends GuiFrame
{
	private Group group;

	private GroupMember groupMember;

	public GuiEditGroup(EntityPlayer player)
	{
		this.groupMember = GroupCore.locate().getPlayers().get(player);
		this.group = this.groupMember.groupsInFor(GroupCore.locate().getDefaultPool()).get(0);
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		GuiPositioner positioner = new GuiPositionerList(0);

		this.content().set("name", new TextElement(GuiFactory.text(this.group.getName(), Color.white, 1.3f), Dim2D.build().pos(70, 100).flush()));
		final RadioButtonSet<PlayerButton> players = new RadioButtonSet<PlayerButton>(Pos2D.flush(), 100, positioner, new PlayersContent(this.group));

		ScrollableGui scrollPlayers = new ScrollableGui(Dim2D.build().pos(InputHelper.getCenter(input)).center(true).area(200, 200).flush(), players);

		this.content().set("players", scrollPlayers);

		IGroupPerms permissions = this.group.getPermissions();

		if (permissions.canRemoveMember(this.group, null, this.groupMember))
		{
			this.content().set("removeMember", new MinecraftButton(Dim2D.build().pos(130, 200).area(75, 20).flush(), UtilCore.translate("gui.removemember"))
			{
				@Override
				public void onMouseInput(MouseInputPool pool, InputProvider input)
				{
					super.onMouseInput(pool, input);
					if (input.isHovered(this) && pool.has(MouseButton.LEFT) && pool.has(ButtonState.PRESS))
					{
						if (players.getSelected() == null)
						{
							return;
						}
						final UUID selected = players.getSelected().uuid;
						final Group g = GuiEditGroup.this.group;
						final GroupMember removing = GuiEditGroup.this.groupMember;
						GroupCore.locate().getDefaultPool().removeMember(selected, g);

						UiCore.locate().open("", new MinecraftGui(new GuiPolling()
						{
							@Override
							protected boolean condition()
							{
								return !g.hasMemberData() || !g.getMemberData().contains(selected) || GroupCore.locate().getDefaultPool().get(g.getName()) == null;
							}

							@Override
							protected void onCondition()
							{
								ClientProxy.GROUP_TAB.onOpen(removing.getProfile().getEntity());
							}
						}));
					}
				}
			});
		}

		if (permissions.canRemoveGroup(this.group, this.groupMember))
		{
			this.content().set("disband", new MinecraftButton(Dim2D.build().pos(210, 200).area(75, 20).flush(), UtilCore.translate("gui.disband"))
			{
				@Override
				public void onMouseInput(MouseInputPool pool, InputProvider input)
				{
					super.onMouseInput(pool, input);
					if (input.isHovered(this) && pool.has(MouseButton.LEFT) && pool.has(ButtonState.PRESS))
					{
						GroupCore.locate().getDefaultPool().remove(GuiEditGroup.this.group);

						UiCore.locate().open("", new MinecraftGui(new GuiPolling()
						{
							@Override
							protected boolean condition()
							{
								return GuiEditGroup.this.group.getParentPool().get(GuiEditGroup.this.group.getName()) == null;
							}

							@Override
							protected void onCondition()
							{
								UiCore.locate().open("", new MinecraftGui(new GuiGroups(Minecraft.getMinecraft().thePlayer)));
							}
						}));
					}
				}
			});
		}

		this.content().set("leave", new MinecraftButton(Dim2D.build().pos(290, 200).area(75, 20).flush(), UtilCore.translate("gui.leave"))
		{
			@Override
			public void onMouseInput(MouseInputPool pool, InputProvider input)
			{
				super.onMouseInput(pool, input);
				if (input.isHovered(this) && pool.has(MouseButton.LEFT) && pool.has(ButtonState.PRESS))
				{
					GroupCore.locate().getDefaultPool().removeMember(GuiEditGroup.this.groupMember.getProfile().getUUID(), GuiEditGroup.this.group);

					UiCore.locate().open("", new MinecraftGui(new GuiPolling()
					{
						@Override
						protected boolean condition()
						{
							return !GuiEditGroup.this.group.hasMemberData() || !GuiEditGroup.this.group.getMemberData().contains(Minecraft.getMinecraft().thePlayer.getGameProfile().getId());
						}

						@Override
						protected void onCondition()
						{
							UiCore.locate().open("", new MinecraftGui(new GuiGroups(Minecraft.getMinecraft().thePlayer)));
						}
					}));
				}
			}
		});

		if (permissions.canInvite(this.group, null, this.groupMember))
		{
			this.content().set("invite", new MinecraftButton(Dim2D.build().pos(310, 100).area(75, 20).flush(), UtilCore.translate("gui.invite"))
			{
				@Override
				public void onMouseInput(MouseInputPool pool, InputProvider input)
				{
					super.onMouseInput(pool, input);
					if (input.isHovered(this) && pool.has(MouseButton.LEFT) && pool.has(ButtonState.PRESS))
					{
						UiCore.locate().open("", new MinecraftGui(new GuiInvite(GuiEditGroup.this.groupMember, GuiEditGroup.this.group)));
					}
				}
			});
		}

		if (permissions.canEditGroupInfo(this.group, this.groupMember.getProfile().getUUID()))
		{
			this.content().set("edit", new MinecraftButton(Dim2D.build().pos(310, 130).area(75, 20).flush(), UtilCore.translate("gui.edit"))
			{
				@Override
				public void onMouseInput(MouseInputPool pool, InputProvider input)
				{
					super.onMouseInput(pool, input);
					if (input.isHovered(this) && pool.has(MouseButton.LEFT) && pool.has(ButtonState.PRESS))
					{
						UiCore.locate().open("", new MinecraftGui(new GuiEditInfo(GuiEditGroup.this.group)));
					}
				}
			});
		}
	}

	private static class PlayersContent implements ContentFactory<PlayerButton>
	{
		private Group group;

		public PlayersContent(Group group)
		{
			this.group = group;
		}

		@Override
		public LinkedHashMap<String, PlayerButton> provideContent(ImmutableMap<String, Ui> currentContent, Rect contentArea)
		{
			LinkedHashMap<String, PlayerButton> buttons = new LinkedHashMap<String, PlayerButton>();
			for (GroupMember member : this.group.getMemberData().onlineMembers())
			{
				buttons.put(member.getProfile().getUsername(), new PlayerButton(member.getProfile().getUUID(), member.getProfile().getUsername()));
			}

			return buttons;
		}
	}

	private static class PlayerButton extends RadioButtonDefault
	{
		private UUID uuid;

		private String username;

		public PlayerButton(UUID uuid, String username)
		{
			super(175, 20);
			this.uuid = uuid;
			this.username = username;
		}

		@Override
		public void initContent(InputProvider input)
		{
			super.initContent(input);

			this.content().set("head", new SkinButton(Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfo(this.uuid).getGameProfile(), 2, 2));
			this.content().set("username", new TextElement(GuiFactory.text(this.username, new Color(0xE5E5E5), 0.75f), Dim2D.build().pos(19, 12).flush()));
		}
	}
}
