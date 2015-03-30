package com.gildedgames.util.spawning;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class SpawnAreaPerTick extends SpawnArea
{

	private boolean isAwake = true, shouldRespawn = false;

	private int secondsUpdated = 0;

	public SpawnAreaPerTick(SpawnManager spawnManager, int areaX, int areaZ, List<SpawnEntry> spawnEntries)
	{
		super(spawnManager, areaX, areaZ, spawnEntries);
	}

	public boolean isAwake()
	{
		return this.isAwake;
	}

	public int getAmountOfUpdates()
	{
		return this.secondsUpdated;
	}

	public void wake()
	{
		this.isAwake = true;
	}

	public boolean shouldRespawn()
	{
		return this.shouldRespawn;
	}

	public void setShouldRespawn(boolean shouldRespawn)
	{
		this.shouldRespawn = shouldRespawn;
	}

	public void onUpdate(int areaSize, World world)
	{
		this.secondsUpdated++;

		for (Iterator<ScheduledSpawn> iter = this.scheduledSpawns.iterator(); iter.hasNext();)
		{
			ScheduledSpawn scheduledSpawn = iter.next();
			if (scheduledSpawn.spawn(areaSize, world) || scheduledSpawn.getSpawnAttempts() >= this.spawnManager.getMaxSpawnAttempts())
			{
				iter.remove();
			}
		}

		this.isAwake = false;
	}

	public boolean noSchedulesLeft()
	{
		return this.scheduledSpawns.isEmpty();
	}

	/**
	 * Schedules spawning for this area in an upcoming tick
	 */
	@Override
	public void schedule(World world, int areaSize, int groupScattering, Random rand)
	{
		final int spawningRange = areaSize * 16;
		//final int halfArea = spawningRange / 2;

		final int posX = this.areaX * areaSize * 16;
		final int posZ = this.areaZ * areaSize * 16;

		/*AxisAlignedBB twobytwo = AxisAlignedBB.getBoundingBox(posX - halfArea, 0, posZ - halfArea, posX + spawningRange + halfArea, world.getActualHeight(), posZ + spawningRange + halfArea);
		final List<EntityLivingBase> listOfEntities2x2 = world.getEntitiesWithinAABB(EntityLivingBase.class, twobytwo);
		final int entitiesIn2x2Area = listOfEntities2x2.size();
		final int max2b2Entities = this.spawnManager.getMaxAmountOfEntitiesIn2x2Area();

		if (entitiesIn2x2Area > max2b2Entities)
		{
			return;
		}*/

		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(posX, 0, posZ, posX + spawningRange, world.getActualHeight(), posZ + spawningRange);
		IEntitySelector entitySelector = new IEntitySelector()
		{
			@Override
			public boolean isEntityApplicable(Entity entity)
			{
				for (SpawnEntry s : SpawnAreaPerTick.this.spawnEntries)
				{
					if (s.getEntityClass() == entity.getClass())
					{
						return true;
					}
				}
				return false;
			}
		};
		final List<EntityLivingBase> listOfEntitiesInArea = world.selectEntitiesWithinAABB(Entity.class, bb, entitySelector);

		final int entitiesInArea = listOfEntitiesInArea.size();

		final int targetEntities = this.spawnManager.getTargetAmountOfEntities(rand);

		if (entitiesInArea > targetEntities)
		{
			return;
		}

		for (final SpawnEntry entry : this.spawnEntries)
		{
			int entityCount = 0;//Count the amount of entities of this type already spawned

			for (final Entity entity : listOfEntitiesInArea)
			{
				if (entry.getEntityClass() == entity.getClass())
				{
					entityCount++;
				}
			}

			//final boolean spawnMore = entry.shouldSpawnMore(entityCount, rand);

			//TODO: Right now it does not go to the target amount of entities.
			//While this is nice in terms of variations, it is maybe not expected.
			if (entry.shouldSpawnMore(entityCount, rand))
			{
				final int numGroups = entry.getGroupsInArea(rand);

				for (int i = 0; i < numGroups; ++i)
				{
					int amountOfSpawns = this.scheduledSpawns.size();
					if (entitiesInArea + amountOfSpawns > targetEntities)
					{
						return;
					}

					/*if (entitiesIn2x2Area + amountOfSpawns > max2b2Entities)
					{
						return;
					}*/
					final int groupSize = entry.getGroupSize(rand);

					final int groupX = posX + rand.nextInt(spawningRange);
					final int groupZ = posZ + rand.nextInt(spawningRange);

					for (int j = 0; j < groupSize; ++j)
					{
						final int scatterX = -groupScattering + rand.nextInt(groupScattering * 2);
						final int scatterZ = -groupScattering + rand.nextInt(groupScattering * 2);

						final ScheduledSpawn scheduledSpawn = new ScheduledSpawn(this.spawnManager, entry, groupX + scatterX, groupZ + scatterZ);
						this.scheduledSpawns.add(scheduledSpawn);
					}
				}
			}
		}
	}
}
