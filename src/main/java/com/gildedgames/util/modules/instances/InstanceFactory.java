package com.gildedgames.util.modules.instances;

import com.gildedgames.util.modules.instances.InstanceHandler;
import net.minecraft.world.DimensionType;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;

public interface InstanceFactory<T extends Instance>
{
	T createInstance(int dimId, InstanceHandler<T> instanceHandler);

	DimensionType dimensionType();

	Teleporter getTeleporter(WorldServer worldIn);
}
