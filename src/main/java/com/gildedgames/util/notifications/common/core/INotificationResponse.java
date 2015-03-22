package com.gildedgames.util.notifications.common.core;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.util.core.nbt.NBT;

public interface INotificationResponse extends NBT
{
	String getName();

	/**
	 * Program here what should happen when you
	 * click on this response.
	 * @return True if the notification should be
	 * deleted when clicked on this button
	 */
	boolean onClicked();

	@SideOnly(Side.CLIENT)
	GuiScreen screenAfterClicked(GuiScreen currentScreen);
}
