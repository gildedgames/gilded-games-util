package com.gildedgames.util.tab.client.social;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.core.gui.util.decorators.MinecraftGui;
import com.gildedgames.util.notifications.client.gui.GuiNotification;
import com.gildedgames.util.notifications.client.gui.GuiNotifications;
import com.gildedgames.util.tab.common.util.TabGeneric;
import com.gildedgames.util.ui.UiModule;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TabNotifications extends TabGeneric
{

	private static final ResourceLocation TEXTURE_NOTIFICATIONS = new ResourceLocation(UtilModule.MOD_ID, "textures/gui/tab_icons/notifications.png");

	@Override
	public String getUnlocalizedName()
	{
		return "tab.chat.name";
	}

	@Override
	public boolean isTabValid(GuiScreen gui)
	{
		return UiModule.locate().containsFrame(gui, GuiNotifications.class, GuiNotification.class);
	}

	@Override
	public void onOpen(EntityPlayer player)
	{
		UiModule.locate().open("", new MinecraftGui(new GuiNotifications(player)));
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

	@Override
	public ResourceLocation getIconTexture()
	{
		return TEXTURE_NOTIFICATIONS;
	}

}
