package com.gildedgames.util.tab.client.social;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.tab.common.util.TabGeneric;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TabChat extends TabGeneric
{

	private static final ResourceLocation TEXTURE_CHAT = new ResourceLocation(UtilCore.MOD_ID, "textures/gui/tab_icons/chat.png");

	@Override
	public String getUnlocalizedName()
	{
		return "tab.chat.name";
	}

	@Override
	public boolean isTabValid(GuiScreen gui)
	{
		return gui instanceof GuiChat;
	}

	@Override
	public void onOpen(EntityPlayer player)
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiChat(""));
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
		return TEXTURE_CHAT;
	}

}
