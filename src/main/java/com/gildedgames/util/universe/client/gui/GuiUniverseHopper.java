package com.gildedgames.util.universe.client.gui;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.universe.UniverseCore;
import com.gildedgames.util.universe.common.UniverseAPI;
import com.gildedgames.util.universe.common.networking.messages.MessageTravelUniverse;
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

	private PlayerUniverse usingPlayer = UniverseCore.locate().getPlayers().get(Minecraft.getMinecraft().thePlayer);

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
	protected void actionPerformed(GuiButton button)
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

			UtilCore.NETWORK.sendToServer(new MessageTravelUniverse(id));

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
		if (SELECTED_UNIVERSE == this.usingPlayer.getUniverse())
		{
			this.travelButton.enabled = false;
		}
		else
		{
			this.travelButton.enabled = true;
		}

		this.drawDefaultBackground();

		this.mc.renderEngine.bindTexture(BASE_TEXTURE);
		this.drawTexturedModalRect(this.baseX, this.baseY, 0, 0, BASE_WIDTH, BASE_HEIGHT);

		this.mc.renderEngine.bindTexture(SELECTED_UNIVERSE.getPreviewTexture());
		this.drawTexturedModalRect(this.baseX + 6, this.baseY + 19, 0, 0, PREVIEW_WIDTH, PREVIEW_HEIGHT);

		final String universeName = StatCollector.translateToLocal(SELECTED_UNIVERSE.getUnlocalizedName());

		this.drawCenteredString(this.mc.fontRenderer, universeName, this.width / 2, this.baseY + 5, 0xFFFFFF);

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
	protected void keyTyped(char typedChar, int keyCode)
	{
		if (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode())
		{
			this.mc.thePlayer.closeScreen();
		}
	}

	@Override
	public void handleMouseInput()
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

		private boolean hovered, isLeft;

		public ToggleButton(int id, int x, int y, boolean isLeft)
		{
			super(id, x, y, 20, 20, "");

			this.isLeft = isLeft;
		}

		@Override
		public int getHoverState(boolean mouseOver)
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
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
				int k = this.getHoverState(this.hovered);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(770, 771);

				this.drawTexturedModalRect(this.xPosition, this.yPosition, k * 20, 174 + (this.isLeft ? 20 : 0), this.width, this.height);

				this.mouseDragged(mc, mouseX, mouseY);
			}
		}

	}

	public static class TravelButton extends GuiButton
	{

		private boolean hovered;

		public TravelButton(int id, int x, int y)
		{
			super(id, x, y, 72, 11, StatCollector.translateToLocal("gui.travel.name"));
		}

		@Override
		public int getHoverState(boolean mouseOver)
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
				FontRenderer fontrenderer = mc.fontRenderer;
				mc.getTextureManager().bindTexture(BASE_TEXTURE);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
				int k = this.getHoverState(this.hovered);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(770, 771);

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
