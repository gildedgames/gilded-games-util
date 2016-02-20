package com.gildedgames.util.modules.spawning.util;

import net.minecraft.world.World;

import com.gildedgames.util.modules.spawning.SpawnSettings;

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
		return 2;
	}

	@Override
	public int groupScattering()
	{
		return 2;
	}

	@Override
	public int maxSpawnAttempts()
	{
		return 25;
	}

	@Override
	public int spawnDistanceFromPlayer()
	{
		return 7;
	}

	@Override
	public int ticksBetweenUpdate()
	{
		return 40;
	}

	@Override
	public int updatesBetweenRespawn()
	{
		return 400;
	}

	@Override
	public float averageAmountOfEntitiesInArea(World world)
	{
		return 4.3f;
	}

	@Override
	public float amountOfEntitiesInAreaDeviation(World world)
	{
		return 1.5f;
	}

	@Override
	public int maxAmountOfEntitiesIn2x2Area(World world)
	{
		return 20;
	}

}
