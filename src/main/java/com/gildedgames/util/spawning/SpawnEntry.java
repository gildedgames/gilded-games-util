package com.gildedgames.util.spawning;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class SpawnEntry
{
	private final Class<? extends Entity> entityClass;

	private final float avgGroupsInArea, groupsInAreaDeviation;

	private final float avgEntitiesInArea, entitiesInAreaDeviation;

	private final int minGroupSize, maxGroupSize;

	private final boolean onGround;

	public SpawnEntry(Class<? extends Entity> entityClass, int minGroupSize, int maxGroupSize, float avgGroupsInArea)
	{
		this(entityClass, minGroupSize, maxGroupSize, avgGroupsInArea, true);
	}

	public SpawnEntry(Class<? extends Entity> entityClass, int minGroupSize, int maxGroupSize, float avgGroupsInArea, boolean onGround)
	{
		this(entityClass, minGroupSize, maxGroupSize, avgGroupsInArea, avgGroupsInArea / 2, onGround);
	}

	public SpawnEntry(Class<? extends Entity> entityClass, int minGroupSize, int maxGroupSize, float avgGroupsInArea, float groupsInAreaDeviation, boolean onGround)
	{
		this.entityClass = entityClass;
		this.minGroupSize = minGroupSize;
		this.maxGroupSize = maxGroupSize;
		this.onGround = onGround;
		this.avgGroupsInArea = avgGroupsInArea;
		this.groupsInAreaDeviation = groupsInAreaDeviation;
		this.avgEntitiesInArea = avgGroupsInArea * (maxGroupSize - minGroupSize);
		this.entitiesInAreaDeviation = groupsInAreaDeviation / avgGroupsInArea * this.avgEntitiesInArea;
	}

	public Class<? extends Entity> getEntityClass()
	{
		return this.entityClass;
	}

	/**
	 * Uses a normal distribution to choose the amounts of groups 
	 * for an area. This method achieves much greater granularity
	 * for controlling spawning than using simple uniform distributions.
	 * 
	 * If the standard deviation is not explicitly given, it is
	 * average / 2, so around 95% of all numbers will be in the 
	 * interval [0, 2 * avgGroupsInArea], obviously with bias
	 * to the average. They are rounded to the nearest whole number.
	 */
	public int getGroupsInArea(Random rand)
	{
		return this.gaussianAbove0(rand, this.avgGroupsInArea, this.groupsInAreaDeviation);
	}

	public int getGroupSize(Random rand)
	{
		return this.getRandomNumberBetween(rand, this.minGroupSize, this.maxGroupSize);
	}

	public boolean onGround()
	{
		return this.onGround;
	}

	public int getMinimumHeight()
	{
		return 20;
	}

	public int getMaximumHeight()
	{
		return 256;
	}

	public int getHeight(Random rand)
	{
		return this.getRandomNumberBetween(rand, this.getMinimumHeight(), this.getMaximumHeight());
	}

	protected int getRandomNumberBetween(Random rand, int min, int max)
	{
		return min + rand.nextInt(max - min + 1);
	}

	public boolean shouldAttempt(World world, int posX, int posY, int posZ)
	{
		return true;
	}

	private int gaussianAbove0(Random rand, float avg, float deviation)
	{
		return (int) Math.round(Math.max(0, rand.nextGaussian() * deviation + avg));
	}

	/**
	 * Uses a normal distribution to determine if the area
	 * already contains enough of this entity type. 
	 */
	public boolean shouldSpawnMore(int currentAmountInArea, Random rand)
	{
		int randAmountOfEntities = this.gaussianAbove0(rand, this.avgEntitiesInArea, this.entitiesInAreaDeviation);
		return randAmountOfEntities > currentAmountInArea;
	}

}
