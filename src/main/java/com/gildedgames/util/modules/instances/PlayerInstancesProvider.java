package com.gildedgames.util.modules.instances;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PlayerInstancesProvider implements ICapabilitySerializable<NBTBase>
{
	private final PlayerInstances.Storage storage = new PlayerInstances.Storage();

	private final PlayerInstances playerInstances;

	public PlayerInstancesProvider(PlayerInstances playerInstances)
	{
		this.playerInstances = playerInstances;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == InstanceModule.PLAYER_INSTANCES && this.playerInstances != null;
	}

	@Override
	@SuppressWarnings("unchecked" /* joy... */)
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (this.hasCapability(capability, facing))
		{
			return (T) this.playerInstances;
		}

		return null;
	}

	@Override
	public NBTBase serializeNBT()
	{
		return this.storage.writeNBT(InstanceModule.PLAYER_INSTANCES, this.playerInstances, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		this.storage.readNBT(InstanceModule.PLAYER_INSTANCES, this.playerInstances, null, nbt);
	}

}
