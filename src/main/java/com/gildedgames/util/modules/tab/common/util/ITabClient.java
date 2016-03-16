package com.gildedgames.util.modules.tab.common.util;

import com.gildedgames.util.modules.tab.common.TabApiImpl;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ITabClient extends ITab
{
	/**
	 * List all {@link GuiScreen} classes which are used within this tab. If you use a class which you have not listed here,
	 * the {@link TabApiImpl} will not be able to recognize its association with this particular tab, meaning it
	 * won't display when the {@link GuiScreen} is open.
	 * @return All of the {@link GuiScreen} classes which are used within this tab.
	 */
	@SideOnly(Side.CLIENT)
	boolean isTabValid(GuiScreen gui);

	/**
	 * @return Returns the icon this tab has.
	 */
	@SideOnly(Side.CLIENT)
	ResourceLocation getIcon();
}
