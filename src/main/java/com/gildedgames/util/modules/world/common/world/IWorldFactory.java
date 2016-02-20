package com.gildedgames.util.modules.world.common.world;

public interface IWorldFactory<W extends IWorld>
{
	/**
	 * Factory that creates IWorld wrappers. 
	 * @param dimId
	 * @param isRemote True if client
	 * @return
	 */
	W create(int dimId, boolean isRemote);
}
