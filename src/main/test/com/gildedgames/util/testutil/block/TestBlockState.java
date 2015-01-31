package com.gildedgames.util.testutil.block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

import com.google.common.collect.ImmutableMap;

/**
 * Uses really hacky stuff to simulate how Blocks work for testing.
 * Also tests on metadata (stone vs granite)
 * @author Emile
 *
 */
public class TestBlockState implements IBlockState
{

	public static BlockStone blockStone = new BlockStone();

	public static TestBlockAir blockAir = new TestBlockAir();

	public static TestBlockState stone = new TestBlockState(blockStone);

	public static TestBlockState granite = new TestBlockState(blockStone);

	public static TestBlockState air = new TestBlockState(blockAir);

	private static List<IBlockState> registered = new ArrayList<IBlockState>();

	private static Random random = new Random();

	static
	{
		registered.add(stone);
		registered.add(air);
		registered.add(granite);
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
		if (property == BlockStone.VARIANT_PROP)
		{
			return this == stone ? BlockStone.EnumType.STONE : BlockStone.EnumType.GRANITE;//ye this is evil don't kill me
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
		return null;
	}

	@Override
	public ImmutableMap getProperties()
	{
		return null;
	}

	@Override
	public Block getBlock()
	{
		return this.block;
	}

}
