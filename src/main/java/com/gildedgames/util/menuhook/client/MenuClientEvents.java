package com.gildedgames.util.menuhook.client;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Mouse;

import com.gildedgames.util.core.ClientProxy;
import com.gildedgames.util.menuhook.MenuCore;

public class MenuClientEvents
{

	@SideOnly(Side.CLIENT)
	private GuiButton leftButton = new GuiButton(0, 5, 5, 20, 20, "<");
	
	@SideOnly(Side.CLIENT)
	private GuiButton rightButton = new GuiButton(0, 30, 5, 20, 20, ">");
	
	@SideOnly(Side.CLIENT)
	private Minecraft mc = Minecraft.getMinecraft();
	
	private void openMenu(IMenu menu)
	{
		MenuCore.locate().setCurrentMenu(menu);
		this.mc.displayGuiScreen(menu.getNewInstance());
		menu.onOpen();
	}

	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event)
	{
		GuiScreen gui = event.gui;
	}

	@SubscribeEvent
	public void tickStart(TickEvent.ClientTickEvent event)
	{
		if (event.phase == TickEvent.Phase.START)
		{
			IMenu menu = MenuCore.locate().getCurrentMenu();
			
			if (menu != null )
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
				
	            boolean disabled =  MenuCore.locate().getRegisteredMenus().size() <= 1;
	            
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
