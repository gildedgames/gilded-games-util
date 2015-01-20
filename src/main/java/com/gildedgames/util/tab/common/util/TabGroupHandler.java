package com.gildedgames.util.tab.common.util;

import java.util.EnumMap;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;

import com.google.common.collect.Maps;

/**
 * This is what contains {@link ITab}s for a {@link GuiScreen}'s tab interface.
 * @author Brandon Pearce
 */
public class TabGroupHandler implements ITabGroupHandler
{

	public EnumMap<Side, TabGroup> sidedGroups = Maps.newEnumMap(Side.class);
	
	public TabGroupHandler()
	{
		this.sidedGroups.put(Side.CLIENT, new TabGroup());
		this.sidedGroups.put(Side.SERVER, new TabGroup());
	}
	
	@Override
	public ITabGroup getSide(Side side)
	{
		return this.sidedGroups.get(side);
	}
	
}
