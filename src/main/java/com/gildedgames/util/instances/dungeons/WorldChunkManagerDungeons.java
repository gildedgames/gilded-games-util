package com.gildedgames.util.instances.dungeons;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

public class WorldChunkManagerDungeons extends WorldChunkManager
{
	private BiomeGenBase biomeGenerator;

	public WorldChunkManagerDungeons()
	{
		this.biomeGenerator = new DungeonsBiome();
	}

	//TODO: Maybe need to override func_180300_a

	@Override
	public boolean areBiomesViable(int i, int j, int k, List list)
	{
		return list.contains(this.biomeGenerator);
	}

	@Override
	public BlockPos findBiomePosition(int x, int y, int z, List biomes, Random random)
	{
		if (biomes.contains(this.biomeGenerator))
		{
			return new BlockPos(x - z + random.nextInt(z * 2 + 1), 0, y - z + random.nextInt(z * 2 + 1));
		}
		else
		{
			return null;
		}
	}

	@Override
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] listToReuse, int x, int z, int width, int length, boolean cache)
	{
		return this.loadBlockGeneratorData(listToReuse, x, z, width, length);
	}

	@Override
	public BiomeGenBase getBiomeGenerator(BlockPos pos)
	{
		return this.biomeGenerator;
	}

	@Override
	public float[] getRainfall(float[] listToReuse, int x, int z, int width, int length)
	{
		if (listToReuse == null || listToReuse.length < width * length)
		{
			listToReuse = new float[width * length];
		}

		Arrays.fill(listToReuse, 0, width * length, 0);
		return listToReuse;
	}

	@Override
	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] oldList, int x, int z, int width, int length)
	{
		if (oldList == null || oldList.length < width * length)
		{
			oldList = new BiomeGenBase[width * length];
		}

		Arrays.fill(oldList, 0, width * length, this.biomeGenerator);
		return oldList;
	}
}
