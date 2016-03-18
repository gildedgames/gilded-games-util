package com.gildedgames.util.modules.tab.client.social;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.core.gui.util.decorators.MinecraftGui;
import com.gildedgames.util.modules.notifications.client.gui.GuiNotification;
import com.gildedgames.util.modules.notifications.client.gui.GuiNotifications;
import com.gildedgames.util.modules.tab.common.util.ITab;
import com.gildedgames.util.modules.tab.common.util.ITabClient;
import com.gildedgames.util.modules.ui.UiModule;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TabNotifications implements ITab
{
	@Override
	public String getUnlocalizedName()
	{
		return "tab.chat.name";
	}

	@Override
	public void onOpen(EntityPlayer player)
	{
		UiModule.locate().open("", new MinecraftGui(new GuiNotifications(player)));
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

	@SideOnly(Side.CLIENT)
	public static class Client extends TabNotifications implements ITabClient
	{
		private static final ResourceLocation ICON = new ResourceLocation(UtilModule.MOD_ID, "textures/gui/tab_icons/notifications.png");

		@Override
		public boolean isTabValid(GuiScreen gui)
		{
			return UiModule.locate().containsFrame(gui, GuiNotifications.class, GuiNotification.class);
		}

		@Override
		public void onClose(EntityPlayer player)
		{

		}

		@Override
		public ResourceLocation getIcon()
		{
			return TabNotifications.Client.ICON;
		}
	}
}
