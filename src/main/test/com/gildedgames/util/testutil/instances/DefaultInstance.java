package com.gildedgames.util.testutil.instances;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.modules.instances.Instance;
import com.gildedgames.util.modules.instances.InstanceHandler;
import com.google.common.collect.Lists;

public class DefaultInstance implements Instance
{

	private List<EntityPlayer> players = Lists.newArrayList();

	private String key;

	public DefaultInstance(int id, InstanceHandler<DefaultInstance> instanceHandler)
	{

	}

	@Override
	public void write(NBTTagCompound output)
	{
	}

	@Override
	public void read(NBTTagCompound input)
	{
	}

	@Override
	public void onJoin(EntityPlayer player)
	{
		this.players.add(player);
	}

	@Override
	public void onLeave(EntityPlayer player)
	{
		this.players.remove(player);
	}

	@Override
	public List<EntityPlayer> getPlayers()
	{
		return this.players;
	}

	public String getKey()
	{
		return this.key;
	}

	protected void setKey(String key)
	{
		this.key = key;
	}

}
