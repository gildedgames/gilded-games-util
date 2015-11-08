package com.gildedgames.util.group.client;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.gui.util.decorators.MinecraftGui;
import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.group.common.core.Group;
import com.gildedgames.util.group.common.core.GroupInfo;
import com.gildedgames.util.group.common.permissions.IGroupPerms;
import com.gildedgames.util.ui.UiCore;
import com.gildedgames.util.ui.util.GuiPolling;

import net.minecraft.client.Minecraft;

public class GuiEditInfo extends GuiCreateGroup
{
	private Group group;

	public GuiEditInfo(Group group)
	{
		this.group = group;
	}

	@Override
	protected String defaultName()
	{
		return this.group.getName();
	}

	@Override
	protected String createButtonName()
	{
		return UtilCore.translate("gui.finish");
	}

	@Override
	protected void onCreated(final String name, final IGroupPerms perms)
	{
		GroupInfo info = new GroupInfo(this.group.getUUID(), name, perms);
		GroupCore.locate().getDefaultPool().changeGroupInfo(UtilCore.proxy.getPlayer().getGameProfile().getId(), this.group, info);
		UiCore.locate().open("", new MinecraftGui(new GuiPolling()
		{
			@Override
			protected boolean condition()
			{
				return GuiEditInfo.this.group.getName().equals(name) && GuiEditInfo.this.group.getPermissions().equals(perms);
			}

			@Override
			protected void onCondition()
			{
				UiCore.locate().open("", new MinecraftGui(new GuiEditGroup(Minecraft.getMinecraft().thePlayer)));
			}
		}));
	}
}
