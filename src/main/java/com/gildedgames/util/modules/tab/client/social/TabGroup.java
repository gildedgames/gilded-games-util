package com.gildedgames.util.modules.tab.client.social;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.core.gui.util.decorators.MinecraftGui;
import com.gildedgames.util.modules.group.GroupModule;
import com.gildedgames.util.modules.group.client.GuiCreateGroup;
import com.gildedgames.util.modules.group.client.GuiEditGroup;
import com.gildedgames.util.modules.group.client.GuiEditInfo;
import com.gildedgames.util.modules.group.client.GuiGroups;
import com.gildedgames.util.modules.group.client.GuiInvite;
import com.gildedgames.util.modules.group.common.player.GroupMember;
import com.gildedgames.util.modules.tab.common.util.ITab;
import com.gildedgames.util.modules.ui.UiModule;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TabGroup implements ITab
{
	private static final ResourceLocation ICON = new ResourceLocation(UtilModule.MOD_ID, "textures/gui/tab_icons/group.png");

	@Override
	public String getUnlocalizedName()
	{
		return "tab.group.name";
	}

	@Override
	public boolean isTabValid(GuiScreen gui)
	{
		return UiModule.locate().containsFrame(gui, GuiGroups.class, GuiCreateGroup.class, GuiEditGroup.class, GuiInvite.class, GuiEditInfo.class);
	}

	@Override
	public ResourceLocation getIcon()
	{
		return TabGroup.ICON;
	}

	@Override
	public void onOpen(EntityPlayer player)
	{
		GroupMember member = GroupMember.get(player);
		if (member.groupsInFor(GroupModule.locate().getDefaultPool()).isEmpty())
		{
			UiModule.locate().open("", new MinecraftGui(new GuiGroups()));
		}
		else
		{
			UiModule.locate().open("", new MinecraftGui(new GuiEditGroup(player)));
		}
	}

	@Override
	public void onClose(EntityPlayer player)
	{

	}

	@Override
	public Container getCurrentContainer(EntityPlayer player, World world, int posX, int posY, int posZ)
	{
		return null;
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}

	@Override
	public boolean isRemembered()
	{
		return true;
	}
}
