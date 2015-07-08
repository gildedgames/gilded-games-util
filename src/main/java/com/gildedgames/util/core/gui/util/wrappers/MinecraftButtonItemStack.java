package com.gildedgames.util.core.gui.util.wrappers;

import net.minecraft.item.ItemStack;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public class MinecraftButtonItemStack extends GuiFrame
{

	protected MinecraftItemStackRender itemStackRender;
	
	public MinecraftButtonItemStack(ItemStack stack)
	{
		this(Dim2D.build().area(20, 20).compile(), stack);
	}

	public MinecraftButtonItemStack(Dim2D dim, ItemStack stack)
	{
		super(dim);

		this.itemStackRender = new MinecraftItemStackRender(stack);
	}
	
	@Override
	public void initContent(InputProvider input)
	{
		this.content().setElement("button", new MinecraftButton(Dim2D.compile(), ""));
		this.content().setElement("itemStackRender", this.itemStackRender);
		
		this.itemStackRender.modDim().center(true).x(this.getDim().width() / 2).y(this.getDim().height() / 2).addX(2).addY(2).compile();
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		this.content().getElement("button", MinecraftButton.class).modDim().buildWith(this).area().build().compile();
	}
	
	public ItemStack getItemStack()
	{
		return this.itemStackRender.getItemStack();
	}

}