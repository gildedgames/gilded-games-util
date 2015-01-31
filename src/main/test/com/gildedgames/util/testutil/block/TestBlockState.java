package com.gildedgames.util.testutil.block;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

import com.google.common.collect.ImmutableMap;

public class TestBlockState implements IBlockState
{

	public static TestBlockState stone = new TestBlockState(new BlockStone());

	public static TestBlockState air = new TestBlockState(new TestBlockAir());

	private static List<IBlockState> registered;

	private static Random random = new Random();

	static
	{
		registered.add(stone);
		registered.add(air);
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
		return null;
	}

	@Override
	public Comparable getValue(IProperty property)
	{
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
