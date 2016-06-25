package com.gildedgames.util.modules.group.client;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.UUID;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.core.client.ClientProxy;
import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.core.gui.util.decorators.MinecraftGui;
import com.gildedgames.util.core.gui.util.wrappers.MinecraftButton;
import com.gildedgames.util.modules.group.GroupModule;
import com.gildedgames.util.modules.group.common.core.Group;
import com.gildedgames.util.modules.group.common.permissions.IGroupPerms;
import com.gildedgames.util.modules.group.common.player.GroupMember;
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
import com.gildedgames.util.modules.ui.util.SkinButton;
import com.gildedgames.util.modules.ui.util.TextElement;
import com.gildedgames.util.modules.ui.util.decorators.ScrollableGui;
import com.gildedgames.util.modules.ui.util.factory.ContentFactory;
import com.gildedgames.util.modules.ui.util.input.RadioButtonSet;
import com.gildedgames.util.modules.ui.util.transform.GuiPositioner;
import com.gildedgames.util.modules.ui.util.transform.GuiPositionerList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class GuiEditGroup extends GuiFrame
{
	private Group group;

	private GroupMember groupMember;

	public GuiEditGroup(EntityPlayer player)
	{
		this.groupMember = GroupMember.get(player);
		this.group = this.groupMember.groupsInFor(GroupModule.locate().getDefaultPool()).get(0);
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		GuiPositioner positioner = new GuiPositionerList(0);

		this.content().set("name", new TextElement(GuiFactory.text(this.group.getName(), Color.white, 1.3f), Dim2D.build().pos(70, 100).flush()));
		final RadioButtonSet<PlayerButton> players = new RadioButtonSet<>(Pos2D.flush(), 100, positioner, new PlayersContent(this.group));

		ScrollableGui scrollPlayers = new ScrollableGui(Dim2D.build().pos(InputHelper.getCenter(input)).center(true).area(200, 200).flush(), players);

		this.content().set("players", scrollPlayers);

		IGroupPerms permissions = this.group.getPermissions();

		if (permissions.canRemoveMember(this.group, null, this.groupMember))
		{
			this.content().set("removeMember", new MinecraftButton(Dim2D.build().pos(130, 200).area(75, 20).flush(), UtilModule.translate("gui.removemember"))
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
						GroupModule.locate().getDefaultPool().removeMember(selected, g);

						UiModule.locate().open("", new MinecraftGui(new GuiPolling()
						{
							@Override
							protected boolean condition()
							{
								return !g.hasMemberData() || !g.getMemberData().contains(selected) || GroupModule.locate().getDefaultPool().get(g.getName()) == null;
							}

							@Override
							protected void onCondition()
							{
								ClientProxy.GROUP_TAB.onOpen(removing.getPlayer());
							}
						}));
					}
				}
			});
		}

		if (permissions.canRemoveGroup(this.group, this.groupMember))
		{
			this.content().set("disband", new MinecraftButton(Dim2D.build().pos(210, 200).area(75, 20).flush(), UtilModule.translate("gui.disband"))
			{
				@Override
				public void onMouseInput(MouseInputPool pool, InputProvider input)
				{
					super.onMouseInput(pool, input);
					if (input.isHovered(this) && pool.has(MouseButton.LEFT) && pool.has(ButtonState.PRESS))
					{
						GroupModule.locate().getDefaultPool().remove(GuiEditGroup.this.group);

						UiModule.locate().open("", new MinecraftGui(new GuiPolling()
						{
							@Override
							protected boolean condition()
							{
								return GuiEditGroup.this.group.getParentPool().get(GuiEditGroup.this.group.getName()) == null;
							}

							@Override
							protected void onCondition()
							{
								UiModule.locate().open("", new MinecraftGui(new GuiGroups()));
							}
						}));
					}
				}
			});
		}

		this.content().set("leave", new MinecraftButton(Dim2D.build().pos(290, 200).area(75, 20).flush(), UtilModule.translate("gui.leave"))
		{
			@Override
			public void onMouseInput(MouseInputPool pool, InputProvider input)
			{
				super.onMouseInput(pool, input);
				if (input.isHovered(this) && pool.has(MouseButton.LEFT) && pool.has(ButtonState.PRESS))
				{
					GroupModule.locate().getDefaultPool().removeMember(GuiEditGroup.this.groupMember.getPlayer().getUniqueID(), GuiEditGroup.this.group);

					UiModule.locate().open("", new MinecraftGui(new GuiPolling()
					{
						@Override
						protected boolean condition()
						{
							return !GuiEditGroup.this.group.hasMemberData() || !GuiEditGroup.this.group.getMemberData().contains(Minecraft.getMinecraft().thePlayer.getGameProfile().getId());
						}

						@Override
						protected void onCondition()
						{
							UiModule.locate().open("", new MinecraftGui(new GuiGroups()));
						}
					}));
				}
			}
		});

		if (permissions.canInvite(this.group, null, this.groupMember))
		{
			this.content().set("invite", new MinecraftButton(Dim2D.build().pos(310, 100).area(75, 20).flush(), UtilModule.translate("gui.invite"))
			{
				@Override
				public void onMouseInput(MouseInputPool pool, InputProvider input)
				{
					super.onMouseInput(pool, input);
					if (input.isHovered(this) && pool.has(MouseButton.LEFT) && pool.has(ButtonState.PRESS))
					{
						UiModule.locate().open("", new MinecraftGui(new GuiInvite(GuiEditGroup.this.groupMember, GuiEditGroup.this.group)));
					}
				}
			});
		}

		if (permissions.canEditGroupInfo(this.group, this.groupMember.getPlayer().getUniqueID()))
		{
			this.content().set("edit", new MinecraftButton(Dim2D.build().pos(310, 130).area(75, 20).flush(), UtilModule.translate("gui.edit"))
			{
				@Override
				public void onMouseInput(MouseInputPool pool, InputProvider input)
				{
					super.onMouseInput(pool, input);
					if (input.isHovered(this) && pool.has(MouseButton.LEFT) && pool.has(ButtonState.PRESS))
					{
						UiModule.locate().open("", new MinecraftGui(new GuiEditInfo(GuiEditGroup.this.group)));
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
			LinkedHashMap<String, PlayerButton> buttons = new LinkedHashMap<>();
			for (GroupMember member : this.group.getMemberData().onlineMembers())
			{
				buttons.put(member.getPlayer().getName(), new PlayerButton(member.getPlayer().getUniqueID(), member.getPlayer().getName()));
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

			this.content().set("head", new SkinButton(Minecraft.getMinecraft().thePlayer.connection.getPlayerInfo(this.uuid).getGameProfile(), 2, 2));
			this.content().set("username", new TextElement(GuiFactory.text(this.username, new Color(0xE5E5E5), 0.75f), Dim2D.build().pos(19, 12).flush()));
		}
	}
}
