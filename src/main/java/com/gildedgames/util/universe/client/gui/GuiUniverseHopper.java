package com.gildedgames.util.universe.client.gui;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Mouse;

import com.gildedgames.util.core.ClientProxy;
import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.universe.UniverseCore;
import com.gildedgames.util.universe.common.UniverseAPI;
import com.gildedgames.util.universe.common.networking.packets.TravelUniversePacket;
import com.gildedgames.util.universe.common.player.PlayerUniverse;
import com.gildedgames.util.universe.common.util.IUniverse;

public class GuiUniverseHopper extends GuiScreen
{

	private static final ResourceLocation BASE_TEXTURE = new ResourceLocation(UtilCore.MOD_ID, "textures/gui/universe_hopper/base.png");

	private static final int BASE_WIDTH = 256, BASE_HEIGHT = 153;

	private static final int PREVIEW_WIDTH = 244, PREVIEW_HEIGHT = 110;

	private int baseX, baseY, universeIndex;

	private static IUniverse SELECTED_UNIVERSE;

	private TravelButton travelButton;

	private final PlayerUniverse usingPlayer = UniverseCore.locate().getPlayers().get(Minecraft.getMinecraft().thePlayer);

	public GuiUniverseHopper()
	{
		super();

		SELECTED_UNIVERSE = this.usingPlayer.getUniverse();
		this.universeIndex = this.getIndexFromUniverse(SELECTED_UNIVERSE);
	}

	public int getIndexFromUniverse(IUniverse universe)
	{
		int index = 0;

		for (IUniverse iuniverse : UniverseAPI.instance().getUniverses())
		{
			if (iuniverse == universe)
			{
				return index;
			}

			index++;
		}

		return 0;
	}

