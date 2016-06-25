package com.gildedgames.util.modules.chunk.api.hook;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

/**
 * An implementation of {@link IChunkHook} that can add an additional {@link IBlockState} to world coordinates.
 *
 * @author Collin Soares
 */
public abstract class ExtendedBlockStateChunkHook implements IChunkHook
{
	private static final int CHUNK_SIZE = 16 * 256 * 16;

	private final BlockStateContainer blockState = this.createBlockState();

	private byte[] metadata;

	/**
	 * Sets the {@link IBlockState} at {@param x}, {@param y}, {@param z} to {@param state}.
	 * @param x The world coordinate X
	 * @param y The world coordinate Y
	 * @param z The world coordinate Z
	 * @param state The new {@link IBlockState}
	 */
	public void setExtendedBlockState(int x, int y, int z, IBlockState state)
	{
		this.metadata[this.getIndexFromCoordinate(x & 15, y & 15, z & 15)] = (byte) this.getMetaFromState(state);
	}

	/**
	 * @param x The world coordinate X
	 * @param y The world coordinate Y
	 * @param z The world coordinate Z
	 * @return The {@link IBlockState} for this {@link IChunkHook} at the world
	 * 		coordinates{@param x}, {@param y}, {@param z}
	 */
	public IBlockState getExtendedBlockState(int x, int y, int z)
	{
		int meta = this.metadata[this.getIndexFromCoordinate(x & 15, y & 15, z & 15)];

		return this.getStateFromMeta(meta);
	}

	/**
	 * Serializes your {@link IBlockState}.
	 * @param state The {@link IBlockState} to be serialized
	 * @return The integer representation of your {@link IBlockState}
	 */
	abstract public int getMetaFromState(IBlockState state);

	/**
	 * De-serializes your {@link IBlockState}
	 * @param meta The integer representation of your {@link IBlockState}
	 * @return The de-serialized {@link IBlockState}
	 */
	abstract public IBlockState getStateFromMeta(int meta);

	/**
	 * Creates the {@link BlockStateContainer} that will hold your data.
	 * @return The new {@link IBlockState}
	 */
	abstract public BlockStateContainer createBlockState();

	@Override
	public void write(NBTTagCompound output)
	{
		output.setByteArray(this.getName(), this.metadata);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		byte[] data = null;

		if (input.hasKey(this.getName()))
		{
			data = input.getByteArray(this.getName());
		}

		if (data == null || data.length < CHUNK_SIZE)
		{
			data = new byte[CHUNK_SIZE];
		}

		this.metadata = data;
	}

	protected IBlockState getBaseState()
	{
		return this.blockState.getBaseState();
	}

	private int getIndexFromCoordinate(int x, int y, int z)
	{
		return (x * 256 * 16 + y * 16 + z);
	}

	public void addDebugInfo(List<String> info, int x, int y, int z)
	{
		IBlockState state = this.getExtendedBlockState(x, y, z);

		for (IProperty<?> property : this.getBaseState().getProperties().keySet())
		{
			String value = state.getValue(property).toString();

			if (property.getValueClass().equals(Boolean.class))
			{
				Boolean bool = (Boolean) state.getValue(property);

				value = (bool ? TextFormatting.GREEN : TextFormatting.RED) + value;
			}

			info.add(property.getName() + "=" + value);
		}
	}
}
