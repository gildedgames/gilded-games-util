package com.gildedgames.util.modules.chunk.impl.hooks;

import com.gildedgames.util.modules.chunk.api.hook.IChunkHook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import java.util.BitSet;

/**
 * A simple block bit-flag implementation of {@link IChunkHook}.
 */
public class BlockBitFlagChunkHook implements IChunkHook
{
	private static final int CHUNK_SIZE = 16 * 256 * 16;

	private BitSet bits = new BitSet(CHUNK_SIZE);

	public void mark(BlockPos pos)
	{
		this.set(pos, true);
	}

	public void clear(BlockPos pos)
	{
		this.set(pos, false);
	}

	public void set(BlockPos pos, boolean value)
	{
		this.bits.set(this.getIndexFromCoordinate(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15), value);
	}

	public boolean isMarked(BlockPos pos)
	{
		return this.bits.get(this.getIndexFromCoordinate(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15));
	}

	@Override
	public void write(NBTTagCompound output)
	{
		output.setByteArray("bits", this.bits.toByteArray());
	}

	@Override
	public void read(NBTTagCompound input)
	{
		byte[] data = null;

		if (input.hasKey("bits"))
		{
			data = input.getByteArray("bits");
		}

		if (data == null)
		{
			this.bits = new BitSet(CHUNK_SIZE);
		}
		else
		{
			this.bits = BitSet.valueOf(data);
		}
	}

	private int getIndexFromCoordinate(int x, int y, int z)
	{
		return (x * 256 * 16 + y * 16 + z);
	}
}
