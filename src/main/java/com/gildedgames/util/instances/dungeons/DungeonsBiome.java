package com.gildedgames.util.instances.dungeons;

import net.minecraft.world.biome.BiomeGenBase;

public class DungeonsBiome extends BiomeGenBase
{
	public DungeonsBiome()
	{
		super(200);//TODO: Biome ID is a constant.
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.setBiomeName("Dungeons");
		this.setDisableRain();
	}

	@Override
	public float getSpawningChance()
	{
		return 0.01F;
	}
}
