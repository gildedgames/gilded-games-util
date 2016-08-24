package com.gildedgames.util.modules.instances;

import com.gildedgames.util.core.nbt.NBTHelper;
import com.gildedgames.util.core.util.BlockPosDimension;
import com.gildedgames.util.io_manager.io.NBT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class PlayerInstances
{
	private NBT activeInstance;

	private BlockPosDimension outside;

	private EntityPlayer player;

	public PlayerInstances(EntityPlayer player)
	{
		this.player = player;
	}

	public NBT getInstance()
	{
		return this.activeInstance;
	}

	public void setInstance(NBT instance)
	{
		this.activeInstance = instance;
	}

	public BlockPosDimension outside()
	{
		return this.outside;
	}

	public void setOutside(BlockPosDimension pos)
	{
		this.outside = pos;
	}

	public static class Storage implements Capability.IStorage<PlayerInstances>
	{

		@Override
		public NBTBase writeNBT(Capability<PlayerInstances> capability, PlayerInstances instance, EnumFacing side)
		{
			NBTTagCompound compound = new NBTTagCompound();

			compound.setTag("outside", NBTHelper.serializeBlockPosDimension(instance.outside));

			NBTHelper.fullySerialize("activeInstance", instance.activeInstance, compound);

			return compound;
		}

		@Override
		public void readNBT(Capability<PlayerInstances> capability, PlayerInstances instance, EnumFacing side, NBTBase nbt)
		{
			if (!(nbt instanceof NBTTagCompound))
			{
				return;
			}

			NBTTagCompound compound = (NBTTagCompound) nbt;

			instance.outside = NBTHelper.getBlockPosDimension(compound.getCompoundTag("outside"));
			instance.activeInstance = NBTHelper.fullyDeserialize("activeInstance", compound);
		}
	}

}