	@Override
	public void initGui()
	{
		this.baseX = this.width / 2 - BASE_WIDTH / 2;
		this.baseY = this.height / 2 - BASE_HEIGHT / 2;

		if (SELECTED_UNIVERSE == null)
		{
			SELECTED_UNIVERSE = this.usingPlayer.getUniverse();
			this.universeIndex = this.getIndexFromUniverse(SELECTED_UNIVERSE);
		}

		this.buttonList.add(new ToggleButton(0, this.width / 2 - 160, this.height / 2 - 10, true));
		this.buttonList.add(new ToggleButton(1, this.width / 2 + 140, this.height / 2 - 10, false));

		this.travelButton = new TravelButton(2, this.width / 2 - 35, this.baseY + BASE_HEIGHT - 18);

		this.buttonList.add(this.travelButton);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		switch (button.id)
		{
			case 0:
			{
				this.universeIndex--;
		
				if (this.universeIndex < 0)
				{
					this.universeIndex = UniverseAPI.instance().getUniverses().size() - 1;
				}
		
				SELECTED_UNIVERSE = UniverseAPI.instance().getUniverses().get(this.universeIndex);
				break;
			}
			case 1:
			{
				this.universeIndex++;
		
				if (this.universeIndex >= UniverseAPI.instance().getUniverses().size())
				{
					this.universeIndex = 0;
				}
		
				SELECTED_UNIVERSE = UniverseAPI.instance().getUniverses().get(this.universeIndex);
				break;
			}
			case 2:
			{
				String id = UniverseAPI.instance().getIDFrom(SELECTED_UNIVERSE);
		
				UtilCore.NETWORK.sendToServer(new TravelUniversePacket(id));
		
				break;
			}
		}
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.travelButton.enabled = SELECTED_UNIVERSE != this.usingPlayer.getUniverse();

		this.drawDefaultBackground();

		this.mc.renderEngine.bindTexture(BASE_TEXTURE);
		this.drawTexturedModalRect(this.baseX, this.baseY, 0, 0, BASE_WIDTH, BASE_HEIGHT);

		this.mc.renderEngine.bindTexture(SELECTED_UNIVERSE.getPreviewTexture());
		this.drawTexturedModalRect(this.baseX + 6, this.baseY + 19, 0, 0, PREVIEW_WIDTH, PREVIEW_HEIGHT);

		final String universeName = StatCollector.translateToLocal(SELECTED_UNIVERSE.getUnlocalizedName());

		this.drawCenteredString(this.mc.fontRendererObj, universeName, this.width / 2, this.baseY + 5, 0xFFFFFF);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();

		if (!this.mc.thePlayer.isEntityAlive() || this.mc.thePlayer.isDead)
		{
			this.mc.thePlayer.closeScreen();
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		if (keyCode == ClientProxy.keyBindHopUniverse.getKeyCode() || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode())
		{
			this.mc.displayGuiScreen(null);

            if (this.mc.currentScreen == null)
            {
                this.mc.setIngameFocus();
            }
		}
		
		super.keyTyped(typedChar, keyCode);
	}

	@Override
	public void handleMouseInput() throws IOException
	{
		super.handleMouseInput();
		
		int wheelDirection = Mouse.getEventDWheel();

		if (wheelDirection != 0)
		{
			if (wheelDirection > 0)
			{
				wheelDirection = 1;
			}

			if (wheelDirection < 0)
			{
				wheelDirection = -1;
			}

			this.universeIndex += wheelDirection;

			if (this.universeIndex < 0)
			{
				this.universeIndex = UniverseAPI.instance().getUniverses().size() - 1;
			}
			else if (this.universeIndex >= UniverseAPI.instance().getUniverses().size())
			{
				this.universeIndex = 0;
			}

			SELECTED_UNIVERSE = UniverseAPI.instance().getUniverses().get(this.universeIndex);
		}
	}

	public static class ToggleButton extends GuiButton
	{

		private boolean isLeft;

		public ToggleButton(int id, int x, int y, boolean isLeft)
		{
			super(id, x, y, 20, 20, "");

			this.isLeft = isLeft;
		}

		@Override
		protected int getHoverState(boolean mouseOver)
		{
			byte hoverState = 0;

			if (!this.enabled)
			{
				hoverState = 2;
			}
			else if (mouseOver)
			{
				hoverState = 1;
			}

			return hoverState;
		}

		@Override
		public void drawButton(Minecraft mc, int mouseX, int mouseY)
		{
			if (this.visible)
			{
				mc.getTextureManager().bindTexture(BASE_TEXTURE);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
				int k = this.getHoverState(this.hovered);
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
				GlStateManager.blendFunc(770, 771);

				this.drawTexturedModalRect(this.xPosition, this.yPosition, k * 20, 174 + (this.isLeft ? 20 : 0), this.width, this.height);

				this.mouseDragged(mc, mouseX, mouseY);
			}
		}

	}

	public static class TravelButton extends GuiButton
	{

		public TravelButton(int id, int x, int y)
		{
			super(id, x, y, 72, 11, StatCollector.translateToLocal("gui.travel.name"));
		}

		@Override
		protected int getHoverState(boolean mouseOver)
		{
			byte hoverState = 0;

			if (!this.enabled)
			{
				hoverState = 2;
			}
			else if (mouseOver)
			{
				hoverState = 1;
			}

			return hoverState;
		}

		@Override
		public void drawButton(Minecraft mc, int mouseX, int mouseY)
		{
			if (this.visible)
			{
				FontRenderer fontrenderer = mc.fontRendererObj;
				mc.getTextureManager().bindTexture(BASE_TEXTURE);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
				int k = this.getHoverState(this.hovered);
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
				GlStateManager.blendFunc(770, 771);

				this.drawTexturedModalRect(this.xPosition, this.yPosition, k * this.width, 158, this.width, this.height);

				this.mouseDragged(mc, mouseX, mouseY);

				int l = 14737632;

				if (this.packedFGColour != 0)
				{
					l = this.packedFGColour;
				}
				else if (!this.enabled)
				{
					l = 10526880;
				}
				else if (this.hovered)
				{
					l = 16777120;
				}

				this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + 1 + (this.height - 8) / 2, l);
			}
		}

	}

}
