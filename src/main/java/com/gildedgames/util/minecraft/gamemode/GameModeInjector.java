package com.gildedgames.util.minecraft.gamemode;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.common.util.EnumHelper;

import com.google.common.collect.ImmutableList;

public class GameModeInjector
{

	private List<GameMode> gameModes = new ArrayList<GameMode>();

	public GameModeInjector()
	{
		
	}
	
	private int getHighestID()
	{
		int highest = 0;
		
		for (GameType type : GameType.values())
		{
			highest = Math.max(highest, type.getID() + 1);
		}
		
		return highest;
	}

	public void inject(GameMode mode)
	{
		this.gameModes.add(mode);

		EnumHelper.addEnum(GameType.class, mode.getName().toUpperCase(), new Class[]{Integer.TYPE, String.class}, new Object[]{this.getHighestID(), mode.getID()});
	}

	public ImmutableList<GameMode> getGameModes()
	{
		return ImmutableList.copyOf(this.gameModes);
	}
	
	public int size()
	{
		return this.gameModes.size();
	}

	public GameMode get(String id)
	{
		for (GameMode gameMode : this.gameModes)
		{
			if (gameMode.getID().equals(id))
			{
				return gameMode;
			}
		}

		return null;
	}
	
	public GameMode convertToGameMode(GameType type)
	{
		for (GameMode mode : this.gameModes)
		{
			if (mode.getID().equals(type.getName()))
			{
				return mode;
			}
		}
		
		return null;
	}
	
	public static int getEnumID(GameMode mode)
	{
		return GameType.getByName(mode.getID()).getID();
	}

}
