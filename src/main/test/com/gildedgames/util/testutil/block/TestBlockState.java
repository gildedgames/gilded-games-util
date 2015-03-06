package com.gildedgames.util.testutil.block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

import com.google.common.collect.ImmutableMap;

/**
 * Uses really hacky stuff to simulate how Blocks work for testing.
 * Also tests on metadata (stone vs granite)
 * Even though it is hacky, it is really explicit, so it could be useful for figuring out what is meant
 * with the IBlockState interface methods.
 * @author Emile
 *
 */
public class TestBlockState implements IBlockState
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing");

	public static BlockStone blockStone = new BlockStone();

	public static TestBlockAir blockAir = new TestBlockAir();

	public static TestBlockState stone = new TestBlockState(blockStone);

	public static TestBlockState granite = new TestBlockState(blockStone);

	public static TestBlockState graniteSmooth = new TestBlockState(blockStone);

	public static TestBlockState diorite = new TestBlockState(blockStone);

	public static TestBlockState air = new TestBlockState(blockAir);

	private static List<IBlockState> registered = new ArrayList<IBlockState>();

	private static Random random = new Random();

	static
	{
		registered.add(stone);
		registered.add(air);
		registered.add(granite);
		registered.add(graniteSmooth);
		registered.add(diorite);
	}

	public static IBlockState getRandomState()
	{
		return registered.get(random.nextInt(registered.size()));
	}

	private Block block;

	public TestBlockState(Block block)
	{
		this.block = block;
	}

	@Override
	public Collection<IProperty> getPropertyNames()
	{
		return new ArrayList<IProperty>();
	}

	@Override
	public Comparable getValue(IProperty property)
	{
		if (property == BlockStone.VARIANT)
		{
			if (this == stone)
			{
				return BlockStone.EnumType.STONE;
			}
			if (this == granite)
			{
				return BlockStone.EnumType.GRANITE;//ye this is evil don't kill me
			}
			if (this == graniteSmooth)
			{
				return BlockStone.EnumType.GRANITE_SMOOTH;
			}
			if (this == diorite)
			{
				return BlockStone.EnumType.DIORITE;
			}
		}
		if (property == FACING)
		{
			if (this == stone)
			{
				return EnumFacing.EAST;
			}
			if (this == granite)
			{
				return EnumFacing.SOUTH;
			}
			if (this == graniteSmooth)
			{
				return EnumFacing.WEST;
			}
			if (this == diorite)
			{
				return EnumFacing.NORTH;
			}
		}
		return null;
	}

	@Override
	public IBlockState withProperty(IProperty property, Comparable value)
	{
		return null;
	}

	@Override
	public IBlockState cycleProperty(IProperty property)
	{
		if (property == FACING)
		{
			if (this == stone)
			{
				return granite;
			}
			if (this == granite)
			{
				return graniteSmooth;
			}
			if (this == graniteSmooth)
			{
				return diorite;
			}
			if (this == diorite)
			{
				return stone;
			}
		}
		return this;
	}

	@Override
	public ImmutableMap<IProperty, String> getProperties()
	{
		return new ImmutableMap.Builder<IProperty, String>().put(FACING, "facing").build();
	}

	@Override
	public Block getBlock()
	{
		return this.block;
	}

}
