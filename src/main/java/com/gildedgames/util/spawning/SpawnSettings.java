package com.gildedgames.util.spawning;

public interface SpawnSettings
{
	/**
	 * @return The width and length in chunks of 
	 * an area for on generation spawning.
	 * Represents an area of areaSize() * 16 by
	 * areaSize() * 16.
	 */
	int areaSize();

	/**
	 * @return The width and length in chunks of 
	 * an area for during ticking spawning.
	 * Represents an area of areaSizePerTick() * 16 
	 * by areaSizePerTick() * 16.
	 */
	int areaSizePerTick();

	int averageAmountOfEntitiesInArea();

	int amountOfEntitiesInAreaDeviation();

	int groupScattering();

	int maxSpawnAttempts();

	int maxAmountOfEntitiesIn2x2Area();

	int spawnDistanceFromPlayer();

	int ticksBetweenUpdate();

	/**
	 * @return The amount of updates between 
	 * each wave of respawning.
	 * An update is ticksBetweenUpdate() 
	 * amount of ticks
	 */
	int updatesBetweenRespawn();
}
