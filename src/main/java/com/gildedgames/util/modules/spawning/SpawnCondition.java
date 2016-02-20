package com.gildedgames.util.modules.spawning;

import net.minecraft.entity.Entity;

public interface SpawnCondition
{
	boolean canSpawn(int areaSize, ScheduledSpawn scheduledSpawn, Entity entity);
}
