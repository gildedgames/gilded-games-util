package com.gildedgames.util.spawning.util;

import com.gildedgames.util.spawning.SpawnSettings;

public class DefaultSpawnSettings implements SpawnSettings
{

	@Override
	public int areaSize()
	{
		return 10;
	}

	@Override
	public int areaSizePerTick()
	{
		return 6;
	}

	@Override
	public int groupScattering()
	{
		return 2;
	}

	@Override
	public int maxSpawnAttempts()
	{
		return 20;
	}

	@Override
	public int spawnDistanceFromPlayer()
	{
		return 7;
	}

	@Override
	public int ticksBetweenUpdate()
	{
		return 30;
	}

	@Override
	public int updatesBetweenRespawn()
	{
		return 300;
	}

	@Override
	public int averageAmountOfEntitiesInArea()
	{
		return 30;
	}

	@Override
	public int amountOfEntitiesInAreaDeviation()
	{
		return 10;
	}

	@Override
	public int maxAmountOfEntitiesIn2x2Area()
	{
		return 160;
	}

}
