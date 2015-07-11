package com.gildedgames.util.minecraft.gamemode;

import java.util.List;

import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.gildedgames.util.core.UtilCore;

public class GameModeGuiInjector
{

	private int index;
	
	private static final int DEFAULT_GAMEMODE_SIZE = 3;
	
	public GameModeGuiInjector()
	{
		
	}
	
	@SubscribeEvent
	public void onGuiInitPre(GuiScreenEvent.InitGuiEvent.Pre event)
	{
		if (event.gui instanceof GuiCreateWorld)
		{
			this.index = 0;
		}

		if (event.gui instanceof GuiSelectWorld)
		{
			GuiSelectWorld gui = (GuiSelectWorld)event.gui;
			
			List<GameMode> modes = UtilCore.locate().getGameModeInjector().getGameModes();
			
			if (!modes.isEmpty())
			{
				gui.field_146635_w = new String[DEFAULT_GAMEMODE_SIZE + 1 + modes.size()];
				
				for (int i = 0; i < modes.size(); i++)
				{
					GameMode mode = modes.get(i);
					
					gui.field_146635_w[GameModeInjector.getEnumID(mode)] = I18n.format(mode.getName() + " Mode", new Object[0]);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onActionPerformed(GuiScreenEvent.ActionPerformedEvent.Post event)
	{
		List<GameMode> modes = UtilCore.locate().getGameModeInjector().getGameModes();
		
		if (modes.isEmpty())
		{
			return;
		}
		
		if (event.gui instanceof GuiCreateWorld)
		{
			GuiCreateWorld gui = (GuiCreateWorld) event.gui;
			
			if (event.button.id == 2)
			{
				this.index++;
				
				if (this.index >= DEFAULT_GAMEMODE_SIZE)
				{
					int modeIndex = this.index - DEFAULT_GAMEMODE_SIZE;
					
					if (modeIndex < modes.size())
					{
						GameMode mode = modes.get(modeIndex);
						
						gui.field_146342_r = mode.getID();
						
						gui.func_146319_h();
						
						gui.btnGameMode.displayString = I18n.format("selectWorld.gameMode", new Object[0]) + ": " + I18n.format(mode.getName(), new Object[0]);
						gui.field_146323_G = I18n.format(mode.getDescriptionLine1(), new Object[0]);
				        gui.field_146328_H = I18n.format(mode.getDescriptionLine2(), new Object[0]);
					}
					else
					{
						gui.field_146342_r = "survival";
						
						gui.func_146319_h();
						
						this.index = 0;
					}
				}
			}
		}
	}
	
}
