package com.gildedgames.util.modules.tab.common.util;

import com.gildedgames.util.core.UtilModule;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This is what contains {@link ITab}s for a {@link GuiScreen}'s tab interface.
 * @author Brandon Pearce
 */
public class TabGroupHandler implements ITabGroupHandler
{
	public final BiMap<Integer, String> idMappings = HashBiMap.create();

	@SideOnly(Side.CLIENT)
	public TabGroup<ITabClient> clientGroup;

	public TabGroup<ITab> serverGroup;

	public int discriminant;

	public TabGroupHandler()
	{
		if (UtilModule.isClient())
		{
			this.clientGroup = new TabGroup<>();
		}

		this.serverGroup = new TabGroup<>();
	}

	@Override
	public void registerServerTab(ITab tab)
	{
		if (!this.idMappings.inverse().containsKey(tab.getUnlocalizedName()))
		{
			this.idMappings.put(this.discriminant++, tab.getUnlocalizedName());
		}

		this.getServerGroup().add(tab);
	}

	@Override
	public void registerClientTab(ITabClient tab)
	{
		if (!this.idMappings.inverse().containsKey(tab.getUnlocalizedName()))
		{
			this.idMappings.put(this.discriminant++, tab.getUnlocalizedName());
		}

		this.getClientGroup().add(tab);
	}

	@Override
	public int getDiscriminant(ITab tab)
	{
		return this.idMappings.inverse().get(tab.getUnlocalizedName());
	}

	@Override
	public ITabGroup<ITabClient> getClientGroup()
	{
		return this.clientGroup;
	}

	@Override
	public ITabGroup<ITab> getServerGroup()
	{
		return this.serverGroup;
	}
}
