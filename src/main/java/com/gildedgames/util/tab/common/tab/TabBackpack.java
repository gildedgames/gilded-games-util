package com.gildedgames.util.tab.common.tab;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.gildedgames.util.core.Sprite;
import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.tab.common.util.ITab;

/**
 * The {@link ITab} representation of the Minecraft's vanilla Inventory {@link GuiScreen}
 * @author Brandon Pearce
 */
public class TabBackpack implements ITab
{

	private static final ResourceLocation TEXTURE = new ResourceLocation(UtilCore.MOD_ID, "textures/gui/tab_icons/backpack.png");

	private static final Sprite sprite = new Sprite("backpack.png", 16, 16);
	
	public TabBackpack()
	{
		sprite.initSprite(16, 16, 0, 0, false);
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return "tab.backpack.name";
	}

	@Override
	public List getGuiClasses()
	{
		return Arrays.asList(GuiInventory.class, GuiContainerCreative.class);
	}

	@Override
	public void renderIcon(int x, int y)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
		Gui.func_146110_a(x, y, 0, 0, sprite.getIconWidth(), sprite.getIconWidth(), sprite.getIconWidth(), sprite.getIconWidth());
	}

	@Override
	public void onOpen(EntityPlayer player)
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiInventory(player));
	}

	@Override
	public void onClose(EntityPlayer player)
	{
		EntityClientPlayerMP playerSP = (EntityClientPlayerMP) player;

		playerSP.sendQueue.addToSendQueue(new C0DPacketCloseWindow(playerSP.openContainer.windowId));
		playerSP.inventory.setItemStack((ItemStack) null);
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