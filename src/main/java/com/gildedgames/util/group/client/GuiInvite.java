package com.gildedgames.util.group.client;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.UUID;

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
import com.mojang.authlib.GameProfile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;

public class GuiInvite extends GuiFrame
{
	private Group group;

	private GroupMember groupMember;

	public GuiInvite(GroupMember member, Group group)
	{
		this.groupMember = member;
		this.group = group;
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		GuiPositioner positioner = new GuiPositionerList(0);

		this.content().set("name", new TextElement(GuiFactory.text(this.group.getName(), Color.white, 1.3f), Dim2D.build().pos(70, 100).flush()));
		final RadioButtonSet<PlayerButton> players = new RadioButtonSet<PlayerButton>(Pos2D.flush(), 100, positioner, new PlayersContent(this.group, this.groupMember.getProfile().getEntity()));

		ScrollableGui scrollPlayers = new ScrollableGui(Dim2D.build().pos(InputHelper.getCenter(input)).center(true).area(200, 200).flush(), players);

		this.content().set("players", scrollPlayers);

		this.content().set("invite", new MinecraftButton(Dim2D.build().pos(130, 200).area(75, 20).flush(), UtilCore.translate("gui.invite"))
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
					final Group g = GuiInvite.this.group;
					final GroupMember inviting = GuiInvite.this.groupMember;
					GroupCore.locate().getDefaultPool().invite(selected, inviting.getProfile().getUUID(), g);

					UiCore.locate().open("", new MinecraftGui(new GuiPolling()
					{
						@Override
						protected boolean condition()
						{
							return !g.hasMemberData() || g.getMemberData().isInvited(selected) || GroupCore.locate().getDefaultPool().get(g.getName()) == null;
						}

						@Override
						protected void onCondition()
						{
							UiCore.locate().open("", new MinecraftGui(new GuiInvite(GuiInvite.this.groupMember, GuiInvite.this.group)));
						}
					}));
				}
			}
		});

		this.content().set("back", new MinecraftButton(Dim2D.build().pos(310, 100).area(75, 20).flush(), UtilCore.translate("gui.back"))
		{

			@Override
			public void onMouseInput(MouseInputPool pool, InputProvider input)
			{
				super.onMouseInput(pool, input);
				if (input.isHovered(this) && pool.has(MouseButton.LEFT) && pool.has(ButtonState.PRESS))
				{
					UiCore.locate().open("", new MinecraftGui(new GuiEditGroup(GuiInvite.this.groupMember.getProfile().getEntity())));
				}
			}
		});
	}

	private static class PlayersContent implements ContentFactory<PlayerButton>
	{
		private Group group;

		private EntityPlayer player;

		public PlayersContent(Group group, EntityPlayer player)
		{
			this.group = group;
			this.player = player;
		}

		@Override
		public LinkedHashMap<String, PlayerButton> provideContent(ImmutableMap<String, Ui> currentContent, Rect contentArea)
		{
			LinkedHashMap<String, PlayerButton> buttons = new LinkedHashMap<String, PlayerButton>();

			for (NetworkPlayerInfo info : Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfoMap())
			{
				GameProfile profile = info.getGameProfile();

				if (!profile.getId().equals(this.player.getGameProfile().getId()) && !this.group.getMemberData().contains(profile.getId()))
				{
					buttons.put(profile.getName(), new PlayerButton(profile.getId(), profile.getName(), this.group.getMemberData().isInvited(profile.getId())));
				}
			}

			return buttons;
		}
	}

	private static class PlayerButton extends RadioButtonDefault
	{
		private UUID uuid;

		private String username;

		private boolean invited;

		public PlayerButton(UUID uuid, String username, boolean invited)
		{
			super(175, 20);
			this.uuid = uuid;
			this.username = username;
			this.invited = invited;
		}

		@Override
		public void initContent(InputProvider input)
		{
			super.initContent(input);

			this.content().set("head", new SkinButton(Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfo(this.uuid).getGameProfile(), 2, 2));
			this.content().set("username", new TextElement(GuiFactory.text(this.username, new Color(0xE5E5E5), 0.75f), Dim2D.build().pos(19, 12).flush()));
			if (this.invited)
			{
				this.content().set("invited", new TextElement(GuiFactory.text(UtilCore.translate("gui.alreadyinvited"), new Color(0xE5E5E5), 0.75f), Dim2D.build().pos(45, 12).flush()));
			}
		}
	}
}
