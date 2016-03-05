package com.gildedgames.util.modules.menu.impl.client;

import java.io.File;
import java.io.IOException;

import com.gildedgames.util.modules.menu.MenuModule;
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

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.core.nbt.NBTFactory;
import com.gildedgames.util.core.nbt.NBTFile;
import com.gildedgames.util.io_manager.IOCore;

public class MenuClientEvents
{

	@SideOnly(Side.CLIENT)
	private final GuiButton leftButton = new GuiButton(0, 5, 5, 20, 20, "<");

	@SideOnly(Side.CLIENT)
	private final GuiButton rightButton = new GuiButton(0, 30, 5, 20, 20, ">");

	@SideOnly(Side.CLIENT)
	private final Minecraft mc = Minecraft.getMinecraft();

	private final File configSaveLocation;
	
	private boolean firstTick = true;

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
			menu = MenuModule.MINECRAFT_MENU;
		}

		int mouseX = Mouse.getX();
		int mouseY = Mouse.getY();

		MenuModule.locate().setCurrentMenu(menu);

		this.mc.displayGuiScreen(menu.getNewInstance());

		menu.onOpen();

		if (!this.firstTick)
		{
			Mouse.setCursorPosition(mouseX, mouseY);
		}

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
		
		if (gui == null)
		{
			return;
		}

		IMenu menu = MenuModule.locate().getCurrentMenu();

		IMenu hooked = MenuModule.locate().getMenuFromScreen(gui);

		if ((gui instanceof GuiMainMenu || hooked != null) && menu == null || gui.getClass() == GuiMainMenu.class && menu.getMenuClass() != GuiMainMenu.class)
		{
			event.setCanceled(true);

			if (this.configSaveLocation.exists())
			{
				try
				{
					MenuConfig config = new MenuConfig();

					IOCore.io().readFile(this.configSaveLocation, new NBTFile(this.configSaveLocation, config, MenuConfig.class), new NBTFactory());

					this.openMenu(MenuModule.locate().getMenuFromID(config.menuID), false);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				this.openMenu(MenuModule.MINECRAFT_MENU);
			}
		}
		else if (menu != null && !gui.getClass().isAssignableFrom(menu.getMenuClass()))
		{
			MenuModule.locate().setCurrentMenu(null);
		}
	}

	@SubscribeEvent
	public void tickStart(TickEvent.ClientTickEvent event)
	{
		if (event.phase == TickEvent.Phase.START)
		{
			if (this.firstTick)
			{
				this.firstTick = false;
			}
			
			IMenu menu = MenuModule.locate().getCurrentMenu();

			if (menu != null)
			{
				if (this.mc.currentScreen != null && this.mc.currentScreen.getClass() == menu.getMenuClass())
				{
					final ScaledResolution scaledresolution = new ScaledResolution(this.mc);

					int scaledWidth = scaledresolution.getScaledWidth();
					int scaledHeight = scaledresolution.getScaledHeight();

					final int mouseX = Mouse.getX() * scaledWidth / this.mc.displayWidth;
					final int mouseY = scaledHeight - Mouse.getY() * scaledHeight / this.mc.displayHeight - 1;

					while (Mouse.next())
					{
						boolean customButtons = menu.useCustomButtons();

						boolean leftButtonMoused = customButtons ? menu.getLeftButton().isMousedOver(mouseX, mouseY) : this.leftButton.isMouseOver();

						boolean rightButtonMoused = customButtons ? menu.getRightButton().isMousedOver(mouseX, mouseY) : this.rightButton.isMouseOver();

						if (Mouse.getEventButtonState() && MenuModule.locate().getRegisteredMenus().size() > 1 && (leftButtonMoused || rightButtonMoused))
						{
							this.leftButton.playPressSound(this.mc.getSoundHandler());

							this.openMenu(leftButtonMoused ? MenuModule.locate().getPrevMenu() : MenuModule.locate().getNextMenu());
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

		}
	}

	@SubscribeEvent
	public void tickEnd(TickEvent.RenderTickEvent event)
	{
		if (event.phase == TickEvent.Phase.END)
		{
			IMenu menu = MenuModule.locate().getCurrentMenu();

			if (menu != null && this.mc.currentScreen != null && this.mc.currentScreen.getClass() == menu.getMenuClass())
			{
				final ScaledResolution scaledresolution = new ScaledResolution(this.mc);

				int scaledWidth = scaledresolution.getScaledWidth();
				int scaledHeight = scaledresolution.getScaledHeight();

				final int mouseX = Mouse.getX() * scaledWidth / this.mc.displayWidth;
				final int mouseY = scaledHeight - Mouse.getY() * scaledHeight / this.mc.displayHeight - 1;

				boolean disabled = MenuModule.locate().getRegisteredMenus().size() <= 1;

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
