package com.gildedgames.util.core.gui.util.wrappers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public class MinecraftItemStackRender extends GuiFrame
{
	
	protected final static Minecraft MC = Minecraft.getMinecraft();
	
	protected ItemStack stack;

	public MinecraftItemStackRender(ItemStack stack)
	{
		this(Dim2D.flush(), stack);
	}
	
	public MinecraftItemStackRender(Dim2D dim, ItemStack stack)
	{
		super(dim.clone().area(20, 20).flush());
		
		this.stack = stack;
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
		
		if (this.stack != null)
		{
			GL11.glPushMatrix();

			RenderHelper.enableGUIStandardItemLighting();
			
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glEnable(GL11.GL_LIGHTING);
			
			int x = this.getDim().x();
			
			MC.getRenderItem().renderItemIntoGUI(this.stack, this.getDim().x(), this.getDim().y());

			GL11.glDisable(GL11.GL_LIGHTING);
			
			GL11.glPopMatrix();
		}
	}
	
	public ItemStack getItemStack()
	{
		return this.stack;
	}

}
