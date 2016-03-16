package com.gildedgames.util.modules.tab.client.inventory;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.modules.tab.common.util.ITab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * The {@link ITab} representation of the Minecraft's vanilla Inventory {@link GuiScreen}
 * @author Brandon Pearce
 */
public class TabBackpack implements ITab
{
	@SideOnly(Side.CLIENT)
	private static final ResourceLocation ICON = new ResourceLocation(UtilModule.MOD_ID, "textures/gui/tab_icons/backpack.png");

	@Override
	public String getUnlocalizedName()
	{
		return "tab.backpack.name";
	}

	@Override
	public boolean isTabValid(GuiScreen gui)
	{
		Class<? extends GuiScreen> clazz = gui.getClass();
		return clazz == GuiInventory.class || clazz == GuiContainerCreative.class;
	}

	@Override
	public ResourceLocation getIcon()
	{
		return TabBackpack.ICON;
	}

	@Override
	public void onOpen(EntityPlayer player)
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiInventory(player));
	}

	@Override
	public void onClose(EntityPlayer player)
	{
		EntityPlayerSP playerSP = (EntityPlayerSP) player;

		playerSP.sendQueue.addToSendQueue(new C0DPacketCloseWindow(playerSP.openContainer.windowId));
		playerSP.inventory.setItemStack(null);
		playerSP.openContainer = playerSP.inventoryContainer;
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
