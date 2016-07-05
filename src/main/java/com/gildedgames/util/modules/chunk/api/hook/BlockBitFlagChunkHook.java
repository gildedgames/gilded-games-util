package com.gildedgames.util.modules.chunk.api.hook;

import net.minecraft.nbt.NBTTagCompound;

import java.util.BitSet;

/**
 * A simple block bit-flag implementation of {@link IChunkHook}.
 */
public class BlockBitFlagChunkHook implements IChunkHook
{
	private static final int CHUNK_SIZE = 16 * 256 * 16;

	private BitSet bits;

	public void mark(int x, int y, int z)
	{
		this.set(x, y, z, true);
	}

	public void clear(int x, int y, int z)
	{
		this.set(x, y, z, false);
	}

	public void set(int x, int y, int z, boolean value)
	{
		this.bits.set(this.getIndexFromCoordinate(x & 15, y & 15, z & 15), value);
	}

	public boolean isMarked(int x, int y, int z)
	{
		return this.bits.get(this.getIndexFromCoordinate(x & 15, y & 15, z & 15));
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
