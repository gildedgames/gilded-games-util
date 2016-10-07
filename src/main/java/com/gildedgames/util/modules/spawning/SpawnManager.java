package com.gildedgames.util.modules.spawning;

import com.gildedgames.util.core.util.ChunkMap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SpawnManager
{
	private final List<SpawnEntry> genSpawningRegister = new ArrayList<>();

	private final List<SpawnEntry> tickSpawningRegister = new ArrayList<>();

	private final List<SpawnCondition> conditionRegister = new ArrayList<>();

	//private ChunkMap<SpawnArea> spawnAreaOnGen;

	private ChunkMap<SpawnAreaPerTick> spawnAreaPerTick;

	protected final static Random random = new Random();

	private final SpawnSettings spawnSettings;

	private final int dimensionId;

	private static List<Block> blacklistedBlocks = new ArrayList<>();

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

	public void tickSpawning(World world, Collection<EntityPlayer> players)
	{
		this.ticks++;

		boolean doUpdate = this.ticks % this.spawnSettings.ticksBetweenUpdate() == 0;

		if (!doUpdate)
		{
			return;
		}

		for (EntityPlayer player : players)
		{
			if (player.isEntityAlive())
			{
				if (player.dimension == this.dimensionId)
				{
					this.wakeUpAreas(world, player.posX, player.posY, player.posZ);
				}
			}
		}

		List<SpawnAreaPerTick> toRemove = new ArrayList<>();
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
					long randomSeed = world.getSeed() + ChunkPos.asLong(areaX, areaZ);
					Random random = new Random(randomSeed);
					final List<SpawnEntry> shuffledRegistered = new ArrayList<>(this.tickSpawningRegister);
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
			this.spawnAreaPerTick = new ChunkMap<>();
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
			final IBlockState l = chunk.getBlockState(posX, k, posZ);

			if (l != Blocks.AIR && l.getMaterial().blocksMovement() && l.getMaterial() != Material.LEAVES && !l.getBlock().isFoliage(world, new BlockPos(x, k, z)) && !blacklistedBlocks.contains(l))
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
