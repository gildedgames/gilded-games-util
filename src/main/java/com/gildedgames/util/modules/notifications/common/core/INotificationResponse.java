package com.gildedgames.util.modules.notifications.common.core;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface INotificationResponse
{
	String getName();

	/**
	 * Executes events associated to this response.
	 * These are executed only server sided.
	 * @return True if the notification should be
	 * deleted when clicked on this button
	 */
	boolean onClicked(EntityPlayer player);

	@SideOnly(Side.CLIENT)
	GuiScreen openScreenAfterClicked(GuiScreen currentScreen);
}
