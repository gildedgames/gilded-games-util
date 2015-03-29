package com.gildedgames.util.spawning;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.world.World;

public class SpawnArea
{
	protected final int areaX, areaZ;

	protected final List<SpawnEntry> spawnEntries;

	protected final List<ScheduledSpawn> scheduledSpawns = new ArrayList<ScheduledSpawn>();

	protected final SpawnManager spawnManager;

	public SpawnArea(SpawnManager spawnManager, int areaX, int areaZ, List<SpawnEntry> spawnEntries)
	{
		this.areaX = areaX;
		this.areaZ = areaZ;

		this.spawnEntries = spawnEntries;

		this.spawnManager = spawnManager;
	}

	public void schedule(World world, int chunksPerArea, int groupScattering, Random rand)
	{
		for (final SpawnEntry entry : this.spawnEntries)
		{
			final int numGroups = entry.getGroupsInArea(rand);

			for (int groupsC = 0; groupsC < numGroups; groupsC++)
			{
				final int groupSize = entry.getGroupSize(rand);

				final int blocksPerArea = chunksPerArea << 4;//times 16

				final int groupSpawnX = this.areaX * blocksPerArea + rand.nextInt(blocksPerArea);
				final int groupSpawnZ = this.areaZ * blocksPerArea + rand.nextInt(blocksPerArea);

				for (int j = 0; j < groupSize; j++)
				{
					final int scatterX = -groupScattering + rand.nextInt(groupScattering * 2);
					final int scatterZ = -groupScattering + rand.nextInt(groupScattering * 2);

					final int posX = groupSpawnX + scatterX;
					final int posZ = groupSpawnZ + scatterZ;

					this.scheduledSpawns.add(new ScheduledSpawn(this.spawnManager, entry, posX, posZ));
				}
			}
		}
	}

	public void performChunkSpawning(World world, int chunksPerArea, int chunkX, int chunkZ)
	{
		Iterator<ScheduledSpawn> iter = this.scheduledSpawns.iterator();
		while (iter.hasNext())
		{
			ScheduledSpawn scheduledSpawn = iter.next();
			final int spawnChunkX = scheduledSpawn.getPosX() >> 4;
			final int spawnChunkZ = scheduledSpawn.getPosZ() >> 4;
			if (chunkX == spawnChunkX && chunkZ == spawnChunkZ)
			{
				if (scheduledSpawn.spawn(chunksPerArea, world))
				{
					iter.remove();
				}
			}
		}
	}

	public int getAreaX()
	{
		return this.areaX;
	}

	public int getAreaZ()
	{
		return this.areaZ;
	}
}
