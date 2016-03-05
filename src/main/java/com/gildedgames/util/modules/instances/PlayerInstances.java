package com.gildedgames.util.modules.instances;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.core.nbt.NBTHelper;
import com.gildedgames.util.modules.entityhook.api.IEntityHookFactory;
import com.gildedgames.util.modules.entityhook.impl.hooks.EntityHook;
import com.gildedgames.util.modules.entityhook.impl.providers.PlayerHookProvider;
import com.gildedgames.util.modules.world.common.BlockPosDimension;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerInstances extends EntityHook<EntityPlayer>
{
	public static final PlayerHookProvider<PlayerInstances> PROVIDER = new PlayerHookProvider<>("instances", new Factory());

	private NBT activeInstance;

	private BlockPosDimension outside;

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

	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTHelper.setBlockPosDimension(compound, this.outside, "outside");
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		this.outside = NBTHelper.getBlockPosDimension(compound, "outside");
	}

	public static class Factory implements IEntityHookFactory<PlayerInstances>
	{
		@Override
		public PlayerInstances createHook()
		{
			return new PlayerInstances();
		}

		@Override
		public void writeFull(ByteBuf buf, PlayerInstances hook) { }

		@Override
		public void readFull(ByteBuf buf, PlayerInstances hook) { }
	}
}