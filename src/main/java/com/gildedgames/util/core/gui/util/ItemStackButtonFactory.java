package com.gildedgames.util.core.gui.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameData;

import com.gildedgames.util.GGHelper;
import com.gildedgames.util.core.gui.util.wrappers.MinecraftButtonItemStack;
import com.gildedgames.util.core.gui.util.wrappers.MinecraftItemStackRender;
import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.util.events.DraggableBehavior;
import com.gildedgames.util.ui.util.factory.ContentFactory;
import com.gildedgames.util.ui.util.factory.GenericFactory;
import com.google.common.collect.ImmutableMap;

public class ItemStackButtonFactory implements ContentFactory
{
	
	public static enum StackTypes
	{
		
		BLOCKS
		{
			@Override
			List<ItemStack> createStacks()
			{
				List<ItemStack> blockStacks = new ArrayList<ItemStack>();

				for (final Block block : GameData.getBlockRegistry().typeSafeIterable())//TODO: Make sure this gets all blocks
				{
					if (block == null)
					{
						continue;
					}

					final Item item = Item.getItemFromBlock(block);

					if (item == null)
					{
						continue;
					}
					
					List<ItemStack> subBlocks = new ArrayList<ItemStack>();

					block.getSubBlocks(item, item.getCreativeTab(), subBlocks);

					for (final ItemStack stack : subBlocks)
					{
						if (GGHelper.getBlockState(stack) == GGHelper.getAirState())
						{
							continue;
						}

						blockStacks.add(stack);
					}
				}

				return blockStacks;
			}
		},
		ITEMS
		{
			@Override
			List<ItemStack> createStacks()
			{
				return null;
			}
		},
		ALL
		{
			@Override
			List<ItemStack> createStacks()
			{
				List<ItemStack> stacks = new ArrayList<ItemStack>();
				
				stacks.addAll(ITEMS.createStacks());
				stacks.addAll(BLOCKS.createStacks());
				
				return stacks;
			}
		};
		
		abstract List<ItemStack> createStacks();
		
	}
	
	private StackTypes stackTypes;
	
	public ItemStackButtonFactory(StackTypes stackTypes)
	{
		this.stackTypes = stackTypes;
	}

	@Override
	public LinkedHashMap<String, Ui> provideContent(ImmutableMap<String, Ui> currentContent, Dim2D contentArea)
	{
		LinkedHashMap<String, Ui> buttons = new LinkedHashMap<String, Ui>();
		
		for (ItemStack stack : this.stackTypes.createStacks())
		{
			final MinecraftButtonItemStack button = new MinecraftButtonItemStack(stack);
			
			button.listeners().set("draggableBehavior", new DraggableBehavior(new GenericFactory<MinecraftItemStackRender>()
			{

				@Override
				public MinecraftItemStackRender create()
				{
					return new MinecraftItemStackRender(button.getItemStack());
				}
				
			}));
			
			buttons.put(stack.getUnlocalizedName(), button);
		}
		
		return buttons;
	}

}
