package com.gildedgames.util.universe.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.universe.UniverseCore;
import com.gildedgames.util.universe.common.player.PlayerUniverse;
import com.gildedgames.util.universe.common.universe.UniverseMinecraft;
import com.gildedgames.util.universe.common.util.IUniverse;
import com.gildedgames.util.universe.common.util.IUniverseListener;

public class UniverseAPI
{

	private Map<String, IUniverse> universes = new HashMap<String, IUniverse>();

	private List<IUniverseListener> listeners = new ArrayList<IUniverseListener>();

	private final IUniverse MINECRAFT = new UniverseMinecraft();

	private final String MINECRAFT_ID = "minecraft:vanilla";

	public static UniverseAPI instance()
	{
		return UniverseCore.locate().getAPI();
	}

	public void travelTo(IUniverse universe, EntityPlayer player)
	{
		if (universe == null || player.worldObj.isRemote)
		{
			return;
		}

		String id = this.getIDFrom(universe);
		boolean hasTravelBefore = false;

		PlayerUniverse playerHook = UniverseCore.locate().getPlayers().get(player);

		for (IUniverseListener listener : this.listeners)
		{
			if (listener != null)
			{
				listener.onTravelToPre(universe, player, hasTravelBefore);
			}
		}

		playerHook.setUniverseID(id);

		universe.onTravelTo(player, hasTravelBefore);

		for (IUniverseListener listener : this.listeners)
		{
			if (listener != null)
			{
				listener.onTravelToPost(universe, player, hasTravelBefore);
			}
		}
	}

	public void travelFrom(IUniverse universe, EntityPlayer player)
	{
		if (universe == null)
		{
			return;
		}

		String id = this.getIDFrom(universe);//TODO: id is not used here!
		boolean hasLeftBefore = false;

		for (IUniverseListener listener : this.listeners)
		{
			if (listener != null)
			{
				listener.onTravelFromPre(universe, player, hasLeftBefore);
			}
		}

		universe.onTravelFrom(player, hasLeftBefore);

		for (IUniverseListener listener : this.listeners)
		{
			if (listener != null)
			{
				listener.onTravelFromPost(universe, player, hasLeftBefore);
			}
		}
	}

	public void register(String id, IUniverse universe)
	{
		this.universes.put(id, universe);
	}

	public void register(IUniverseListener listener)
	{
		this.listeners.add(listener);
	}

	public List<IUniverse> getUniverses()
	{
		return new ArrayList<IUniverse>(this.universes.values());
	}

	public String getIDFrom(IUniverse universe)
	{
		String universeID = null;

		for (Map.Entry<String, IUniverse> entry : this.universes.entrySet())
		{
			String key = entry.getKey();
			Object value = entry.getValue();

			if (value == universe)
			{
				universeID = key;
			}
		}

		return universeID;
	}

	public IUniverse getFromID(String id)
	{
		return this.universes.get(id);
	}

	public IUniverse getMinecraftUniverse()
	{
		return this.MINECRAFT;
	}

	public String getMinecraftUniverseID()
	{
		return this.MINECRAFT_ID;
	}

}
