package com.gildedgames.util.modules.instances;

import net.minecraft.util.BlockPos;

public class BlockPosDimension extends BlockPos
{
	private final int dimensionId;

	public BlockPosDimension(int x, int y, int z, int dimensionId)
	{
		super(x, y, z);
		this.dimensionId = dimensionId;
	}

	public int dimId()
	{
		return this.dimensionId;
	}

}
