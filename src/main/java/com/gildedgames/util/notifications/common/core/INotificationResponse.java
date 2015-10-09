package com.gildedgames.util.notifications.common.core;

import com.gildedgames.util.core.nbt.NBT;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface INotificationResponse extends NBT
{
	String getName();

	/**
	 * Executes events associated to this response.
	 * These are executed server sided.
	 * @return True if the notification should be
	 * deleted when clicked on this button
	 */
	boolean onClicked();

	@SideOnly(Side.CLIENT)
	GuiScreen screenAfterClicked(GuiScreen currentScreen);
}
