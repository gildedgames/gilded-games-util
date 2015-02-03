package com.gildedgames.util.menu.client;

import java.io.File;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Mouse;

import com.gildedgames.util.core.ClientProxy;
import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.core.nbt.NBTFactory;
import com.gildedgames.util.core.nbt.NBTFile;
import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.menu.MenuCore;

public class MenuClientEvents
{

	@SideOnly(Side.CLIENT)
	private GuiButton leftButton = new GuiButton(0, 5, 5, 20, 20, "<");

	@SideOnly(Side.CLIENT)
	private GuiButton rightButton = new GuiButton(0, 30, 5, 20, 20, ">");

	@SideOnly(Side.CLIENT)
	private Minecraft mc = Minecraft.getMinecraft();

	private File configSaveLocation;

	public static class MenuConfig implements NBT
	{

		private String menuID;

		private MenuConfig()
		{

		}

		@Override
		public void write(NBTTagCompound output)
		{
			output.setString("menuID", this.menuID);
		}

		@Override
		public void read(NBTTagCompound input)
		{
			this.menuID = input.getString("menuID");
		}

	}

	public MenuClientEvents()
	{
		this.configSaveLocation = new File(Minecraft.getMinecraft().mcDataDir, "mod-config\\menu.dat");
	}

	private void openMenu(IMenu menu)
	{
		this.openMenu(menu, true);
	}

	private void openMenu(IMenu menu, boolean shouldSaveToConfig)
	{
		if (menu == null)
		{
			return;
		}

		MenuCore.locate().setCurrentMenu(menu);

		this.mc.displayGuiScreen(menu.getNewInstance());

		menu.onOpen();

		if (!shouldSaveToConfig)
		{
			return;
		}

		try
		{
			MenuConfig config = new MenuConfig();

			config.menuID = menu.getID();

			IOCore.io().writeFile(this.configSaveLocation, new NBTFile(this.configSaveLocation, config, MenuConfig.class), new NBTFactory());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event)
	{
		GuiScreen gui = event.gui;

		if (gui instanceof GuiMainMenu && MenuCore.locate().getCurrentMenu() == null)
		{
			event.setCanceled(true);

			if (this.configSaveLocation.exists())
			{
				try
				{
					MenuConfig config = new MenuConfig();

					IOCore.io().readFile(this.configSaveLocation, new NBTFile(this.configSaveLocation, config, MenuConfig.class), new NBTFactory());

					this.openMenu(MenuCore.locate().getMenuFromID(config.menuID), false);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				this.openMenu(ClientProxy.MINECRAFT_MENU);
			}
		}
	}

	@SubscribeEvent
	public void tickStart(TickEvent.ClientTickEvent event)
	{
		if (event.phase == TickEvent.Phase.START)
		{
			IMenu menu = MenuCore.locate().getCurrentMenu();

			if (menu != null)
			{
				if (this.mc.currentScreen != null && this.mc.currentScreen.getClass() == menu.getMenuClass())
				{
					final ScaledResolution scaledresolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);

					int scaledWidth = scaledresolution.getScaledWidth();
					int scaledHeight = scaledresolution.getScaledHeight();

					final int mouseX = Mouse.getX() * scaledWidth / this.mc.displayWidth;
					final int mouseY = scaledHeight - Mouse.getY() * scaledHeight / this.mc.displayHeight - 1;

					while (Mouse.next())
					{
						boolean customButtons = menu.useCustomButtons();

						boolean leftButtonMoused = customButtons ? menu.getLeftButton().isMousedOver(mouseX, mouseY) : this.leftButton.isMouseOver();

						boolean rightButtonMoused = customButtons ? menu.getRightButton().isMousedOver(mouseX, mouseY) : this.rightButton.isMouseOver();

						if (Mouse.getEventButtonState() && MenuCore.locate().getRegisteredMenus().size() > 1 && (leftButtonMoused || rightButtonMoused))
						{
							this.leftButton.playPressSound(this.mc.getSoundHandler());

							if (leftButtonMoused)
							{
								this.openMenu(MenuCore.locate().getPreviousMenu());
							}
							else if (rightButtonMoused)
							{
								this.openMenu(MenuCore.locate().getNextMenu());
							}
						}
						else if (Minecraft.getMinecraft().currentScreen != null)
						{
							try
							{
								this.mc.currentScreen.handleMouseInput();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
					}
				}
			}
			else if (this.configSaveLocation.exists())
			{
				try
				{
					MenuConfig config = new MenuConfig();

					IOCore.io().readFile(this.configSaveLocation, new NBTFile(this.configSaveLocation, config, MenuConfig.class), new NBTFactory());

					this.openMenu(MenuCore.locate().getMenuFromID(config.menuID), false);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				this.openMenu(ClientProxy.MINECRAFT_MENU);
			}
		}
	}

	@SubscribeEvent
	public void tickEnd(TickEvent.RenderTickEvent event)
	{
		if (event.phase == TickEvent.Phase.END)
		{
			IMenu menu = MenuCore.locate().getCurrentMenu();

			if (menu != null && this.mc.currentScreen != null && this.mc.currentScreen.getClass() == menu.getMenuClass())
			{
				final ScaledResolution scaledresolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);

				int scaledWidth = scaledresolution.getScaledWidth();
				int scaledHeight = scaledresolution.getScaledHeight();

				final int mouseX = Mouse.getX() * scaledWidth / this.mc.displayWidth;
				final int mouseY = scaledHeight - Mouse.getY() * scaledHeight / this.mc.displayHeight - 1;

				boolean disabled = MenuCore.locate().getRegisteredMenus().size() <= 1;

				if (!disabled)
				{
					if (menu.useCustomButtons())
					{
						menu.getLeftButton().render(mouseX, mouseY, "<");
						menu.getRightButton().render(mouseX, mouseY, ">");
					}
					else
					{
						this.leftButton.drawButton(this.mc, mouseX, mouseY);
						this.rightButton.drawButton(this.mc, mouseX, mouseY);
					}
				}
			}
		}
	}

}
