package com.gildedgames.util.spawning;

import net.minecraft.entity.Entity;

public interface SpawnCondition
{
	boolean canSpawn(int areaSize, ScheduledSpawn scheduledSpawn, Entity entity);
}
