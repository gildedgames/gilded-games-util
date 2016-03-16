package com.gildedgames.util.modules.tab.client.social;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.modules.tab.common.util.ITab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TabChat implements ITab
{
	@SideOnly(Side.CLIENT)
	private static final ResourceLocation ICON = new ResourceLocation(UtilModule.MOD_ID, "textures/gui/tab_icons/chat.png");

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
	public ResourceLocation getIcon()
	{
		return TabChat.ICON;
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
}
