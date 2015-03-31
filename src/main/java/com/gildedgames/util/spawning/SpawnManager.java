package com.gildedgames.util.spawning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.player.common.player.IPlayerProfile;
import com.gildedgames.util.spawning.util.ChunkMap;

public class SpawnManager
{
	private final List<SpawnEntry> genSpawningRegister = new ArrayList<SpawnEntry>();

	private final List<SpawnEntry> tickSpawningRegister = new ArrayList<SpawnEntry>();

	private final List<SpawnCondition> conditionRegister = new ArrayList<SpawnCondition>();

	//private ChunkMap<SpawnArea> spawnAreaOnGen;

	private ChunkMap<SpawnAreaPerTick> spawnAreaPerTick;

	protected final static Random random = new Random();

	private final SpawnSettings spawnSettings;

	private final int dimensionId;

	private static List<Block> blacklistedBlocks = new ArrayList<Block>();

	private int ticks;

	public SpawnManager(int dimensionId, SpawnSettings spawnSettings)
	{
		this.spawnSettings = spawnSettings;
		this.dimensionId = dimensionId;
	}

	public void clear()
	{
		//this.spawnAreaOnGen = null;
		this.spawnAreaPerTick = null;
	}

	public void tickSpawning(World world, Collection<? extends IPlayerHook> playerHooks)
	{
		this.ticks++;
		boolean doUpdate = this.ticks % this.spawnSettings.ticksBetweenUpdate() == 0;
		if (!doUpdate)
		{
			return;
		}
		for (IPlayerHook player : playerHooks)
		{
			IPlayerProfile profile = player.getProfile();
			if (profile.isLoggedIn())
			{
				EntityPlayer entityP = profile.getEntity();
				if (entityP.dimension == this.dimensionId)
				{
					this.wakeUpAreas(world, entityP.posX, entityP.posY, entityP.posZ);
				}
			}
		}

		List<SpawnAreaPerTick> toRemove = new ArrayList<SpawnAreaPerTick>();
		ChunkMap<SpawnAreaPerTick> spawnAreaPerTick = this.getspawnAreaPerTick();
		for (final SpawnAreaPerTick area : spawnAreaPerTick.getValues())
		{
			if (area.isAwake())
			{
				area.onUpdate(this.spawnSettings.areaSize(), world);
				if (area.getAmountOfUpdates() % this.spawnSettings.updatesBetweenRespawn() == 0 && area.noSchedulesLeft())
				{
					toRemove.add(area);
				}
			}
			//TODO: We can consider removing the area here too, when it's not awake
			//That means less memory, but possibly more CPU... A tradeoff
		}
		for (SpawnAreaPerTick removing : toRemove)
		{
			spawnAreaPerTick.remove(removing.getAreaX(), removing.getAreaZ());
		}
	}

	public int getTargetAmountOfEntities(World world, Random random)
	{
		return (int) Math.round(random.nextGaussian() * this.spawnSettings.amountOfEntitiesInAreaDeviation(world) + this.spawnSettings.averageAmountOfEntitiesInArea(world));
	}

	public int getMaxAmountOfEntitiesIn2x2Area(World world)
	{
		return this.spawnSettings.maxAmountOfEntitiesIn2x2Area(world);
	}

	private void wakeUpAreas(World world, double posX, double posY, double posZ)
	{
		final int chunkX = MathHelper.floor_double(posX) >> 4;
		final int chunkZ = MathHelper.floor_double(posZ) >> 4;

		final int pAreaX = chunkX / this.spawnSettings.areaSizePerTick();
		final int pAreaZ = chunkZ / this.spawnSettings.areaSizePerTick();

		ChunkMap<SpawnAreaPerTick> spawnAreaPerTick = this.getspawnAreaPerTick();

		for (int areaX = pAreaX - 1; areaX <= pAreaX + 1; areaX++)
		{
			for (int areaZ = pAreaZ - 1; areaZ <= pAreaZ + 1; areaZ++)
			{
				SpawnAreaPerTick spawnArea = spawnAreaPerTick.get(areaX, areaZ);

				if (spawnArea == null)
				{
					//Shuffle the list to allow for more variation, seeing as the algorithm
					//has bias towards entries earlier in the list.
					long randomSeed = world.getSeed() + ChunkCoordIntPair.chunkXZ2Int(areaX, areaZ);
					Random random = new Random(randomSeed);
					final List<SpawnEntry> shuffledRegistered = new ArrayList<SpawnEntry>(this.tickSpawningRegister);
					Collections.shuffle(shuffledRegistered, random);

					spawnArea = new SpawnAreaPerTick(this, areaX, areaZ, shuffledRegistered);

					spawnArea.schedule(world, this.spawnSettings.areaSizePerTick(), this.spawnSettings.groupScattering(), random);

					spawnAreaPerTick.put(areaX, areaZ, spawnArea);
				}

				spawnArea.wake();
			}
		}
	}

	//Dynamic instantiation to preserve memory
	/*private ChunkMap<SpawnArea> getspawnAreaOnGen()
	{
		if (this.spawnAreaOnGen == null)
		{
			this.spawnAreaOnGen = new ChunkMap<SpawnArea>();
		}

		return this.spawnAreaOnGen;
	}*/

	protected static void registerBlacklistedBlock(Block block)
	{
		blacklistedBlocks.add(block);
	}

	private ChunkMap<SpawnAreaPerTick> getspawnAreaPerTick()
	{
		if (this.spawnAreaPerTick == null)
		{
			this.spawnAreaPerTick = new ChunkMap<SpawnAreaPerTick>();
		}

		return this.spawnAreaPerTick;
	}

	public void registerSpawnCondition(SpawnCondition spawnCondition)
	{
		this.conditionRegister.add(spawnCondition);
	}

	public void registerGenEntry(SpawnEntry spawnEntry)
	{
		this.genSpawningRegister.add(spawnEntry);
	}

	public void registerPerTickEntry(SpawnEntry spawnEntry)
	{
		this.tickSpawningRegister.add(spawnEntry);
	}

	public void registerEntry(SpawnEntry spawnEntry)
	{
		this.genSpawningRegister.add(spawnEntry);
		this.tickSpawningRegister.add(spawnEntry);
	}

	public List<SpawnCondition> getSpawnConditions()
	{
		return this.conditionRegister;
	}

	public int getMaxSpawnAttempts()
	{
		return this.spawnSettings.maxSpawnAttempts();
	}

	public int getSpawnDistanceFromPlayer()
	{
		return this.spawnSettings.spawnDistanceFromPlayer();
	}

	/**
	 * Finds the highest block on the x, z coordinate that is solid and returns its y coord. Args x, z
	 */
	public int getTopSolidOrLiquidBlock(final World world, int posX, int posZ)
	{
		final Chunk chunk = world.getChunkFromChunkCoords(posX >> 4, posZ >> 4);
		final int x = posX;
		final int z = posZ;
		int k = chunk.getTopFilledSegment() + 15;
		posX &= 15;//Get the latest 4 bits of posX. idk what for. :D

		for (posZ &= 15; k > 0; --k)
		{
			final Block l = chunk.getBlock(posX, k, posZ);

			if (l != Blocks.air && l.getMaterial().blocksMovement() && l.getMaterial() != Material.leaves && !l.isFoliage(world, x, k, z) && !blacklistedBlocks.contains(l))
			{
				return k + 1;
			}
		}

		return -1;
	}

	public int getDimensionId()
	{
		return this.dimensionId;
	}
}
