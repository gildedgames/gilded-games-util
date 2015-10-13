package com.gildedgames.util.core.gui.util.wrappers;

import net.minecraft.item.ItemStack;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.rect.Dim2D;
import com.gildedgames.util.ui.data.rect.Rect;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public class MinecraftButtonItemStack extends GuiFrame
{

	protected MinecraftItemStackRender itemStackRender;
	
	public MinecraftButtonItemStack(ItemStack stack)
	{
		this(Dim2D.build().area(20, 20).flush(), stack);
	}

	public MinecraftButtonItemStack(Rect dim, ItemStack stack)
	{
		super(dim);

		this.itemStackRender = new MinecraftItemStackRender(stack);
	}
	
	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);
		
		this.content().set("button", new MinecraftButton(Dim2D.build().buildWith(this).area().flush(), ""));
		this.content().set("itemStackRender", this.itemStackRender);
		
		this.itemStackRender.dim().mod().center(true).x(this.dim().width() / 2).y(this.dim().height() / 2).flush();
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
		
		MinecraftButton button = this.content().get("button", MinecraftButton.class);
		
		button.dim().mod().buildWith(this).area().build().flush();
	}
	
	public ItemStack getItemStack()
	{
		return this.itemStackRender.getItemStack();
	}

}