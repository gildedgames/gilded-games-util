package com.gildedgames.util.tab.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.gildedgames.util.tab.common.util.ITab;
import com.gildedgames.util.tab.common.util.ITabGroup;
import com.gildedgames.util.tab.common.util.TabGroupHandler;

public class RenderTabGroup extends Gui
{
	
	private static final ResourceLocation TEXTURE_TAB_ITEMS = new ResourceLocation("textures/gui/container/creative_inventory/tab_items.png");

	private static final ResourceLocation TEXTURE_TABS = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
	
	@SideOnly(Side.CLIENT)
	protected void drawHoveringText(String text, int par2, int par3, FontRenderer font)
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		int k = font.getStringWidth(text);

		int i1 = par2 + 12;
		int j1 = par3 - 12;
		int k1 = 8;

		this.zLevel = 300.0F;
		int l1 = -267386864;
		this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
		this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
		this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
		this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
		this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
		int i2 = 1347420415;
		int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
		this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
		this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
		this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
		this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);

		font.drawString(text, i1, j1, -1);

		this.zLevel = 0.0F;

		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	/**
	 * Renders the {@link TabGroupHandler} and all of its containing {@link ITab ITab}s
	 */
	public void render(ITabGroup tabGroup)
	{
		if (tabGroup.getEnabledTabs().size() <= 1)
		{
			return;
		}
		
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

		int xPosition = (scaledresolution.getScaledWidth() - 28 * tabGroup.getEnabledTabs().size()) / 2;
		int yPosition = 13;

		int topY = -7;
		int width = 28 * tabGroup.getEnabledTabs().size() + 30;
		int centerX = scaledresolution.getScaledWidth() / 2;

		mc.getTextureManager().bindTexture(TEXTURE_TAB_ITEMS);

		GL11.glColor4f(1.f, 1.f, 1.f, 1.f);
		GL11.glDisable(GL11.GL_LIGHTING);

		this.drawTexturedModalRect(centerX - width / 2, topY, 0, 0, width / 2, 15);
		this.drawTexturedModalRect(centerX - width / 2, topY + 15, 0, 130, width / 2, 5);
		this.drawTexturedModalRect(centerX, topY, 194 - width / 2, 0, width / 2, 15);
		this.drawTexturedModalRect(centerX, topY + 15, 194 - width / 2, 130, width / 2, 5);

		this.drawCenteredString(mc.fontRendererObj, StatCollector.translateToLocal(tabGroup.getSelectedTab().getUnlocalizedName()), centerX, topY + 8, 0xFFFFFFFF);

		for (ITab tab : tabGroup.getEnabledTabs())
		{
			if (tab != null && tab.isEnabled())
			{
				mc.getTextureManager().bindTexture(TEXTURE_TABS);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

				int u = 28;

				if (tab == tabGroup.getSelectedTab())
				{
					int v = 3 * 32;
					this.drawTexturedModalRect(xPosition, yPosition - 3, u, v, 28, 14);
					this.drawTexturedModalRect(xPosition, yPosition + 10, u, v + 22, 28, 10);
				}
				else
				{
					int v = 2 * 32;
					this.drawTexturedModalRect(xPosition, yPosition, u, v, 28, 11);
					this.drawTexturedModalRect(xPosition, yPosition + 10, u, v + 20, 28, 12);
				}

				tab.renderIcon(xPosition + 6, yPosition - 1);

				xPosition += 28;
			}
		}

		ITab hoveredTab = this.getHoveredTab(tabGroup);

		if (hoveredTab != null)
		{
			this.drawHoveringText(StatCollector.translateToLocal(hoveredTab.getUnlocalizedName()), Mouse.getX() * scaledresolution.getScaledWidth() / mc.displayWidth, scaledresolution.getScaledHeight() - Mouse.getY() * scaledresolution.getScaledHeight() / mc.displayHeight - 1, mc.fontRendererObj);
		}
	}
	
	/**
	 * @return The current {@link ITab} that is hovered over by the player's mouse cursor
	 */
	public ITab getHoveredTab(ITabGroup tabGroup)
	{
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

		int x = Mouse.getX() * scaledresolution.getScaledWidth() / mc.displayWidth;
		int y = scaledresolution.getScaledHeight() - Mouse.getY() * scaledresolution.getScaledHeight() / mc.displayHeight - 1;

		if (y >= 13 && y <= 32)
		{
			int xPosition = (scaledresolution.getScaledWidth() - 28 * tabGroup.getEnabledTabs().size()) / 2;

			x -= xPosition;

			if (x > 0 && x < 28 * tabGroup.getEnabledTabs().size())
			{
				return tabGroup.getEnabledTabs().get(x / 28);
			}
		}

		return null;
	}

}
