package com.gildedgames.util.worldhook.common.world;

public interface IWorldWrapperFactory<W extends IWorld>
{
	/**
	 * Factory that creates IWorld wrappers. 
	 * @param dimId
	 * @param isRemote True if client
	 * @return
	 */
	W create(int dimId, boolean isRemote);
}
