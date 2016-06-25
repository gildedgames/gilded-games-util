package com.gildedgames.util.modules.instances;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.core.nbt.NBTHelper;
import com.gildedgames.util.modules.world.common.BlockPosDimension;
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

	protected void setInstance(NBT instance)
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

			NBTHelper.setBlockPosDimension(compound, instance.outside, "outside");

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

			instance.outside = NBTHelper.getBlockPosDimension(compound, "outside");
			instance.activeInstance = NBTHelper.fullyDeserialize("activeInstance", compound);
		}
	}

}
