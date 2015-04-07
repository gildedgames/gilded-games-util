package com.gildedgames.util.instances;

import net.minecraft.world.WorldServer;

public interface InstanceFactory<T extends Instance>
{
	T createInstance(WorldServer world, int minX, int id, InstanceHandler<T> instanceHandler);

	int distanceBetweenInstances();
}
